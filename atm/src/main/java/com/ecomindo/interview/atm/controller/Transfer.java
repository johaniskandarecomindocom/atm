package com.ecomindo.interview.atm.controller;

import com.ecomindo.interview.atm.exception.CliDbAbleException;
import com.ecomindo.interview.atm.service.AtmService;

public class Transfer implements CliDbProcessable {

	public static void main(String[] args) throws CliDbAbleException {
		CliDbAppProcessor.process(new Transfer(), args);
	}

	@Override
	public void process(String[] args) {
		String transferToAccountName = args[0];
		String transferAmount = args[1];		
		AtmService.transfer(transferToAccountName, transferAmount);
	}

}
