package com.bootcamp.webflux.proyect1_bankAccounts.models.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customers {
	
	private String id;
	
	private String name;
	
	private TypeCustomer typeCustomer;
	
	private TypeDocument typeDocument;
	
	private String numbDocument;
	
	public Customers(String name, TypeCustomer typeCustomer, TypeDocument typeDocument, String numbDocument) {
		this.name = name;
		this.typeCustomer = typeCustomer;
		this.typeDocument = typeDocument;
		this.numbDocument = numbDocument;
	}

	public Customers() {
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

	public TypeCustomer getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(TypeCustomer typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getNumbDocument() {
		return numbDocument;
	}

	public void setNumbDocument(String numbDocument) {
		this.numbDocument = numbDocument;
	}

	@Override
	public String toString() {
		return "Customers [id=" + id + ", name=" + name + ", typeCustomer=" + typeCustomer + ", typeDocument="
				+ typeDocument + ", numbDocument=" + numbDocument + "]";
	}
	
	
	
}
