package com.example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.model.Accounts;
import com.example.model.Login;

import reactor.test.StepVerifier;

@SpringBootTest
public class LoginRegistrationServiceTest {
	
	LoginRegistrationServiceImpl loginRegistrationService;
	
	@BeforeEach
	public void intialize() {
		loginRegistrationService = new LoginRegistrationServiceImpl();
	}

	@Test
	public void testRegister() {
		Accounts a1 = new Accounts(4, "asas", "sakjdl", "sdfkld", "sdkfjsk", "sdkfs", "sdfkjsd", "woeiv", "owieoiw", "dfuwif", "dfalkjd", "sdlflk");
		loginRegistrationService.registerAccount(a1);
		StepVerifier.create(loginRegistrationService.registerAccount(a1)).expectNext(a1).expectComplete().verify();
	}
	
	@Test
	public void testCheckCredentialsTrue() {
		assertTrue(loginRegistrationService.checkPassword(new Login("sdfkld","sdkfjsk")));
	}
	
	@Test
	public void testCheckCredentialsFalse() {
		assertFalse(loginRegistrationService.checkPassword(new Login("sdfkld","sdkfjsk")));
	}
}
