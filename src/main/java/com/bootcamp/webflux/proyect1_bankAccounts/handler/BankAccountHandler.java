package com.bootcamp.webflux.proyect1_bankAccounts.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.net.URI;

import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp.webflux.proyect1_bankAccounts.models.documents.BankAccount;
import com.bootcamp.webflux.proyect1_bankAccounts.models.services.BankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BankAccountHandler {

	@Autowired
	private BankAccountService service;
	
	@Autowired
	private Validator validator;
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> list(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), BankAccount.class);
		
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> see(ServerRequest request){
		String id = request.pathVariable("id");
		
		return service.findById(id).flatMap(response -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(response))
				.switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> create(ServerRequest request){
		//se puebla los datos del objeto en el mono bankAccount
		Mono<BankAccount> bankAccount = request.bodyToMono(BankAccount.class);
		
		//conervir el mono de bankAccount en un objeto para guardar en db
		return bankAccount.flatMap(b -> {
			Errors errors = new BeanPropertyBindingResult(b, BankAccount.class.getName());
			//validamos si hay error
			validator.validate(b, errors);
			if(errors.hasErrors()) {
			//majenamos el error
			//obtenemos la lista del error y convertimos a un iterable
			return Flux.fromIterable(errors.getFieldErrors())
					//lo modificamos a tipo String
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					//convertimos a Mono list
					.collectList()
					//convertimos a Mono.serverResponse
					.flatMap(lista -> ServerResponse.badRequest().body(fromObject(lista)));
			}else {
				return bankAccount.flatMap(bk -> service.save(bk)).flatMap(r -> ServerResponse
						.created(URI.create("/api/bankAccount/".concat(r.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(BodyInserters.fromObject(r)));
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> edit(ServerRequest request){
		String id = request.pathVariable("id");
		Mono<BankAccount> bankAccount = request.bodyToMono(BankAccount.class);
		Mono<BankAccount> dbBankAccount = service.findById(id);
		
		return dbBankAccount.zipWith(bankAccount, (db, req) -> {
			db.setCustomerId(req.getCustomerId());
			db.setNameAccount(req.getNameAccount());
			db.setAmount(req.getAmount());
			db.setAmountTotal(req.getAmountTotal());
			return db;
		}).flatMap(response -> ServerResponse
				.created(URI.create("/api/bankAccount/".concat(response.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.save(response), BankAccount.class)
				//manejamos el error en caso el id sea vacio o no exista
				.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Mono<ServerResponse> delete(ServerRequest request){
		String id = request.pathVariable("id");
		Mono<BankAccount> bankAccountdb = service.findById(id);
		
		return bankAccountdb.flatMap(b -> service.delete(b).then(ServerResponse.noContent().build()))
				//manejamos el error en caso el id sea vacio o no exista - status 404 no found
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
