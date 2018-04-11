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
package org.openmrs.calculation.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.CalculationUtil;
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.db.CalculationRegistrationDAO;
import org.openmrs.validator.ValidateUtil;

/**
 * It is a default implementation of {@link CalculationRegistrationService}.
 */
public class CalculationRegistrationServiceImpl extends BaseOpenmrsService implements CalculationRegistrationService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private CalculationRegistrationDAO dao;
	
	private Map<String, CalculationRegistration> tokenCalculationRegistrationCache = new ConcurrentHashMap<String, CalculationRegistration>();
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(CalculationRegistrationDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#getCalculationRegistration(java.lang.Integer)
	 */
	@Override
	public CalculationRegistration getCalculationRegistration(Integer calculationRegistrationId) {
		return dao.getCalculationRegistration(calculationRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#getCalculationRegistrationByUuid(String)
	 */
	@Override
	public CalculationRegistration getCalculationRegistrationByUuid(String uuid) {
		return dao.getCalculationRegistrationByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#getCalculationRegistrationByToken(java.lang.String)
	 */
	@Override
	public CalculationRegistration getCalculationRegistrationByToken(String token) {
		return dao.getCalculationRegistrationByToken(token);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#getAllCalculationRegistrations()
	 */
	@Override
	public List<CalculationRegistration> getAllCalculationRegistrations() {
		return dao.getAllCalculationRegistrations();
	}

	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#getCalculationRegistrationsByProviderClassname(String)
	 */
	@Override
	public List<CalculationRegistration> getCalculationRegistrationsByProviderClassname(String providerClassname) {
		return dao.getCalculationRegistrationsByProviderClassname(providerClassname);
	}

	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#findCalculationRegistrations(java.lang.String)
	 */
	@Override
	public List<CalculationRegistration> findCalculationRegistrations(String partialToken) {
		return dao.findCalculationRegistrations(partialToken);
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#saveCalculationRegistration(org.openmrs.calculation.CalculationRegistration)
	 */
	@Override
	public CalculationRegistration saveCalculationRegistration(CalculationRegistration calculationRegistration) {
		ValidateUtil.validate(calculationRegistration);
		CalculationRegistration savedToken = dao.saveCalculationRegistration(calculationRegistration);
		
		tokenCalculationRegistrationCache.clear();
		
		return savedToken;
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#purgeCalculationRegistration(CalculationRegistration)
	 */
	@Override
	public void purgeCalculationRegistration(CalculationRegistration calculationRegistration) {
		dao.deleteCalculationRegistration(calculationRegistration);
		tokenCalculationRegistrationCache.clear();
	}
	
	/**
	 * @see org.openmrs.calculation.api.CalculationRegistrationService#getCalculation(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Calculation> T getCalculation(String tokenName, Class<T> clazz) throws InvalidCalculationException {
		if (tokenName == null || clazz == null)
			throw new IllegalArgumentException("tokenName and clazz cannot be null");
		
		CalculationRegistration calculationRegistration = tokenCalculationRegistrationCache.get(tokenName);
		if (calculationRegistration == null) {
			calculationRegistration = getCalculationRegistrationByToken(tokenName);
			if (calculationRegistration != null)
				tokenCalculationRegistrationCache.put(tokenName, calculationRegistration);
		}
		
		return (T) CalculationUtil.getCalculationForCalculationRegistration(calculationRegistration);
	}
}
