/**
 * Copyright to srenkel 2014
 */
package com.capgemini.pt.core.data.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.capgemini.pt.core.data.ISelfServiceDataManager;
import com.capgemini.pt.entity.ApplicationStatus;
import com.capgemini.pt.entity.Definition;
import com.capgemini.pt.persistence.DatabaseManager;

public class SelfServiceDataManager extends DatabaseManager implements
		ISelfServiceDataManager {

	public SelfServiceDataManager() {
		super(PERSISTENCE_UNIT_CORE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Definition> getDefinitions() {
		final Query query = getEntityManager().createNamedQuery(
				"Definition.findAll");
		return query.getResultList();
	}

	@Override
	public Definition findDefinition(String name) throws NoResultException,
			NonUniqueResultException {
		final Query query = getEntityManager().createNamedQuery(
				"Definition.findByName");
		query.setParameter("name", name);
		return (Definition) query.getSingleResult();
	}

	@Override
	public Definition storeDefinition(Definition definition) {
		if (definition.getOid() != null) {
			return merge(definition);
		} else {
			persist(definition);
			return definition;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationStatus> getApplicationStatus() {
		final Query query = getEntityManager().createNamedQuery(
				"ApplicationStatus.findAll");
		return query.getResultList();
	}

	@Override
	public ApplicationStatus storeApplicationStatus(
			ApplicationStatus applicationStatus) {
		if (applicationStatus.getOid() != null) {
			return merge(applicationStatus);
		} else {
			persist(applicationStatus);
			return applicationStatus;
		}
	}

}
