package com.examplebank.account;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Transactions")
public class Transaction {
	@Id
	@Column(name ="TRAN_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(targetEntity=Account.class)
	@JoinColumn(name = "ACCOUNT_NO", referencedColumnName = "ACCOUNT_NO")
	@JsonIgnore
	private Account account;
	
	@Column(name ="TRAN_AMOUNT")
	private double amount;
	
	@Column(name ="TRAN_DATE")
	private String transDate;
	
	@Column(name ="TRAN_NATURE")
	private String natureOfTransaction;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getNatureOfTransaction() {
		return natureOfTransaction;
	}
	public void setNatureOfTransaction(String natureOfTransaction) {
		this.natureOfTransaction = natureOfTransaction;
	}
}
