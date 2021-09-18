package com.ecomindo.interview.bank;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.ecomindo.interview.bank.exception.OverdraftException;
import com.ecomindo.interview.bank.model.Account;

public class Transfer {

	public static void main(String[] args) throws SQLException {
		DB.startDatabase();

		try {
			final EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("br.com.fredericci.pu");
			final EntityManager entityManager = entityManagerFactory.createEntityManager();

			TypedQuery<Account> userfind = entityManager.createQuery("from Account a where a.loggedIn = true",
					Account.class);
			Account user;
			try {
				user = userfind.getSingleResult();
				double lastBalance = user.getBalance() - Double.valueOf(args[1]);
				if (lastBalance>=0) {
					try {
						TypedQuery<Account> transferfind = entityManager.createQuery("from Account a where a.login = ?1",
								Account.class);
						Account transferAcc;
						transferfind.setParameter(1, args[0]);
						transferAcc = transferfind.getSingleResult();
						
						transferAcc.setBalance(transferAcc.getBalance() + Double.valueOf(args[1]));
						user.setBalance(lastBalance);
						
						entityManager.getTransaction().begin();
						entityManager.persist(transferAcc);
						entityManager.persist(user);
						entityManager.getTransaction().commit();
						System.out.println("You have transfered from " + user.getLogin() + " to " + transferAcc.getLogin() + " for the amount of " + Double.valueOf(args[1]));
						System.out.println("Your current balance is " + user.getBalance());
					} catch (NoResultException e) {
						System.out.println("Your transaction was aborted due to recipient of funds is not valid!" + args[0]);
					}
				} else {
					System.out.println("Your transaction was aborted due to insufficient funds!\nYou balance is " + user.getBalance());
				}
			} catch (NoResultException e) {
				System.out.println("Please login first! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.stopDatabase();
		}
	}

}
