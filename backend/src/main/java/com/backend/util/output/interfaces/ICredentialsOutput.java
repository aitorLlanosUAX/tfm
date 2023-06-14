package com.backend.util.output.interfaces;

public interface ICredentialsOutput extends IOutput {
	String createFile(String directoryPath, String credentials);
	
	
}
