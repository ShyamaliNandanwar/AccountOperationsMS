package com.examplebank.account;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountOperations {
	private static Map<Integer, Account> accounts = null;
	
	@Autowired
	AccountRepository repository;

	static {
		accounts = new HashMap<Integer, Account>();

		Account jamesAccount = new Account();
		jamesAccount.setAccountName("James Cooper");
		jamesAccount.setAccountNumber(1234567890);
		jamesAccount.setBalance(18000);
		jamesAccount.setCustomerID(100);
		jamesAccount.setDateOpened((new java.util.Date()).toString());
		jamesAccount.setInterest(0.08);
		jamesAccount.setOpeningBalance(18000);

		accounts.put(jamesAccount.getAccountNumber(), jamesAccount);
	}
	
	// http://localhost:8080/AccountOperationsMS/accounts + GET
	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public Iterable<Account> getAllAccount(){
		return repository.findAll();
		//return accounts.values();
	}
	
	@RequestMapping(value = "/accounts/{accNumber}", method = RequestMethod.GET)
	public ResponseEntity<Account> getAccount(@PathVariable("accNumber") Integer accNumber) {
		ResponseEntity<Account> response = null;
		Optional<Account> foundAccount = repository.findById(accNumber);
		if (foundAccount.isPresent()) {
			Account account = foundAccount.get();
			response = new ResponseEntity<Account>(account, HttpStatus.OK);
		}else {
			response = new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	
	@PostMapping(path = "/accounts")
	public ResponseEntity<String> createAccount(@RequestBody Account newAccount){
		repository.save(newAccount);
		ResponseEntity<String> response = 
				new ResponseEntity<String>("Account Created", HttpStatus.CREATED);
		accounts.put(newAccount.getAccountNumber(), newAccount);
		return response;
	}
	
	@PostMapping(path="/accounts/{accNumber}/deposit")
	public ResponseEntity<String> deposit(@PathVariable("accNumber") Integer accNumber, @RequestBody Transaction transaction){
		ResponseEntity<String> response = null;
		Optional<Account> account =  repository.findById(accNumber);
		if(account.isPresent()) {
			//Getting the Account object
			Account accountObj = account.get();
			//Updating Transaction nature
			transaction.setNatureOfTransaction("deposit");
			//Changing the Account balance
			accountObj.setBalance(transaction.getAmount() + accountObj.getBalance());
			//Create Transaction record and associate it with Account.
			List<Transaction> transactions = accountObj.getTransactions();
			transactions.add(transaction);
			accountObj.setTransactions(transactions);
			//Perform save to database.
			repository.save(accountObj);
			response = new ResponseEntity<String>("Deposit Successful", HttpStatus.CREATED);
		}else {
			response = new ResponseEntity<String>("Account Not Found", HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@PostMapping(path="/accounts/{accNumber}/withdraw")
	public ResponseEntity<String> withdraw(@PathVariable("accNumber") Integer accNumber, @RequestBody Transaction transaction){
		ResponseEntity<String> response = null;
		Optional<Account> account =  repository.findById(accNumber);
		if(account.isPresent()) {
			//Getting the Account object
			Account accountObj = account.get();
			//Updating Transaction nature
			transaction.setNatureOfTransaction("withdraw");
			//Changing the Account balance
			accountObj.setBalance(accountObj.getBalance()-transaction.getAmount() );
			//Create Transaction record and associate it with Account.
			List<Transaction> transactions = accountObj.getTransactions();
			transactions.add(transaction);
			accountObj.setTransactions(transactions);
			//Perform save to database.
			repository.save(accountObj);
			response = new ResponseEntity<String>("Withdraw Successful", HttpStatus.CREATED);
		}else {
			response = new ResponseEntity<String>("Account Not Found", HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@RequestMapping(path="/accounts/{accNumber}", method = RequestMethod.PUT)
	public ResponseEntity<Account> updateAccount(@PathVariable("accNumber") Integer accNumber,@RequestBody Account newAccount){
		ResponseEntity<Account> response = null;
		Optional<Account> foundAccount = repository.findById(accNumber);
		if (foundAccount.isPresent()) {
			Account account = foundAccount.get();
			//account.setInterest(0.7);
			repository.save(newAccount);
			response = new ResponseEntity<Account>(newAccount, HttpStatus.OK);
		}else {
			response = new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
}

