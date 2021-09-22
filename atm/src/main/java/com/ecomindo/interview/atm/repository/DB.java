package com.ecomindo.interview.atm.repository;

import java.sql.SQLException;

import org.h2.tools.Server;

public class DB {
	private static Server s = null;

    public static void startDatabase() throws SQLException {
    	DB.s = new Server();
        DB.s.runTool("-tcp", "-web", "-ifNotExists");
    }
    
    public static void stopDatabase() throws SQLException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DB.s.shutdown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
