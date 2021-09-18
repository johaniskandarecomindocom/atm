package com.ecomindo.interview.bank;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.h2.tools.Server;

import com.ecomindo.interview.bank.model.Account;

public class Login {
	
	public static void main(String[] args) throws SQLException {
		DB.startDatabase();
		
		try {
	        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
	        final EntityManager entityManager = entityManagerFactory.createEntityManager();

	        TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true", Account.class);

	        try {
	        	Account user = userfind.getSingleResult();
	        	System.out.println("Please logout user " +user.getLogin()+ " first!");
	        } catch (NoResultException e) {
		        Account user = entityManager.find(Account.class, args[0]);
		        if (user==null) {
		        	user =  new Account();
		        	user.setLogin(args[0]);
		        	user.setBalance(0d);		            
		        }
	        	user.setLoggedIn(true);
	            entityManager.getTransaction().begin();
	        	entityManager.persist(user);
	            entityManager.getTransaction().commit();
		        System.out.println ("Hello "+ user.getLogin());
		        System.out.println ("Your current balance is "+ user.getBalance());
	        }
	        
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			DB.stopDatabase();
		}
	}

}
