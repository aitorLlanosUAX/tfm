package com.executor.service.terraform;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TerraformClient implements AutoCloseable {
    private static final String TERRAFORM_EXE_NAME = "terraform";
    private static final String VERSION_COMMAND = "version", INIT_COMMAND = "init", PLAN_COMMAND = "plan", APPLY_COMMAND = "apply", DESTROY_COMMAND = "destroy";
    private static final Map<String, String> NON_INTERACTIVE_COMMAND_MAP = new HashMap<>();
    static {
        NON_INTERACTIVE_COMMAND_MAP.put(APPLY_COMMAND, "-auto-approve");
        NON_INTERACTIVE_COMMAND_MAP.put(DESTROY_COMMAND, "-auto-approve");
    }

    private final ExecutorService executor = Executors.newWorkStealingPool();
    private File workingDirectory;
    private boolean inheritIO;
    private Consumer<String> outputListener, errorListener;


    public Consumer<String> getOutputListener() {
        return this.outputListener;
    }

    public void setOutputListener(Consumer<String> listener) {
        this.outputListener = listener;
    }

    public Consumer<String> getErrorListener() {
        return this.errorListener;
    }

    public void setErrorListener(Consumer<String> listener) {
        this.errorListener = listener;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void setWorkingDirectory(Path folderPath) {
        this.setWorkingDirectory(folderPath.toFile());
    }

    public boolean isInheritIO() {
        return this.inheritIO;
    }

    public void setInheritIO(boolean inheritIO) {
        this.inheritIO = inheritIO;
    }

    public CompletableFuture<String> version() throws IOException {
        ProcessLauncher launcher = this.getTerraformLauncher(VERSION_COMMAND);
        StringBuilder version = new StringBuilder();
        Consumer<String> outputListener = this.getOutputListener();
        launcher.setOutputListener(m -> {
            version.append(version.length() == 0 ? m : "");
            if (outputListener != null) {
                outputListener.accept(m);
            }
        });
        return launcher.launch().thenApply((c) -> c == 0 ? version.toString() : null);
    }

    public CompletableFuture<Boolean> plan() throws IOException {
        this.checkRunningParameters();
        return this.run(INIT_COMMAND, PLAN_COMMAND);
    }

    public CompletableFuture<Boolean> apply() throws IOException {
        this.checkRunningParameters();
        return this.run(INIT_COMMAND, APPLY_COMMAND);
    }

    public CompletableFuture<Boolean> destroy() throws IOException {
        this.checkRunningParameters();
        return this.run(INIT_COMMAND, DESTROY_COMMAND);
    }

    private CompletableFuture<Boolean> run(String... commands) throws IOException {
        assert commands.length > 0;
        ProcessLauncher[] launchers = new ProcessLauncher[commands.length];
        for (int i = 0; i < commands.length; i++) {
            launchers[i] = this.getTerraformLauncher(commands[i]);
        }

        CompletableFuture<Integer> result = launchers[0].launch().thenApply(c -> c == 0 ? 1 : -1);
        for (int i = 1; i < commands.length; i++) {
            result = result.thenCompose(index -> {
                if (index > 0) {
                    return launchers[index].launch().thenApply(c -> c == 0 ? index + 1 : -1);
                }
                return CompletableFuture.completedFuture(-1);
            });
        }
        return result.thenApply(i -> i > 0);
    }

    private void checkRunningParameters() {
        if (this.getWorkingDirectory() == null) {
            throw new IllegalArgumentException("working directory should not be null");
        }
    }

    private ProcessLauncher getTerraformLauncher(String command) throws IOException {
        ProcessLauncher launcher = new ProcessLauncher(this.executor, TERRAFORM_EXE_NAME, command);
        launcher.setDirectory(this.getWorkingDirectory());
        launcher.setInheritIO(this.isInheritIO());
      
        launcher.appendCommands(NON_INTERACTIVE_COMMAND_MAP.get(command));
        launcher.setOutputListener(this.getOutputListener());
        launcher.setErrorListener(this.getErrorListener());
        return launcher;
    }

    @Override
    public void close() throws Exception {
        this.executor.shutdownNow();
        if (!this.executor.awaitTermination(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("executor did not terminate");
        }
        System.out.println("Terraform closed");
    }
}
