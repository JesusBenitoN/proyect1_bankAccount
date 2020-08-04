package com.bootcamp.webflux.proyect1_bankAccounts;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.Customers;

import reactor.core.publisher.Mono;

public class CustomerWebClient {
	
	private WebClient client = WebClient.create("http://localhost:8080");
	
//	public Mono<String> listClientbyId (String id) {
//		@SuppressWarnings("deprecation")
//		Mono<ClientResponse> listClient = client.get()
//			      .uri("/api/v2/customers/"+id)
//			      .accept(MediaType.APPLICATION_JSON_UTF8)
//			      .exchange();;
//				return listClient;
//	}

}
