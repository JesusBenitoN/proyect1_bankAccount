package com.bootcamp.webflux.proyect1_bankAccounts.models.services;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {
	
	public Flux<BankAccount> findAll();
	
	public Mono<BankAccount> findById(String id);
	
	public Mono<BankAccount> save(BankAccount bankAccount);
	
	public Mono<Void> delete(BankAccount bankAccount);
	
	public Mono<BankAccount> findByNameAccount(String nameAccount);
	
	public Mono<BankAccount> findByCustomerId(String customerId);

}
