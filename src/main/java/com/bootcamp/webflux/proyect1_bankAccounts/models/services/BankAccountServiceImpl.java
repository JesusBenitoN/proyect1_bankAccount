package com.bootcamp.webflux.proyect1_bankAccounts.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.webflux.proyect1_bankAccounts.models.dao.BankAccountDao;
import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;
import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.Customers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements BankAccountService{
	
	@Autowired
	private BankAccountDao dao;
	
	@Autowired
	private WebClient webClientCustomer;

	@Override
	public Flux<BankAccount> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<BankAccount> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<BankAccount> save(BankAccount bankAccount) {
		return dao.save(bankAccount);
	}

	@Override
	public Mono<Void> delete(BankAccount bankAccount) {
		return dao.delete(bankAccount);
	}

	@Override
	public Mono<BankAccount> findByNameAccount(String nameAccount) {
		return dao.findByNameAccount(nameAccount);
	}

	@Override
	public Mono<BankAccount> findByCustomerId(String customerId) {
		return dao.findByCustomerId(customerId);
	}

	public Customers findIdApi(String id) {
		Customers customers;
		Mono<Customers> monoCustomer;
		if(webClientCustomer.get().uri("/api/customers/"+id).exchange().block().statusCode()!=HttpStatus.NOT_FOUND) {
		monoCustomer = webClientCustomer
			.method(HttpMethod.GET)
			.uri("/api/customers/{id}", id)
			.retrieve()
			.bodyToMono(Customers.class);
		
		customers = monoCustomer.block();
		
		}else {
			customers = null;
			
		}
		return customers;
		
	}
	
}
