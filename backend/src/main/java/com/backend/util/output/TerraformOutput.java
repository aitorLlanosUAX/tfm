package com.backend.util.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.backend.entities.terraform.TerraformTemplate;
import com.backend.util.output.interfaces.IPatternOutput;

public class TerraformOutput implements IPatternOutput {

	private String pathTerraform;

	public TerraformOutput(String path) {
		this.pathTerraform = path;
	}

	public String createFile(TerraformTemplate template) {
		pathTerraform += "/" + template.getProcessName() + "/";
		File file = new File(pathTerraform + template.getProcessName() + ".tf");
		BufferedWriter writer = null;
		try {
			File directory = new File(pathTerraform);
			if (directory.exists()) {
				String[] entries = directory.list();
				for (String s : entries) {
					File currentFile = new File(directory.getPath(), s);
					currentFile.delete();
				}
				directory.delete();
			}
			directory.mkdirs();
			if (!file.createNewFile()) {
				return null;
			}
			writer = new BufferedWriter(new FileWriter(file.getPath(), true));
			writer.write(template.setRequiredProvider());
			writer.newLine();

			writer.write(template.setProvider());
			writer.newLine();
			writer.write(template.getProvider().setInstanceResource(template.getProcessName().replaceAll("\\s+", ""),
					template.getInstanceNumber()));

		} catch (IOException e) {
			return null;
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				return null;
			}
		}
		return file.getPath();
	}

	public void deleteFile(String path) {
		File file = new File(path);
		file.delete();

	}

}
