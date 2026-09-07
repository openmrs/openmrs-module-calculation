/***
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.calculation.db;

import java.util.List;
import java.util.Locale;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.api.db.hibernate.HibernateUtil;
import org.openmrs.calculation.CalculationRegistration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link CalculationRegistrationDAO}.
 */
public class HibernateCalculationRegistrationDAO implements CalculationRegistrationDAO {

	protected final Log log = LogFactory.getLog(this.getClass());

	private static final String TOKEN = "token";

	private static final String PROVIDER_CLASS_NAME = "providerClassName";

	private final SessionFactory sessionFactory;

	public HibernateCalculationRegistrationDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the current Hibernate session
	 */
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @see CalculationRegistrationDAO#getCalculationRegistration(Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public CalculationRegistration getCalculationRegistration(Integer calculationRegistrationId) {
		return getCurrentSession().find(CalculationRegistration.class, calculationRegistrationId);
	}

	/**
	 * @see CalculationRegistrationDAO#getCalculationRegistrationByUuid(String)
	 */
	@Override
	@Transactional(readOnly = true)
	public CalculationRegistration getCalculationRegistrationByUuid(String uuid) {
		return HibernateUtil.getUniqueEntityByUUID(sessionFactory, CalculationRegistration.class, uuid);
	}

	/**
	 * @see CalculationRegistrationDAO#getCalculationRegistrationByToken(String)
	 */
	@Override
	@Transactional(readOnly = true)
	public CalculationRegistration getCalculationRegistrationByToken(String token) {
		Session session = getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CalculationRegistration> query = builder.createQuery(CalculationRegistration.class);
		Root<CalculationRegistration> root = query.from(CalculationRegistration.class);

		query.select(root)
				.where(builder.equal(
						builder.lower(root.get(TOKEN)),
						token.toLowerCase(Locale.ROOT)
				));

		return session.createQuery(query).uniqueResult();
	}

	/**
	 * @see CalculationRegistrationDAO#getAllCalculationRegistrations()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CalculationRegistration> getAllCalculationRegistrations() {
		Session session = getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CalculationRegistration> query = builder.createQuery(CalculationRegistration.class);
		Root<CalculationRegistration> root = query.from(CalculationRegistration.class);

		query.select(root);

		return session.createQuery(query).getResultList();
	}

	/**
	 * @see CalculationRegistrationDAO#getCalculationRegistrationsByProviderClassname(String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CalculationRegistration> getCalculationRegistrationsByProviderClassname(String providerClassname) {
		Session session = getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CalculationRegistration> query = builder.createQuery(CalculationRegistration.class);
		Root<CalculationRegistration> root = query.from(CalculationRegistration.class);

		query.select(root)
				.where(builder.equal(root.get(PROVIDER_CLASS_NAME), providerClassname));

		return session.createQuery(query).getResultList();
	}

	/**
	 * @see CalculationRegistrationDAO#findCalculationRegistrations(String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CalculationRegistration> findCalculationRegistrations(String partialToken) {
		Session session = getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CalculationRegistration> query = builder.createQuery(CalculationRegistration.class);
		Root<CalculationRegistration> root = query.from(CalculationRegistration.class);

		query.select(root)
				.where(builder.like(
						builder.lower(root.get(TOKEN)),
						containsIgnoreCasePattern(partialToken)
				));

		return session.createQuery(query).getResultList();
	}

	/**
	 * @see CalculationRegistrationDAO#saveCalculationRegistration(CalculationRegistration)
	 */
	@Override
	@Transactional
	public CalculationRegistration saveCalculationRegistration(
			CalculationRegistration calculationRegistration) {
		return HibernateUtil.saveOrUpdate(
				getCurrentSession(),
				calculationRegistration
		);
	}

	/**
	 * @see CalculationRegistrationDAO#deleteCalculationRegistration(CalculationRegistration)
	 */
	@Override
	@Transactional
	public void deleteCalculationRegistration(CalculationRegistration calculationRegistration) {
		Session session = getCurrentSession();

		CalculationRegistration managedCalculationRegistration = session.contains(calculationRegistration)
				? calculationRegistration
				: session.merge(calculationRegistration);

		session.remove(managedCalculationRegistration);
	}

	private String containsIgnoreCasePattern(String value) {
		return "%" + value.toLowerCase(Locale.ROOT) + "%";
	}
}
