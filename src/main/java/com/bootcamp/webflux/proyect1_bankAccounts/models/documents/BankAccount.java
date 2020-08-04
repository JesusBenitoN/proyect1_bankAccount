package com.bootcamp.webflux.proyect1_bankAccounts.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bankAccount")
public class BankAccount {
	
	@Id
	private String id;
	
	private String customerId;
	
	private String nameAccount;
	
	private Double amount;
	
	private Double amountTotal;

	public BankAccount() {}

	public BankAccount(String customerId, String nameAccount, Double amount, Double amountTotal) {
		this.customerId = customerId;
		this.nameAccount = nameAccount;
		this.amount = amount;
		this.amountTotal = amountTotal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNameAccount() {
		return nameAccount;
	}

	public void setNameAccount(String nameAccount) {
		this.nameAccount = nameAccount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}
	
}
