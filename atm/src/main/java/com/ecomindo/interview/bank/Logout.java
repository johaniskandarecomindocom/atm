package com.ecomindo.interview.bank;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.h2.tools.Server;

import com.ecomindo.interview.bank.model.Account;

public class Logout {
	
	private static Server s = null;
	
	public static void main(String[] args) throws SQLException {
		DB.startDatabase();
		
		try {
	        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
	        final EntityManager entityManager = entityManagerFactory.createEntityManager();

	        TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true and a.login=?1", Account.class);

	        try {
	        	userfind.setParameter(1, args[0]);
	        	Account user = userfind.getSingleResult();
	        	user.setLoggedIn(false);
	        	
	            entityManager.getTransaction().begin();
	        	entityManager.persist(user);
	            entityManager.getTransaction().commit();
	        	System.out.println("Thank you user " + user.getLogin() + " for using this ATM!");
	        } catch (NoResultException e) {
	        	System.out.println("Could not find logged in user " + args[0] + "!");
	        }
	        
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			DB.stopDatabase();
		}
	}

}
