package com.bootcamp.webflux.proyect1_bankAccounts;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;
import com.bootcamp.webflux.proyect1_bankAccounts.models.services.BankAccountService;


import reactor.core.publisher.Mono;

//para el mock
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)//RANDOM_PORT
class Proyect1BankAccountsApplicationTests {
	
	@Autowired
	private WebTestClient client;
		
	@Autowired
	private BankAccountService service;

	
	@Test
	void listTest() {
		
		client.get()
		.uri("/api/bankAccount")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(BankAccount.class)
		.consumeWith(response -> {
			List<BankAccount> bankAccounts = response.getResponseBody();
			bankAccounts.forEach(c -> {
				System.out.print(c.getNameAccount());
			});
			Assertions.assertThat(bankAccounts.size()>0).isTrue();
		});
	}
	
	
	@Test
	void seeTest() {
		
		BankAccount bankAccount = service.findByCustomerId("qwe9888").block();
		
		client.get()
		.uri("/api/bankAccount/{id}", Collections.singletonMap("id", bankAccount.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		//cuando el body es un objeto
		.expectBody(BankAccount.class)
		.consumeWith(response -> {
			BankAccount c = response.getResponseBody();
			Assertions.assertThat(c.getId()).isNotEmpty();
			Assertions.assertThat(c.getCustomerId()).isEqualTo("qwe9888");
		});
	}
	
	
	@Test
	void createTest() {
		
		BankAccount bankAccount = new BankAccount("eeer4567", "Cuenta Corriente", 0.00, 150.00);
		client.post()
		.uri("/api/bankAccount")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(bankAccount), BankAccount.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(BankAccount.class)
		.consumeWith(response -> {
			BankAccount c = response.getResponseBody();
			Assertions.assertThat(c.getId()).isNotEmpty();
			Assertions.assertThat(c.getCustomerId()).isEqualTo("eeer4567");
			Assertions.assertThat(c.getNameAccount()).isEqualTo("Cuenta Corriente");
		});

	}

	
	@Test
	void editTest() {
		BankAccount bankAccount = service.findByCustomerId("qwe0990").block();
		
		BankAccount bankAccountEdit = new BankAccount("qwe0990", "Cuenta Corriente", 0.00, 190.00);
		client.put()
		.uri("/api/bankAccount/{id}", Collections.singletonMap("id", bankAccount.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(bankAccountEdit), BankAccount.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(BankAccount.class)
		.consumeWith(response -> {
			BankAccount c = response.getResponseBody();
			Assertions.assertThat(c.getId()).isNotEmpty();
			Assertions.assertThat(c.getNameAccount()).isEqualTo("Cuenta Corriente");
		});
	}
	
	@Test
	void deleteTest() {
		BankAccount bankAccount = service.findByCustomerId("poi89000").block();
		
		client.delete()
		.uri("/api/bankAccount/{id}", Collections.singletonMap("id", bankAccount.getId()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody()
		.isEmpty();
		
	}

}
