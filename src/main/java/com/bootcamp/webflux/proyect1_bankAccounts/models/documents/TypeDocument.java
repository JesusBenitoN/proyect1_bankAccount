package com.bootcamp.webflux.proyect1_bankAccounts.models.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeDocument {
	
	private String id;
	
	private String name;
	
	public TypeDocument(String name) {
		this.name = name;
	}

	public TypeDocument() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
