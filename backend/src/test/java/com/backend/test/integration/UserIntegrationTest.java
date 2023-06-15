package com.backend.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.backend.controllers.UserController;
import com.backend.entities.User;
import com.backend.entities.User.ROLE;
import com.backend.service.UsersService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
public class UserIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsersService userService;

	private User wellUser = new User();

	String jsonUser = "{\"id\":null,\"name\":\"Aitor\",\"surname\":\"Llanos\",\"username\":\"aitorCapgemini\",\"email\":\"aitor@capgemini.com\""
			+ ",\"password\":\"1234\",\"state\":null,\"role\":\"USER\",\"passwordRepeated\":\"1234\"}";


	@BeforeEach
	public void setUp() {
		wellUser.setName("Aitor");
		wellUser.setSurname("Llanos");
		wellUser.setUsername("aitorCapgemini");
		wellUser.setEmail("aitor@capgemini.com");
		wellUser.setPassword("1234");
		wellUser.setPasswordRepeated("1234");
		wellUser.setRole(ROLE.USER);
	}




	
	@Test
	public void loginWithBadPassword() throws Exception {
		String url = "/user/login";
		String jsonString = "{\"username\":\"aitorCapgemini\",\"password\":\"12345\"}";

		
		Mockito.when(userService.login("aitorCapgemini", "1234")).thenReturn(wellUser);

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals("", content);

	}
	
	@Test
	public void loginWithBadUsername() throws Exception {
		String url = "/user/login";
		String jsonString = "{\"username\":\"notRegistered\",\"password\":\"1234\"}";

		
		Mockito.when(userService.login("aitorCapgemini", "1234")).thenReturn(wellUser);

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals("", content);

	}
	
	@Test
	public void loginWithEmptyParams() throws Exception {
		String url = "/user/login";
		String jsonString = "{\"username\":\"\",\"password\":\"\"}";

	
		Mockito.when(userService.login("aitorCapgemini", "1234")).thenReturn(wellUser);

		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals("", content);

	}
	
	
	@Test
	public void signup() throws Exception {
		String url ="/user/signup?username=aitorCapgemini&name=Aitor&surname=Llanos&email=aitor@capgemini.com&password=1234&passwordRepeated=1234";

		MvcResult result = mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String textResponse = response.getContentAsString();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(textResponse, "success");
	}
	

	@Test
	public void signupEmptyParams() throws Exception {
		String url ="/user/signup";

		MvcResult result = mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String textResponse = response.getContentAsString();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals(textResponse, "");
	}
	
	
	@Test
	public void differentPasswordsSignUp() throws Exception {
		String url ="/user/signup?username=aitorCapgemini&name=Aitor&surname=Llanos&email=aitor@capgemini.com&password=1234&passwordRepeated=12345";


		MvcResult result = mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String textResponse = response.getContentAsString();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(textResponse, "EQUAL_PASS");
	}
	

}
