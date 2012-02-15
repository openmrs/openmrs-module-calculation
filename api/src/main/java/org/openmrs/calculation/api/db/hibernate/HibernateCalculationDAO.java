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
package org.openmrs.calculation.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.api.db.CalculationDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * It is a default implementation of {@link CalculationDAO}.
 */
public class HibernateCalculationDAO implements CalculationDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#getTokenRegistration(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId) {
		return (TokenRegistration) getCurrentSession().get(TokenRegistration.class, tokenRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#getTokenRegistrationByUuid(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public TokenRegistration getTokenRegistrationByUuid(String uuid) {
		return (TokenRegistration) getCurrentSession().createQuery("FROM TokenRegistration tr WHERE tr.uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#getTokenRegistrationByName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public TokenRegistration getTokenRegistrationByName(String name) {
		return (TokenRegistration) getCurrentSession().createQuery("FROM TokenRegistration tr WHERE tr.name = :name")
		        .setString("name", name).uniqueResult();
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#getAllTokenRegistrations()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TokenRegistration> getAllTokenRegistrations() {
		return getCurrentSession().createCriteria(TokenRegistration.class).list();
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#findTokens(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TokenRegistration> findTokens(String partialName) {
		Criteria criteria = getCurrentSession().createCriteria(TokenRegistration.class);
		criteria.add(Restrictions.ilike("name", partialName, MatchMode.ANYWHERE));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#saveTokenRegistration(org.openmrs.calculation.TokenRegistration)
	 */
	@Override
	@Transactional
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration) {
		getCurrentSession().saveOrUpdate(tokenRegistration);
		return tokenRegistration;
	}
	
	/**
	 * @see org.openmrs.calculation.api.db.CalculationDAO#deleteTokenRegistration(org.openmrs.calculation.TokenRegistration)
	 */
	@Override
	@Transactional
	public void deleteTokenRegistration(TokenRegistration tokenRegistration) {
		getCurrentSession().delete(tokenRegistration);
	}
}
