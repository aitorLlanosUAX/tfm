package com.backend.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.entities.BatchProcess;
import com.backend.service.ProcessService;

@Controller
@RequestMapping("/process")
public class ProcessController {

	ProcessService processService;

	public ProcessController(ProcessService processService) {
		this.processService = processService;
	}

	@GetMapping("/list")
	public @ResponseBody List<BatchProcess> findAll() {
		return processService.findAllNotDeleted();
	}

	@PostMapping("/newProcess")
	public @ResponseBody String insert(@RequestBody Map<String, String> processDate) {
		String messageError = processService.checkRequiredInsertParams(processDate);
		if (messageError != null)
			return messageError;
		String result = processService.insertNewProcess(processDate);
		if (result == null)
			return "CREATING_PROCESS_ERROR";
		return "Success";
	}

	@GetMapping("/search")
	public @ResponseBody List<BatchProcess> findByPartialText(@RequestParam String partialText) {
		return processService.findByPartialText(partialText);
	}

	@GetMapping("/findById")
	public @ResponseBody BatchProcess findById(@RequestParam Integer id) {
		if (processService.findById(id) == null)
			return null;
		return processService.findById(id);
	}

	@PostMapping("/delete")
	public @ResponseBody int delete(@RequestParam Integer id) {
		BatchProcess process = processService.findById(id);
		if (process == null)
			return Response.SC_NOT_FOUND;
		process.setDeleted_at(new Date(System.currentTimeMillis()));
		processService.update(process);
		return Response.SC_OK;
	}

	@PostMapping("/update")
	public @ResponseBody int update(@RequestBody Map<String, String> processDate) {
		BatchProcess process = processService.findById(Integer.valueOf(processDate.get("id")));
		if (process == null)
			return Response.SC_NOT_FOUND;
		process.setName(processDate.get("name"));
		process.setDescription(processDate.get("description"));
		process.setActive(Boolean.parseBoolean(processDate.get("active")));
		process.setStatus(processDate.get("status"));

		processService.update(process);
		return Response.SC_OK;
	}

	@GetMapping("/downloadTemplate")
	public ResponseEntity<Resource> downloadTemplate(@RequestParam Integer process_id) {
		File file = processService.getTemplate(process_id);

		InputStreamResource resource = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		try {
			resource = new InputStreamResource(new FileInputStream(file));

		} catch (FileNotFoundException e) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

}
