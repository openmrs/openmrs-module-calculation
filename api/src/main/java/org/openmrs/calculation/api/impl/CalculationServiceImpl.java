/**
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
package org.openmrs.calculation.api.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.api.CalculationService;
import org.openmrs.calculation.api.db.CalculationDAO;
import org.openmrs.calculation.evaluator.CalculationEvaluator;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EmptyResult;
import org.openmrs.calculation.result.Result;
import org.openmrs.util.HandlerUtil;

/**
 * It is a default implementation of {@link CalculationService}.
 */
public class CalculationServiceImpl extends BaseOpenmrsService implements CalculationService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private CalculationDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(CalculationDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#createCalculationContext()
	 */
	@Override
	public CalculationContext createCalculationContext() {
		return new CalculationContext() {
			
			private Date indexDate = null;
			
			private Map<String, Object> contextCache = new WeakHashMap<String, Object>();
			
			/**
			 * @see org.openmrs.calculation.api.CalculationContext#getIndexDate()
			 */
			@Override
			public Date getIndexDate() {
				return indexDate;
			}
			
			/**
			 * @see org.openmrs.calculation.api.CalculationContext#setIndexDate(java.util.Date)
			 */
			@Override
			public void setIndexDate(Date date) {
				indexDate = date;
			}
			
			/**
			 * @see org.openmrs.calculation.api.CalculationContext#addToCache(java.lang.String,
			 *      java.lang.Object)
			 */
			@Override
			public void addToCache(String key, Object value) {
				contextCache.put(key, value);
			}
			
			/**
			 * @see org.openmrs.calculation.api.CalculationContext#getFromCache(java.lang.String)
			 */
			@Override
			public Object getFromCache(String key) {
				return contextCache.get(key);
			}
			
			/**
			 * @see org.openmrs.calculation.api.CalculationContext#getFromCache(org.openmrs.Cohort,
			 *      org.openmrs.calculation.Calculation)
			 */
			@Override
			public CohortResult getFromCache(Cohort cohort, Calculation calculation) {
				//TODO Add implementation code
				throw null;
			}
			
			/**
			 * @see org.openmrs.calculation.api.CalculationContext#removeFromCache(java.lang.String)
			 */
			@Override
			public void removeFromCache(String key) {
				contextCache.remove(key);
			}
		};
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getTokenRegistration(java.lang.Integer)
	 */
	@Override
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId) throws APIException {
		return dao.getTokenRegistration(tokenRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getTokenRegistrationByUuid(String)
	 */
	@Override
	public TokenRegistration getTokenRegistrationByUuid(String uuid) throws APIException {
		return dao.getTokenRegistrationByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getTokenRegistrationByName(java.lang.String)
	 */
	@Override
	public TokenRegistration getTokenRegistrationByName(String name) throws APIException {
		return dao.getTokenRegistrationByName(name);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getAllTokenRegistrations()
	 */
	@Override
	public List<TokenRegistration> getAllTokenRegistrations() throws APIException {
		return dao.getAllTokenRegistrations();
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#findTokens(java.lang.String)
	 */
	@Override
	public List<TokenRegistration> findTokens(String partialName) throws APIException {
		return dao.findTokens(partialName);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#saveTokenRegistration(org.openmrs.calculation.TokenRegistration)
	 */
	@Override
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration) throws APIException {
		return dao.saveTokenRegistration(tokenRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#purgeTokenRegistration(TokenRegistration)
	 */
	@Override
	public void purgeTokenRegistration(TokenRegistration tokenRegistration) throws APIException {
		dao.deleteTokenRegistration(tokenRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getCalculation(java.lang.String)
	 */
	@Override
	public Calculation getCalculation(String tokenName) throws APIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.Calculation)
	 */
	@Override
	public Result evaluate(Integer patientId, Calculation calculation) throws APIException {
		return evaluate(patientId, calculation, null);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.Calculation, org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public Result evaluate(Integer patientId, Calculation calculation, CalculationContext context) throws APIException {
		return evaluate(patientId, calculation, null, context);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public Result evaluate(Integer patientId, Calculation calculation, Map<String, Object> parameterValues,
	                       CalculationContext context) throws APIException {
		Cohort cohort = new Cohort(patientId);
		cohort.addMember(patientId);
		CohortResult cr = evaluate(cohort, calculation, parameterValues, context);
		if (cr.size() == 0)
			return new EmptyResult();
		
		return cr.get(patientId);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation calculation) throws APIException {
		return evaluate(cohort, calculation, null);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation calculation, CalculationContext context) throws APIException {
		return evaluate(cohort, calculation, null, context);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation calculation, Map<String, Object> parameterValues,
	                             CalculationContext context) throws APIException {
		if (calculation == null)
			throw new IllegalArgumentException("Calculation cannot be null");
		//TODO Check if required parameters are set
		
		if (context != null && context.getIndexDate() == null)
			context.setIndexDate(new Date());
		
		CohortResult cr = HandlerUtil.getPreferredHandler(CalculationEvaluator.class, calculation.getClass()).evaluate(
		    cohort, calculation, parameterValues, context);
		
		return cr;
	}
}
