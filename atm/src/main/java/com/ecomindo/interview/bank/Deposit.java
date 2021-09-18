package com.ecomindo.interview.bank;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.ecomindo.interview.bank.model.Account;

public class Deposit {

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
				user.setBalance(user.getBalance() + Double.valueOf(args[0]));

				entityManager.getTransaction().begin();
				entityManager.persist(user);
				entityManager.getTransaction().commit();
				System.out.println(
						"You have deposited " + user.getLogin() + " for the amount of " + Double.valueOf(args[0]));
				System.out.println("Your current balance is " + user.getBalance());
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
