package com.batchCloud.back.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.backend.controllers.ProcessController;
import com.backend.entities.BatchProcess;
import com.backend.service.ProcessService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProcessController.class)
public class ProcessIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProcessService processService;
	
	private BatchProcess batchProcess = new BatchProcess();
	
	String json = "{\"id\":null,\"name\":\"eu-west-3\",\"city\":\"Paris\",\"provider_id\":1}";
	String jsonStringList = "[{\"id\":null,\"name\":\"Test\",\"description\":\"Test\",\"pathTemplate\":null,\"created_at\":null,\"deleted_at\":null,\"active\":true,\"status\":\"Testing\"}]";

	
	@BeforeEach
	public void setup() {
		batchProcess.setName("Test");
		batchProcess.setStatus("Testing");
		batchProcess.setDescription("Test");
		batchProcess.setActive(true);
		batchProcess.setCreated_at(null);
	}
	
	@Test
	public void normalList() throws Exception {
		String url = "/process/list";
		List<BatchProcess> processes = new ArrayList<BatchProcess>();
		processes.add(batchProcess);

		Mockito.when(processService.findAllNotDeleted()).thenReturn(processes);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonStringList, response.getContentAsString());

	}

	@Test
	public void normalListEmpty() throws Exception {
		String url = "/process/list";

		Mockito.when(processService.findAll()).thenReturn(new ArrayList<BatchProcess>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}

	

	
	@Test
	public void addProcessWithNoParams() throws Exception {
		String url = "/process/newProcess";
		String jsonString = "{}";
		Mockito.when(processService.checkRequiredInsertParams(anyMap())).thenReturn("Process name not found");

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString)).andReturn();

		assertEquals("Process name not found", result.getResponse().getContentAsString());

	}
	
	@Test
	public void normalDelete() throws Exception {
		String url = "/process/delete?id=1";

		Mockito.when(processService.findById(1)).thenReturn(batchProcess);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void deleteRegionWithoutId() throws Exception {
		String url = "/process/delete?id=2";

		Mockito.when(processService.findById(2)).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
				
		assertEquals(String.valueOf(Response.SC_NOT_FOUND), result.getResponse().getContentAsString());

	}
	
	
}
