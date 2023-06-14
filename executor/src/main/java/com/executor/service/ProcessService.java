package com.executor.service;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.executor.entities.BatchProcess;
import com.executor.repository.ProcessRepository;
import com.executor.service.terraform.TerraformClient;

@Service
public class ProcessService {
    Logger logger = LoggerFactory.getLogger(ProcessService.class);


	private ProcessRepository processRepository;

	private TerraformClient terraform = new TerraformClient();
	@Autowired
	private ApplicationContext context;

	@Autowired
	public void context(ApplicationContext context) {
		this.context = context;
	}

	public ProcessService(ProcessRepository processRepository) {
		this.processRepository = processRepository;
	}

	public void executeProcess() {
		MessageListener listener = context.getBean(MessageListener.class);
		try {
			listener.executableLatch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
	}

	private void executeTerraform(BatchProcess process) throws Exception {
		String regexPatter = process.getName() + ".tf";
		String directory = process.getPathTemplate().split("\\\\" + regexPatter)[0];
		File directoryFile = new File(directory);
		logger.trace("Executable received: " + process);
		try {
			this.terraform.setOutputListener(System.out::println);
			this.terraform.setErrorListener(System.err::println);

			this.terraform.setWorkingDirectory(directoryFile);
			this.terraform.plan().get();
			this.terraform.apply().get();
		}catch(Exception e) {
			System.out.println("Error running instances");
		} 
		logger.trace("Running instances: " + process);
		System.out.println("Running instances");
		process.setStatus("Running");
		processRepository.save(process);
	}

	private void destroyTerraform(BatchProcess process) throws Exception {
		logger.trace("Destroying process: " + process);

		try {
			this.terraform.destroy().get();
		} catch(Exception e) {
			System.out.println("Error destroying instances");
		} 
		logger.trace("Destroyed process: " + process);

		System.out.println("Destroyed instances");
		process.setStatus("Pending");
		processRepository.save(process);
	}

	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}

	public static class MessageListener {

		private CountDownLatch executableLatch = new CountDownLatch(1);

		@Autowired
		private ProcessService service;

		@KafkaListener(topics = "${message.topic.name}", containerFactory = "executableKafkaListenerContainerFactory")
		public void executableListener(BatchProcess executable) {
			System.out.println("Received executable message: " + executable);
			try {

				service.executeTerraform(executable);
			} catch (Exception e) {
			}
			this.executableLatch.countDown();
		}
		
		@KafkaListener(topics = "${message.topic.destroy}", containerFactory = "executableKafkaListenerContainerFactory")
		public void executableListenerDestoy(BatchProcess executable) {
			System.out.println("Received destroy message: " + executable);
			try {
				service.destroyTerraform(executable);
			} catch (Exception e) {
				
			}
			this.executableLatch.countDown();
		}


	}

}
