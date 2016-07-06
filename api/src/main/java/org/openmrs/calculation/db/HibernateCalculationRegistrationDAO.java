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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.calculation.CalculationRegistration;
import org.springframework.transaction.annotation.Transactional;

/**
 * It is a default implementation of {@link CalculationRegistrationDAO}.
 */
public class HibernateCalculationRegistrationDAO implements CalculationRegistrationDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private DbSessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the session
	 */
	private DbSession getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#getCalculationRegistration(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public CalculationRegistration getCalculationRegistration(Integer calculationRegistrationId) {
		return (CalculationRegistration) getCurrentSession().get(CalculationRegistration.class, calculationRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#getCalculationRegistrationByUuid(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public CalculationRegistration getCalculationRegistrationByUuid(String uuid) {
		return (CalculationRegistration) getCurrentSession().createQuery("FROM CalculationRegistration tr WHERE tr.uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}
	
	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#getCalculationRegistrationByToken(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public CalculationRegistration getCalculationRegistrationByToken(String token) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CalculationRegistration.class);
		criteria.add(Restrictions.ilike("token", token, MatchMode.EXACT));
		return (CalculationRegistration) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#getAllCalculationRegistrations()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<CalculationRegistration> getAllCalculationRegistrations() {
		return getCurrentSession().createCriteria(CalculationRegistration.class).list();
	}

	/**
	 * @see CalculationRegistrationDAO#getCalculationRegistrationsByProviderClassname(String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CalculationRegistration> getCalculationRegistrationsByProviderClassname(String providerClassname) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CalculationRegistration.class);
		criteria.add(Restrictions.eq("providerClassName", providerClassname));
		return criteria.list();
	}

	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#findCalculationRegistrations(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<CalculationRegistration> findCalculationRegistrations(String partialToken) {
		Criteria criteria = getCurrentSession().createCriteria(CalculationRegistration.class);
		criteria.add(Restrictions.ilike("token", partialToken, MatchMode.ANYWHERE));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#saveCalculationRegistration(org.openmrs.calculation.CalculationRegistration)
	 */
	@Override
	@Transactional
	public CalculationRegistration saveCalculationRegistration(CalculationRegistration calculationRegistration) {
		getCurrentSession().saveOrUpdate(calculationRegistration);
		return calculationRegistration;
	}
	
	/**
	 * @see org.openmrs.calculation.db.CalculationRegistrationDAO#deleteCalculationRegistration(org.openmrs.calculation.CalculationRegistration)
	 */
	@Override
	@Transactional
	public void deleteCalculationRegistration(CalculationRegistration calculationRegistration) {
		getCurrentSession().delete(calculationRegistration);
	}
}
