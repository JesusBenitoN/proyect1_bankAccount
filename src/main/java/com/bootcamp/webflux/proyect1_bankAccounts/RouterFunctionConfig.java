package com.bootcamp.webflux.proyect1_bankAccounts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp.webflux.proyect1_bankAccounts.handler.BankAccountHandler;

import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> routes(BankAccountHandler handler){
		
		return RouterFunctions.route(RequestPredicates.GET("/api/v2/bankAccount"), handler::list)
				.andRoute(RequestPredicates.GET("/api/v2/bankAccount/{id}"), handler::see)
				.andRoute(RequestPredicates.POST("/api/v2/bankAccount"), handler::create)
				.andRoute(RequestPredicates.PUT("/api/v2/bankAccount/{id}"), handler::edit)
				.andRoute(RequestPredicates.DELETE("/api/v2/bankAccount/{id}"), handler::delete);
	}

}
