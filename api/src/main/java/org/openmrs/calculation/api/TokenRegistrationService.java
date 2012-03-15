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
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.patient.PatientCalculation;

/**
 * Contains service methods for managing {@link TokenRegistration}s
 */
public interface TokenRegistrationService extends OpenmrsService {
	
	/**
	 * Gets a token from the database with a matching id
	 * 
	 * @param tokenRegistrationId
	 * @return
	 * @should return a token with a matching id
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId);
	
	/**
	 * Gets a token from the database with a matching uuid
	 * 
	 * @param uuid
	 * @return
	 * @should fetch a token with a matching uuid
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public TokenRegistration getTokenRegistrationByUuid(String uuid);
	
	/**
	 * Gets a token from the database with a matching name
	 * 
	 * @param name
	 * @return
	 * @should fetch a token with a matching name
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public TokenRegistration getTokenRegistrationByName(String name);
	
	/**
	 * Saves or updates the specified token in the database
	 * 
	 * @param tokenRegistration the tokenRegistration to save or update
	 * @return
	 * @should save the specified tokenRegistration to the database
	 * @should update an existing token
	 */
	@Authorized(CalculationConstants.PRIV_MANAGE_TOKEN_REGISTRATIONS)
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration) throws InvalidCalculationException;
	
	/**
	 * Gets all tokens in the database
	 * 
	 * @return a list of {@link TokenRegistration}s
	 * @should get all tokens in the database
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public List<TokenRegistration> getAllTokenRegistrations();
	
	/**
	 * Fetches all tokens that have a name that contains the specified phrase
	 * 
	 * @param partialName the phrase to search against
	 * @return a list of {@link TokenRegistration}s
	 * @should get all tokenRegistrations with a matching name
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_TOKEN_REGISTRATIONS)
	public List<TokenRegistration> findTokenRegistrations(String partialName);
	
	/**
	 * Purges the specified token from the database
	 * 
	 * @param tokenRegistration the tokenRegistration to purge
	 * @should purge the specified tokenRegistration from the database
	 */
	@Authorized(CalculationConstants.PRIV_MANAGE_TOKEN_REGISTRATIONS)
	public void purgeTokenRegistration(TokenRegistration tokenRegistration);
	
	/**
	 * Gets a {@link PatientCalculation} with given name associated to a {@link TokenRegistration} with a name that
	 * matches the specified tokenName
	 * 
	 * @param tokenName
	 * @return {@link PatientCalculation}
	 * @should return the calculation associated to the tokenRegistration with the given name
	 */
	@Authorized(CalculationConstants.PRIV_VIEW_CALCULATIONS)
	public <T extends Calculation> T getCalculation(String tokenName, Class<T> clazz) throws InvalidCalculationException;
	
}
