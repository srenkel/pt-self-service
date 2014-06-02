/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

/**
 *
 */
public abstract class DatabaseManager {
	
	protected static final String PERSISTENCE_UNIT_CORE = "com.capgemini.pt.selfservice";
	protected static final String PERSISTENCE_UNIT_PUPPET_DASHBOARD = "com.capgemini.pt.puppet.dashboard";

	private final EntityManager em;

	public DatabaseManager(final String persistenceUnitName) {
		this.em = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	protected <T> void persist(final T obj) {
		doTransacted(new TxAction() {

			@Override
			public void exec(EntityManager em) {
				em.persist(obj);
			}
		});
	}

	protected <T> T merge(final T obj) {
		return doTransacted(new TxResultAction<T>() {

			@Override
			public T exec(EntityManager em) {
				return em.merge(obj);
			}
		});
	}

	protected <T> T find(final Class<T> resourceClass, final Long oid) {
		return em.find(resourceClass, oid);
	}

	/**
	 * Assures that a transaction has been started.
	 */
	protected void doTransacted(final TxAction action) {
		final EntityTransaction tx = em.getTransaction();
		if (tx.isActive()) {
			action.exec(em);
		} else {
			try {
				tx.begin();
				action.exec(em);
				tx.commit();
			} catch (RollbackException e) {
				throw e;
			} catch (RuntimeException e) {
				tx.setRollbackOnly();
				throw e;
			}
		}
	}

	/**
	 * Assures that a transaction has been started.
	 */
	protected <T> T doTransacted(final TxResultAction<T> action) {
		final EntityTransaction tx = em.getTransaction();
		if (tx.isActive()) {
			return action.exec(em);
		} else {
			try {
				tx.begin();
				T result = action.exec(em);
				tx.commit();
				return result;
			} catch (RollbackException e) {
				throw e;
			} catch (RuntimeException e) {
				tx.setRollbackOnly();
				throw e;
			}
		}
	}

	/**
	 * Inner interface for transacted actions
	 */
	protected static interface TxAction {

		/**
		 * Do something transacted with a given entity manager. The transaction
		 * handling and other aspects are handled outside this code block.
		 * 
		 * @param em
		 *            The entity manager.
		 */
		void exec(EntityManager em);

	}

	/**
	 * Inner interface for transacted actions
	 */
	protected static interface TxResultAction<T> {

		/**
		 * Do something transacted with a given entity manager. The transaction
		 * handling and other aspects are handled outside this code block.
		 * 
		 * @param em
		 *            The entity manager.
		 */
		T exec(EntityManager em);

	}
}
