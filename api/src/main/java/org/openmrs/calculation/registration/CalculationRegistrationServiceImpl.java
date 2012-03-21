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
package org.openmrs.calculation.registration;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.CalculationUtil;
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.registration.db.CalculationRegistrationDAO;
import org.openmrs.validator.ValidateUtil;

/**
 * It is a default implementation of {@link CalculationRegistrationService}.
 */
public class CalculationRegistrationServiceImpl extends BaseOpenmrsService implements CalculationRegistrationService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private CalculationRegistrationDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(CalculationRegistrationDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#getCalculationRegistration(java.lang.Integer)
	 */
	@Override
	public CalculationRegistration getCalculationRegistration(Integer calculationRegistrationId) {
		return dao.getCalculationRegistration(calculationRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#getCalculationRegistrationByUuid(String)
	 */
	@Override
	public CalculationRegistration getCalculationRegistrationByUuid(String uuid) {
		return dao.getCalculationRegistrationByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#getCalculationRegistrationByToken(java.lang.String)
	 */
	@Override
	public CalculationRegistration getCalculationRegistrationByToken(String token) {
		return dao.getCalculationRegistrationByToken(token);
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#getAllCalculationRegistrations()
	 */
	@Override
	public List<CalculationRegistration> getAllCalculationRegistrations() {
		return dao.getAllCalculationRegistrations();
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#findCalculationRegistrations(java.lang.String)
	 */
	@Override
	public List<CalculationRegistration> findCalculationRegistrations(String partialToken) {
		return dao.findCalculationRegistrations(partialToken);
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#saveCalculationRegistration(org.openmrs.calculation.registration.CalculationRegistration)
	 */
	@Override
	public CalculationRegistration saveCalculationRegistration(CalculationRegistration calculationRegistration) {
		ValidateUtil.validate(calculationRegistration);
		return dao.saveCalculationRegistration(calculationRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#purgeCalculationRegistration(CalculationRegistration)
	 */
	@Override
	public void purgeCalculationRegistration(CalculationRegistration calculationRegistration) {
		dao.deleteCalculationRegistration(calculationRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.registration.CalculationRegistrationService#getCalculation(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Calculation> T getCalculation(String tokenName, Class<T> clazz) throws InvalidCalculationException {
		CalculationRegistration calculationRegistration = getCalculationRegistrationByToken(tokenName);
		return (T) CalculationUtil.getCalculationForCalculationRegistration(calculationRegistration);
	}
}
