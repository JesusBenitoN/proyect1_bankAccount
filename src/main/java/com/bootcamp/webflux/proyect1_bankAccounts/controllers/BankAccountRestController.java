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
import org.springframework.web.reactive.function.client.WebClient;

//import com.bootcamp.webflux.proyect1_bankAccounts.CustomerWebClient;
import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;
import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.Customers;
import com.bootcamp.webflux.proyect1_bankAccounts.models.services.BankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountRestController {
	
//	@Autowired
//	private CustomerWebClient customerClient;
	
	@Autowired
	private BankAccountService service;
	
	@GetMapping("/list/{id}")
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
	
	@GetMapping
	public Mono<ResponseEntity<Flux<BankAccount>>> list() {
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.findAll()));
	}
	
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<BankAccount>> seeDetail(@PathVariable String id) {
		return service.findById(id).map(r -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(r))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@PostMapping
	public Mono<ResponseEntity<BankAccount>> create(@RequestBody BankAccount bankaccount){
//		Customers customers = service.findIdApi(bankaccount.getCustomerId());
		String e = "not";
		Customers customers = new Customers();
//		if (customerClient.listCustomer(bankaccount.getId()).subscribe()!=null) {
//			customers = customerClient.listCustomer(bankaccount.getId()).block();
//		}
		if (service.findIdApi(bankaccount.getCustomerId()) != null) {
			customers = service.findIdApi(bankaccount.getCustomerId());
		}
		Mono<BankAccount> dbbankAccount = null;
		
		if (service.findByCustomerId(bankaccount.getCustomerId()) != null) {
			dbbankAccount = service.findByCustomerId(bankaccount.getCustomerId());
		}

		if (!bankaccount.getCustomerId().isEmpty() && !bankaccount.getCustomerId().isEmpty()  && bankaccount.getCustomerId().equals(customers.getId())) {
			if (customers.getTypeCustomer().getName().equals("Personal")) {
			dbbankAccount.map(db -> {
				if (db.getNameAccount().equals(bankaccount.getNameAccount())) {
					bankaccount.equals(null);
				}
				db.equals(bankaccount);
				return db;
			});
			}else if (customers.getTypeCustomer().getName().equals("Business")) {
				dbbankAccount.map(db -> {
					if (db.getNameAccount().equals("Cuenta Corriente") && bankaccount.getNameAccount().equals("Cuenta Ahorro")||
							db.getNameAccount().equals("Cuenta Corriente") && bankaccount.getNameAccount().equals("Cuenta a plazo Fijo")){
						bankaccount.equals(null);
					}
					db.equals(bankaccount);
				return db;
			});
			}
		}
		return service.save(bankaccount).map(b -> 
				ResponseEntity
				.created(URI.create("/api/bankAccount/".concat(b.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(b))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<BankAccount>> edit(@RequestBody BankAccount bankaccount, @PathVariable String id){
		return service.findById(id).flatMap(e -> {
			e.setCustomerId(bankaccount.getCustomerId());
			e.setNameAccount(bankaccount.getNameAccount());
			e.setAmount(bankaccount.getAmount());
			e.setAmountTotal(bankaccount.getAmountTotal());
			return service.save(e);
		}).map(r -> ResponseEntity.created(URI.create("/api/bankAccount/".concat(r.getId())))
				.contentType(MediaType.APPLICATION_JSON)
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
