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

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.api.CalculationService;
import org.openmrs.calculation.api.db.CalculationDAO;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.Result;

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
	 * @see org.openmrs.calculation.api.CalculationService#createContext()
	 */
	@Override
	public CalculationContext createContext() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getTokenRegistration(java.lang.Integer)
	 */
	@Override
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId) {
		return dao.getTokenRegistration(tokenRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getTokenRegistrationByUuid(String)
	 */
	@Override
	public TokenRegistration getTokenRegistrationByUuid(String uuid) {
		return dao.getTokenRegistrationByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getTokenRegistrationByName(java.lang.String)
	 */
	@Override
	public TokenRegistration getTokenRegistrationByName(String name) {
		return dao.getTokenRegistrationByName(name);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getAllTokenRegistrations()
	 */
	@Override
	public List<TokenRegistration> getAllTokenRegistrations() {
		return dao.getAllTokenRegistrations();
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#findTokens(java.lang.String)
	 */
	@Override
	public List<TokenRegistration> findTokens(String partialName) {
		return dao.findTokens(partialName);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#saveTokenRegistration(org.openmrs.calculation.TokenRegistration)
	 */
	@Override
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration) {
		return dao.saveTokenRegistration(tokenRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#purgeTokenRegistration(TokenRegistration)
	 */
	@Override
	public void purgeTokenRegistration(TokenRegistration tokenRegistration) {
		dao.deleteTokenRegistration(tokenRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#getCalculation(java.lang.String)
	 */
	@Override
	public Calculation getCalculation(String tokenName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public Result evaluate(Integer patientId, Calculation Calculation, Map<String, Object> parameters,
	                       CalculationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.Calculation, org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public Result evaluate(Integer patientId, Calculation Calculation, CalculationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.Calculation)
	 */
	@Override
	public Result evaluate(Integer patientId, Calculation Calculation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation Calculation, Map<String, Object> parameters,
	                             CalculationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation Calculation, CalculationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation Calculation) {
		// TODO Auto-generated method stub
		return null;
	}
}
