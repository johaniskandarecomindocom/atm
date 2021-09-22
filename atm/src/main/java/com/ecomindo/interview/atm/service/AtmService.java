package com.ecomindo.interview.atm.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.ecomindo.interview.atm.model.Account;

public class AtmService {

	public static void logout(String userName) {
		final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
	
		TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true and a.login=?1", Account.class);
	
		try {
			userfind.setParameter(1, userName);
			Account user = userfind.getSingleResult();
			user.setLoggedIn(false);
			
		    entityManager.getTransaction().begin();
			entityManager.persist(user);
		    entityManager.getTransaction().commit();
			System.out.println("Thank you user " + user.getLogin() + " for using this ATM!");
		} catch (NoResultException e) {
			System.out.println("Could not find logged in user " + userName + "!");
		}
	}

	public static void transfer(String transferToAccountName, String transferAmount) {
		final EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("br.com.fredericci.pu");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
	
		TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true",
				Account.class);
		Account user;
		try {
			user = userfind.getSingleResult();
			double lastBalance = user.getBalance() - Double.valueOf(transferAmount);
			if (lastBalance>=0) {
				try {
					TypedQuery<Account> transferfind = entityManager.createQuery("from Account a where a.login = ?1",
							Account.class);
					Account transferAcc;
					transferfind.setParameter(1, transferToAccountName);
					transferAcc = transferfind.getSingleResult();
					
					transferAcc.setBalance(transferAcc.getBalance() + Double.valueOf(transferAmount));
					user.setBalance(lastBalance);
					
					entityManager.getTransaction().begin();
					entityManager.persist(transferAcc);
					entityManager.persist(user);
					entityManager.getTransaction().commit();
					System.out.println("You have transfered from " + user.getLogin() + " to " + transferAcc.getLogin() + " for the amount of " + Double.valueOf(transferAmount));
					System.out.println("Your current balance is " + user.getBalance());
				} catch (NoResultException e) {
					System.out.println("Your transaction was aborted due to recipient of funds is not valid!" + transferToAccountName);
				}
			} else {
				System.out.println("Your transaction was aborted due to insufficient funds!\nYou balance is " + user.getBalance());
			}
		} catch (NoResultException e) {
			System.out.println("Please login first! ");
		}
	}

	public static void withdrawl(String withdrawlAmount) {
		final EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("br.com.fredericci.pu");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
	
		TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true",
				Account.class);
		Account user;
		try {
			user = userfind.getSingleResult();
			double lastBalance = user.getBalance() - Double.valueOf(withdrawlAmount);
			if (lastBalance>=0) {
				user.setBalance(lastBalance);
	
				entityManager.getTransaction().begin();
				entityManager.persist(user);
				entityManager.getTransaction().commit();
				System.out.println("You have withdrawn " + user.getLogin() + " for the amount of " + Double.valueOf(withdrawlAmount));
				System.out.println("Your current balance is " + user.getBalance());
			} else {
				System.out.println("Your transaction was aborted due to insufficient funds!\nYou balance is " + user.getBalance());
			}
		} catch (NoResultException e) {
			System.out.println("Please login first! ");
		}
	}

	public static void deposit(String balance) {
		final EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("br.com.fredericci.pu");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
	
		TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true",
				Account.class);
		Account user;
		try {
			user = userfind.getSingleResult();
			user.setBalance(user.getBalance() + Double.valueOf(balance));
	
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			System.out.println(
					"You have deposited " + user.getLogin() + " for the amount of " + Double.valueOf(balance));
			System.out.println("Your current balance is " + user.getBalance());
		} catch (NoResultException e) {
			System.out.println("Please login first! ");
		}
	}

	public static void login(String userName) {
		final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true", Account.class);
		try {
			Account user = userfind.getSingleResult();
			System.out.println("Please logout user " +user.getLogin()+ " first!");
		} catch (NoResultException e) {
			Account user = entityManager.find(Account.class, userName);
		    if (user==null) {
		    	user =  new Account();
		    	user.setLogin(userName);
		    	user.setBalance(0d);		            
		    }
			user.setLoggedIn(true);
		    entityManager.getTransaction().begin();
			entityManager.persist(user);
		    entityManager.getTransaction().commit();
		    System.out.println ("Hello "+ user.getLogin());
		    System.out.println ("Your current balance is "+ user.getBalance());
		}
	}

}
