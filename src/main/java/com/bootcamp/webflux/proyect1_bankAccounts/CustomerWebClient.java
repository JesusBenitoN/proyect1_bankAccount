package com.bootcamp.webflux.proyect1_bankAccounts;

//import java.util.function.Function;
//
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.Customers;

import reactor.core.publisher.Mono;

@Configuration
public class CustomerWebClient {
//	private String urlCustomer ="http://localhost:8080/api/customers";
//	private String urlCustomer = "http://servicio-customers/api/customers";
	
//	@Bean
//	@LoadBalanced
//	public WebClient.Builder lsitCustomerId(String id) {
//		return WebClient.builder().baseUrl(urlCustomer+"/"+id);
//	}
	
	public Mono<Customers> listCustomer (@PathVariable String id){
		Mono<Customers> client;
        WebClient webClient=WebClient.create("http://localhost:8080/");
        if(webClient.get().uri("/api/customers/"+id).exchange().block().statusCode()!=HttpStatus.NOT_FOUND) {
            client=webClient.get().uri("/api/customers/"+id)
                    .retrieve()
                    .bodyToMono(Customers.class);
        }else {
            client=null;
        }  
      
        return client;
	}
	
	Mono<Customers> client;
	public Mono<Customers> listClient() {
        WebClient webClient=WebClient.create("http://localhost:8080/");
            return client=webClient.get().uri("/api/customers")
                    .retrieve().bodyToMono(Customers.class);

    }


}
