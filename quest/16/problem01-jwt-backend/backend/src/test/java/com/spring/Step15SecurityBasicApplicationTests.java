package com.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class Step15SecurityBasicApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSignupAndLogin() throws Exception {
		// Signup
		String signupJson = """
				{
					"username": "testuser",
					"password": "password123",
					"email": "test@example.com"
				}
				""";
		mockMvc.perform(post("/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(signupJson))
				.andExpect(status().isCreated())
				.andDo(print());

		// Login Success
		String loginJson = """
				{
					"username": "testuser",
					"password": "password123"
				}
				""";
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
				.andExpect(status().isOk())
				.andDo(print());

		// Login Failure (Wrong password)
		String badLoginJson = """
				{
					"username": "testuser",
					"password": "wrongpassword"
				}
				""";
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(badLoginJson))
				.andDo(print());
	}
}
