package com.bootcamp.webflux.proyect1_bankAccounts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;
import com.bootcamp.webflux.proyect1_bankAccounts.models.services.BankAccountService;


import reactor.core.publisher.Flux;

@SpringBootApplication
public class Proyect1BankAccountsApplication implements CommandLineRunner{
	
	@Autowired
	private BankAccountService service;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(BankAccount.class);

	@Bean
	public WebClient webClientCustomer(WebClient.Builder builder) {
		return builder
			.baseUrl("http://localhost:8080/")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Proyect1BankAccountsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("bankAccount").subscribe();
		
		Flux.just(new BankAccount("qwe9888", "Cuenta Ahorro", 0.00, 500.00),
				new BankAccount("poi89000", "Cuenta a plazo Fijo", 0.00, 100.00),
				new BankAccount("ppp90989", "Cuenta Corriente", 0.00, 800.00),
				new BankAccount("qwe0990", "Cuenta Fijo", 0.00, 120.00))
		.flatMap(bankAccount -> {
			return service.save(bankAccount);
		})
		.subscribe(bankAccount -> log.info("Insert NameAccount: "+ bankAccount.getNameAccount()));
		
		
	}

}
