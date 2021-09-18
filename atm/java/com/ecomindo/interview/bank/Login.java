package com.ecomindo.interview.bank;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.Server;

import com.ecomindo.interview.bank.model.Account;

public class Login {
	
	public static void main(String[] args) throws SQLException {
        Login l = new Login();
        l.startDatabase();

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        Account user = entityManager.find(Account.class, args[0]);
        if (user==null) {
        	user =  new Account();
        	user.setLogin(args[0]);
        	user.setBalance(0d);
        	user.setLoggedIn(true);
            
            entityManager.getTransaction().begin();
        	entityManager.persist(user);
            entityManager.getTransaction().commit();
        }
        System.out.println ("Hello "+ user.getLogin());
        System.out.println ("Your current balance is "+ user.getBalance());
        
	}
    private void startDatabase() throws SQLException {
        try {
			new Server().runTool("-tcp", "-web", "-ifNotExists");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
