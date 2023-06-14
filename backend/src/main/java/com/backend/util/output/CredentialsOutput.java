package com.backend.util.output;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.backend.util.output.interfaces.ICredentialsOutput;

public class CredentialsOutput implements ICredentialsOutput {

	public String createFile(String directoryPath, String credentials) {
		String path = directoryPath + "/";
		File file = new File(path + "credentials");
		BufferedWriter writer = null;
		try {
			File directory = new File(path);
			directory.mkdirs();
			if (!file.createNewFile()) {
				return null;
			}
			writer = new BufferedWriter(new FileWriter(file.getPath(), true));
			writer.write(credentials);

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

	public Map<String, String> getCredentials(String path) {
		File file = new File(path);
		if (!file.exists())
			return null;
		BufferedReader reader = null;
		Map<String, String> credentials = new HashMap<String, String>();

		try {
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();// Profile
			while (reader.ready()) {
				String credential = reader.readLine();
				String[] splitedLine = credential.split("=");
				credentials.put(splitedLine[0], splitedLine[1].replace("\"", ""));
			}
		} catch (IOException e) {
			return null;
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		return credentials;
	}

}
