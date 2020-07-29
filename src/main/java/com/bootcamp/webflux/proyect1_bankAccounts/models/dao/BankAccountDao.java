package com.bootcamp.webflux.proyect1_bankAccounts.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;

import reactor.core.publisher.Mono;

public interface BankAccountDao extends ReactiveMongoRepository<BankAccount, String>{
	
	public Mono<BankAccount> findByNameAccount(String nameAccount);
	
	public Mono<BankAccount> findByCustomerId(String customerId);
	

}
