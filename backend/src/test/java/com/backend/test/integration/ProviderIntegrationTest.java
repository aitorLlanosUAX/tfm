package com.backend.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

import com.backend.controllers.ProviderController;
import com.backend.entities.terraform.terraformDatabase.Provider;
import com.backend.service.ProviderService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProviderController.class)
public class ProviderIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProviderService providerService;

	private Provider provider = new Provider();

	String jsonString = "{\"id\":0,\"name\":\"Aws\",\"pathCredentials\":\"C:/terraform-files/test/test.tf\"}";
	String jsonStringList = "[{\"id\":0,\"name\":\"Aws\",\"pathCredentials\":\"C:/terraform-files/test/test.tf\"}]";

	@BeforeEach
	public void setup() {
		provider.setName("Aws");
		provider.setPathCredentials("C:/terraform-files/test/test.tf");
	}

	@Test
	public void normalList() throws Exception {
		String url = "/provider/list";
		List<Provider> providers = new ArrayList<Provider>();
		providers.add(provider);

		Mockito.when(providerService.findAll()).thenReturn(providers);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonStringList, response.getContentAsString());

	}

	@Test
	public void normalListEmpty() throws Exception {
		String url = "/provider/list";

		Mockito.when(providerService.findAll()).thenReturn(new ArrayList<Provider>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[]", response.getContentAsString());

	}

	@Test
	public void normalFindById() throws Exception {
		String url = "/provider/findById?id=1";

		Mockito.when(providerService.findById(1)).thenReturn(provider);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonString, response.getContentAsString());

	}

	@Test
	public void findByIdWithNoId() throws Exception {
		String url = "/provider/findById?id=2";

		Mockito.when(providerService.findById(1)).thenReturn(provider);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("", response.getContentAsString());

	}

	@Test
	public void deleteNormal() throws Exception {
		String url = "/provider/delete?id=1";

		Mockito.when(providerService.findById(1)).thenReturn(provider);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void deleteWithNoId() throws Exception {
		String url = "/provider/delete?id=2";

		Mockito.when(providerService.findById(2)).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(String.valueOf(HttpStatus.NOT_FOUND.value()), response.getContentAsString());

	}

}
