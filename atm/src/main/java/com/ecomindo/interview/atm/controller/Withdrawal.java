package com.ecomindo.interview.atm.controller;

import com.ecomindo.interview.atm.exception.CliDbAbleException;
import com.ecomindo.interview.atm.service.AtmService;

public class Withdrawal implements CliDbProcess{

	public static void main(String[] args) throws CliDbAbleException {
		CliDbAppProcessor.process(new Withdrawal(), args);
	}

	@Override
	public void process(String[] args) {
		String withdrawlAmount = args[0];
		AtmService.withdrawl(withdrawlAmount);
	}

}
