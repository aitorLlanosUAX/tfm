package com.validator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.validator.service.ProcessService;

@RestController 
public class ProcessExecutor {

	private ProcessService processService;

	public ProcessExecutor(ProcessService processService) {
		this.processService = processService;
	}

	/**
	 * 
	 * @param id
	 */
	@GetMapping("/executeProcess")
	public String executeProcess(@RequestParam Integer id) {
		if(processService.executeProcess(id) == -1)
			return "Not valid process";
		return "Published";
	}
	
	/**
	 * 
	 * @param id
	 */
	@GetMapping("/destroyProcess")
	public String destroyProcess(@RequestParam Integer id) {
		if(processService.destroyProcess(id) == -1)
			return "Not valid process";
		return "Published";

	}

}
