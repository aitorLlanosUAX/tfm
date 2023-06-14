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

import com.backend.controllers.ImageIsoController;
import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.service.ImageIsoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ImageIsoController.class)
public class ImageIsoIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ImageIsoService imageService;
	
	
	private ImageIso wellImage = new ImageIso();
	
	
	String jsonString = "{\"id\":null,\"name\":\"image_name\",\"operatingSystem\":\"Ubuntu\",\"region_id\":1,\"provider_id\":1}";
	String jsonStringList = "[{\"id\":null,\"name\":\"image_name\",\"operatingSystem\":\"Ubuntu\",\"region_id\":1,\"provider_id\":1}]";


	
	@BeforeEach
	public void setup() {
		wellImage.setName("image_name");
		wellImage.setOperatingSystem("Ubuntu");
		wellImage.setProvider_id(1);
		wellImage.setRegion_id(1);
	}
	
	@Test
	public void normalAddImage() throws Exception {
		String url = "/image/insert";
		String jsonString = "{\"operatingSystem\":\"Ubuntu\",\"name\":\"a\",\"provider_id\":1,\"image_id\":1}";

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void addImageWithoutParams() throws Exception {
		String url = "/image/insert";
		String jsonString = "{}";

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		assertEquals(String.valueOf(Response.SC_BAD_REQUEST), result.getResponse().getContentAsString());
	}
	
	@Test
	public void normalDelete() throws Exception {
		String url = "/image/delete?id=1";

		Mockito.when(imageService.findById(1)).thenReturn(wellImage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void deleteImageWithoutParams() throws Exception {
		String url = "/image/delete";


		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
	
	@Test
	public void deleteImageWithoutExistentimage() throws Exception {
		String url = "/image/delete?id=2";

		Mockito.when(imageService.findById(1)).thenReturn(wellImage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

				
		assertEquals(String.valueOf(Response.SC_NOT_FOUND), result.getResponse().getContentAsString());
	}
	

	
	@Test
	public void normalList() throws Exception {
		String url = "/image/list";
		List<ImageIso> images = new ArrayList<ImageIso>();
		images.add(wellImage);

		Mockito.when(imageService.findAll()).thenReturn(images);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonStringList, response.getContentAsString());

	}

	
	@Test
	public void normalListEmpty() throws Exception {
		String url = "/image/list";
	
		Mockito.when(imageService.findAll()).thenReturn(new ArrayList<ImageIso>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
	
	@Test
	public void normalFindById() throws Exception {
		String url = "/image/findById?id=1";
	
		Mockito.when(imageService.findById(1)).thenReturn(wellImage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonString, response.getContentAsString());

	}
	
	@Test
	public void findByIdWithNoId() throws Exception {
		String url = "/image/findById?id=2";
	
		Mockito.when(imageService.findById(1)).thenReturn(wellImage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("", response.getContentAsString());

	}
	
	
	@Test
	public void normalFindByProviderAndRegionId() throws Exception {
		String url = "/image/findByRegionAndProvider?region_id=1&provider_id=1";
		List<ImageIso> images = new ArrayList<ImageIso>();
		images.add(wellImage);

		Mockito.when(imageService.findByRegionIdAndProviderId(1,1)).thenReturn(images);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonStringList, response.getContentAsString());

	}
	
	@Test
	public void findByProviderIdNoExistent() throws Exception {
		String url = "/image/findByRegionAndProvider?region_id=1&provider_id=2";
		List<ImageIso> images = new ArrayList<ImageIso>();
		images.add(wellImage);

		Mockito.when(imageService.findByRegionIdAndProviderId(1,1)).thenReturn(images);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
	
	@Test
	public void findByRegionIdNoExistent() throws Exception {
		String url = "/image/findByRegionAndProvider?region_id=2&provider_id=1";
		List<ImageIso> images = new ArrayList<ImageIso>();
		images.add(wellImage);

		Mockito.when(imageService.findByRegionIdAndProviderId(1,1)).thenReturn(images);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}
	

}
