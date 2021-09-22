package com.ecomindo.interview.atm.controller;

import com.ecomindo.interview.atm.exception.CliDbAbleException;
import com.ecomindo.interview.atm.service.AtmService;

public class Login implements CliDbProcess{

	public static void main(String[] args) throws CliDbAbleException {
		CliDbAppProcessor.process(new Login(), args);
	}

	@Override
	public void process(String[] args) {
		String userName = args[0];			
		AtmService.login(userName);
	}

}
