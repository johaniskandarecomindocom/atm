package com.ecomindo.interview.atm.controller;

import com.ecomindo.interview.atm.exception.CliDbAbleException;
import com.ecomindo.interview.atm.repository.DB;

public class CliDbAppProcessor {

	public static <T extends CliDbProcess> void process(T object, String[] args) throws CliDbAbleException {
		try {
			DB.startDatabase();
			try {
				object.process(args);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.stopDatabase();
			}
		} catch (Exception e) {
			throw new CliDbAbleException(e.getMessage(), e);
		}
	}

}
