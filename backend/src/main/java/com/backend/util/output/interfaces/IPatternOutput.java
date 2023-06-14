package com.backend.util.output.interfaces;

import com.backend.entities.terraform.TerraformTemplate;


public interface IPatternOutput extends IOutput {
	
	String createFile(TerraformTemplate t);
	

}
