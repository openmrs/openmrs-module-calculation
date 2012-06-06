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

import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.CalculationConstants;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.patient.PatientCalculation;

/**
 * Contains service methods for managing {@link CalculationRegistration}s
 */
public interface CalculationRegistrationService extends OpenmrsService {
	
	/**
	 * Gets a token from the database with a matching id
	 * 
	 * @param calculationRegistrationId
	 * @return
	 * @should return a token with a matching id
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public CalculationRegistration getCalculationRegistration(Integer calculationRegistrationId);
	
	/**
	 * Gets a token from the database with a matching uuid
	 * 
	 * @param uuid
	 * @return
	 * @should fetch a token with a matching uuid
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public CalculationRegistration getCalculationRegistrationByUuid(String uuid);
	
	/**
	 * Gets a token from the database with a matching name
	 * 
	 * @param name
	 * @return
	 * @should fetch a token with a matching name
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public CalculationRegistration getCalculationRegistrationByToken(String token);
	
	/**
	 * Saves or updates the specified token in the database
	 * 
	 * @param calculationRegistration the calculationRegistration to save or update
	 * @return
	 * @should save the specified calculationRegistration to the database
	 * @should update an existing token
	 * @should update the cached token registration
	 */
	@Authorized(CalculationConstants.PRIV_MANAGE_TOKEN_REGISTRATIONS)
	public CalculationRegistration saveCalculationRegistration(CalculationRegistration calculationRegistration)
	    throws InvalidCalculationException;
	
	/**
	 * Gets all tokens in the database
	 * 
	 * @return a list of {@link CalculationRegistration}s
	 * @should get all tokens in the database
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public List<CalculationRegistration> getAllCalculationRegistrations();
	
	/**
	 * Fetches all tokens that have a token that contains the specified phrase
	 * 
	 * @param partialToken the phrase to search against
	 * @return a list of {@link CalculationRegistration}s
	 * @should get all calculationRegistrations with a matching name
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public List<CalculationRegistration> findCalculationRegistrations(String partialToken);
	
	/**
	 * Purges the specified token from the database
	 * 
	 * @param calculationRegistration the calculationRegistration to purge
	 * @should purge the specified calculationRegistration from the database
	 */
	@Authorized(CalculationConstants.PRIV_MANAGE_TOKEN_REGISTRATIONS)
	public void purgeCalculationRegistration(CalculationRegistration calculationRegistration);
	
	/**
	 * Gets a {@link PatientCalculation} with given name associated to a
	 * {@link CalculationRegistration} with a name that matches the specified tokenName
	 * 
	 * @param tokenName
	 * @return {@link PatientCalculation}
	 * @should return the calculation associated to the calculationRegistration with the given name
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_CALCULATIONS)
	public <T extends Calculation> T getCalculation(String tokenName, Class<T> clazz) throws InvalidCalculationException;
	
}
