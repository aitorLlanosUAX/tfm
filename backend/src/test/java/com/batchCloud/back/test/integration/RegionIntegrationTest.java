package com.batchCloud.back.test.integration;

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

import com.backend.controllers.RegionController;
import com.backend.entities.terraform.terraformDatabase.Region;
import com.backend.service.RegionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RegionController.class)
public class RegionIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RegionService regionService;

	private Region wellRegion = new Region();

	String jsonRegion = "{\"id\":null,\"name\":\"eu-west-3\",\"zone\":\"Paris\",\"provider_id\":1}";
	String jsonRegionList = "[{\"id\":null,\"name\":\"eu-west-3\",\"zone\":\"Paris\",\"provider_id\":1}]";

	@BeforeEach
	public void setUp() {
		wellRegion.setName("eu-west-3");
		wellRegion.setZone("Paris");
		wellRegion.setProvider_id(1);
	}

	@Test
	public void normaAddRegion() throws Exception {
		String url = "/region/insert";
		String jsonString = "{\"zone\":\"Paris\",\"name\":\"eu-west-3\",\"provider_id\":\"1\"}";

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}
	
	@Test
	public void addRegionWithoutParams() throws Exception {
		String url = "/region/insert";
		String jsonString = "{}";

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString)).andReturn();

		assertEquals(String.valueOf(Response.SC_BAD_REQUEST), result.getResponse().getContentAsString());

	}
	
	@Test
	public void normalDelete() throws Exception {
		String url = "/region/delete?id=1";

		Mockito.when(regionService.findById(1)).thenReturn(wellRegion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void deleteRegionWithoutParams() throws Exception {
		String url = "/region/delete";

		Mockito.when(regionService.findById(1)).thenReturn(wellRegion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
	
	@Test
	public void deleteRegionWithoutExistentRegion() throws Exception {
		String url = "/region/delete?id=2";

		Mockito.when(regionService.findById(1)).thenReturn(wellRegion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

				
		assertEquals(String.valueOf(Response.SC_NOT_FOUND), result.getResponse().getContentAsString());
	}
	
	
	@Test
	public void normalList() throws Exception {
		String url = "/region/list";
		List<Region> regions = new ArrayList<Region>();
		regions.add(wellRegion);

		Mockito.when(regionService.findAll()).thenReturn(regions);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonRegionList, response.getContentAsString());

	}

	
	@Test
	public void normalListEmpty() throws Exception {
		String url = "/region/list";
	
		Mockito.when(regionService.findAll()).thenReturn(new ArrayList<Region>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
	
	@Test
	public void normalFindById() throws Exception {
		String url = "/region/findById?id=1";
	
		Mockito.when(regionService.findById(1)).thenReturn(wellRegion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonRegion, response.getContentAsString());

	}
	
	@Test
	public void findByIdWithNoId() throws Exception {
		String url = "/region/findById?id=2";
	
		Mockito.when(regionService.findById(1)).thenReturn(wellRegion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("", response.getContentAsString());

	}
	
	
	@Test
	public void normalFindByProviderId() throws Exception {
		String url = "/region/fromProvider?provider_id=1";
		List<Region> regions = new ArrayList<Region>();
		regions.add(wellRegion);

		Mockito.when(regionService.findByProviderId(1)).thenReturn(regions);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonRegionList, response.getContentAsString());

	}
	
	@Test
	public void findByProviderIdNoExistent() throws Exception {
		String url = "/region/fromProvider?provider_id=2";
		List<Region> regions = new ArrayList<Region>();
		regions.add(wellRegion);

		Mockito.when(regionService.findByProviderId(1)).thenReturn(regions);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
}
