package com.ecomindo.interview.atm.controller;

import com.ecomindo.interview.atm.exception.CliDbAbleException;
import com.ecomindo.interview.atm.service.AtmService;

public class Deposit implements CliDbProcess{

	public static void main(String[] args) throws CliDbAbleException {
		CliDbAppProcessor.process(new Deposit(), args);
	}

	@Override
	public void process(String[] args) {
		String balance = args[0];
		AtmService.deposit(balance);
	}

}
