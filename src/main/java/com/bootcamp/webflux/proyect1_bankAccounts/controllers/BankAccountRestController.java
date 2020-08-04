package com.bootcamp.webflux.proyect1_bankAccounts.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;
import com.bootcamp.webflux.proyect1_bankAccounts.models.services.BankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountRestController {
	
	@Autowired
	private BankAccountService service;
	
	@SuppressWarnings("deprecation")
	@GetMapping
	public Mono<ResponseEntity<Flux<BankAccount>>> list() {
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll()));
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/{id}")
	public Mono<ResponseEntity<BankAccount>> seeDetail(@PathVariable String id) {
		return service.findById(id).map(r -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(r))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping
	public Mono<ResponseEntity<BankAccount>> create(@RequestBody BankAccount bankaccount){
		
		return service.save(bankaccount).map(b -> 
				ResponseEntity
				.created(URI.create("/api/bankAccount/".concat(b.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(b));
	}
	
	@SuppressWarnings("deprecation")
	@PutMapping("/{id}")
	public Mono<ResponseEntity<BankAccount>> edit(@RequestBody BankAccount bankaccount, @PathVariable String id){
		return service.findById(id).flatMap(e -> {
			e.setCustomerId(bankaccount.getCustomerId());
			e.setNameAccount(bankaccount.getNameAccount());
			e.setAmount(bankaccount.getAmount());
			e.setAmountTotal(bankaccount.getAmountTotal());
			return service.save(e);
		}).map(r -> ResponseEntity.created(URI.create("/api/bankAccount/".concat(r.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(r))
		.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
		return service.findById(id).flatMap(d -> {
			return service.delete(d).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		})
		.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
