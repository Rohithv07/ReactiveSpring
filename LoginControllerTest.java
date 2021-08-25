package com.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.model.Accounts;
import com.example.service.LoginRegistrationServiceImpl;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	LoginRegistrationServiceImpl loginRegistratioService;
	
	private Accounts account;
	
	@BeforeEach
	public void setupTest() {
		System.out.println("Before Each called...");
		account = new Accounts(1, "ABC", "abc", "user", "pass", "add", "stae", "country", "email", "pan", "12345",
				"12/12/12");
	}
	
	@Test
	public void testRegister() {
		Mockito.when(loginRegistratioService.registerAccount(account)).thenReturn(Mono.just(account));
		webTestClient.post().uri("register", account).exchange().expectStatus().isOk();
	}


}
