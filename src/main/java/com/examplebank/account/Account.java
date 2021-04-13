package com.examplebank.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Id
	@Column(name = "ACCOUNT_NO")
	private int accountNumber;
	@Column(name = "ACCOUNT_BALANCE")
	private double balance;
	@Column(name = "ACCOUNT_OPENING_BALANCE")
	private double openingBalance;
	@Column(name = "ACCOUNT_HOLDER_NAME")
	private String accountName;
	@Column(name = "ACCOUNT_CUSTOMER")
	private int customerID;
	@Column(name = "ACCOUNT_RATE_OF_INTEREST")
	private double interest;
	@Column(name = "ACCOUNT_OPENED_ON")
	private String dateOpened;
	
	@OneToMany(targetEntity=Transaction.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "ACCOUNT_NO")
	private java.util.List<Transaction> transactions;
		
	public java.util.List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(java.util.List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(String dateOpened) {
		this.dateOpened = dateOpened;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
}
