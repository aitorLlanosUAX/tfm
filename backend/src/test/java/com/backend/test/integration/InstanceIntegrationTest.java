package com.backend.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.backend.controllers.InstanceController;
import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.service.InstanceService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = InstanceController.class)
public class InstanceIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private InstanceService instanceService;
	
	
	private Instance instance = new Instance();
	
	String jsonString = "{\"id\":null,\"name\":\"t2.micro\",\"vCpu\":1,\"storage\":null,\"memory\":1,\"cost\":1.0,\"imagen_id\":1}";
	String jsonStringList = "[{\"id\":null,\"name\":\"t2.micro\",\"vCpu\":1,\"storage\":null,\"memory\":1,\"cost\":1.0,\"imagen_id\":1}]";
	

	@BeforeEach
	public void setup() {
		instance.setName("t2.micro");
		instance.setvCpu(1);
		instance.setMemory(1);
		instance.setCost(1);
		instance.setImagen_id(1);
	}
	
	
	@Test
	public void normalAddinstance() throws Exception {
		String url = "/instance/insert";
		String jsonString = "{\"name\":\"t2.micro\",\"vCpu\":\"1\",\"memory\":1,\"cost\":1,\"image_id\":1}";

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void addinstanceWithoutParams() throws Exception {
		String url = "/instance/insert";
		String jsonString = "{}";

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		assertEquals(String.valueOf(Response.SC_NOT_ACCEPTABLE), result.getResponse().getContentAsString());
	}
	
	@Test
	public void normalDelete() throws Exception {
		String url = "/instance/delete?id=1";

		Mockito.when(instanceService.findById(1)).thenReturn(instance);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void deleteinstanceWithoutParams() throws Exception {
		String url = "/instance/delete";


		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
	
	@Test
	public void deleteinstanceWithoutExistentinstance() throws Exception {
		String url = "/instance/delete?id=2";

		Mockito.when(instanceService.findById(1)).thenReturn(instance);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

				
		assertEquals(String.valueOf(Response.SC_NOT_FOUND), result.getResponse().getContentAsString());
	}
	

	
	@Test
	public void normalList() throws Exception {
		String url = "/instance/list";
		List<Instance> instances = new ArrayList<Instance>();
		instances.add(instance);

		Mockito.when(instanceService.findAll()).thenReturn(instances);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonStringList, response.getContentAsString());

	}

	
	@Test
	public void normalListEmpty() throws Exception {
		String url = "/instance/list";
	
		Mockito.when(instanceService.findAll()).thenReturn(new ArrayList<Instance>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
	
	@Test
	public void normalFindById() throws Exception {
		String url = "/instance/findById?id=1";
	
		Mockito.when(instanceService.findById(1)).thenReturn(instance);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonString, response.getContentAsString());

	}
	
	@Test
	public void findByIdWithNoId() throws Exception {
		String url = "/instance/findById?id=2";
	
		Mockito.when(instanceService.findById(1)).thenReturn(instance);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("", response.getContentAsString());

	}
	
	@Test
	public void normalFindByImageId() throws Exception {
		String url = "/instance/fromImage?image_id=1";
		List<Instance> instances = new ArrayList<Instance>();
		instances.add(instance);

		Mockito.when(instanceService.findByImageId(1)).thenReturn(instances);


		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonStringList, response.getContentAsString());

	}
	
	@Test
	public void findByIdWithNoImageId() throws Exception {
		String url = "/instance/fromImage?image_id=2";
		List<Instance> instances = new ArrayList<Instance>();
		instances.add(instance);

		Mockito.when(instanceService.findByImageId(1)).thenReturn(instances);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
	
	
	

}
