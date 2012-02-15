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

import org.openmrs.Cohort;
import org.openmrs.api.OpenmrsService;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.Result;

/**
 * The RuleService is the primary mechanism for evaluating Rules and for associating Rule instances
 * with saved tokens.
 */
public interface CalculationService extends OpenmrsService {
	
	/**
	 * @return
	 */
	public CalculationContext createContext();
	
	/**
	 * Gets a token from the database with a matching id
	 * 
	 * @param tokenRegistrationId
	 * @return
	 * @should return a token with a matching id
	 */
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId);
	
	/**
	 * Gets a token from the database with a matching uuid
	 * 
	 * @param uuid
	 * @return
	 * @should fetch a token with a matching uuid
	 */
	public TokenRegistration getTokenRegistrationByUuid(String uuid);
	
	/**
	 * Gets a token from the database with a matching name
	 * 
	 * @param name
	 * @return
	 * @should fetch a token with a matching name
	 */
	public TokenRegistration getTokenRegistrationByName(String name);
	
	/**
	 * Gets all tokens in the database
	 * 
	 * @return a list of {@link TokenRegistration}s
	 * @should get all tokens in the database
	 */
	public List<TokenRegistration> getAllTokenRegistrations();
	
	/**
	 * Fetches all tokens that have a name that contains the specified phrase
	 * 
	 * @param partialName the phrase to search against
	 * @return a list of {@link TokenRegistration}s
	 * @should get all tokenRegistrations with a matching name
	 */
	public List<TokenRegistration> findTokens(String partialName);
	
	/**
	 * Saves or updates the specified token in the database
	 * 
	 * @param tokenRegistration the tokenRegistration to save or update
	 * @return
	 * @should save the specified tokenRegistration to the database
	 */
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration);
	
	/**
	 * Purges the specified token from the database
	 * 
	 * @param tokenRegistration the tokenRegistration to purge
	 * @should purge the specified tokenRegistration from the database
	 */
	public void purgeTokenRegistration(TokenRegistration tokenRegistration);
	
	/**
	 * Gets a {@link Calculation} associated to a {@link TokenRegistration} with a name that matches
	 * the specified tokenName
	 * 
	 * @param tokenName
	 * @return {@link Calculation}
	 * @should return the calculation associated to the tokenRegistration with the given name
	 */
	public Calculation getCalculation(String tokenName);
	
	/**
	 * @param patientId
	 * @param Calculation
	 * @param parameters
	 * @param context
	 * @return
	 */
	public Result evaluate(Integer patientId, Calculation Calculation, Map<String, Object> parameters,
	                       CalculationContext context);
	
	/**
	 * @param patientId
	 * @param Calculation
	 * @param context
	 * @return
	 */
	public Result evaluate(Integer patientId, Calculation Calculation, CalculationContext context);
	
	/**
	 * @param patientId
	 * @param Calculation
	 * @return
	 */
	public Result evaluate(Integer patientId, Calculation Calculation);
	
	/**
	 * @param cohort
	 * @param Calculation
	 * @param parameters
	 * @param context
	 * @return
	 */
	public CohortResult evaluate(Cohort cohort, Calculation Calculation, Map<String, Object> parameters,
	                             CalculationContext context);
	
	/**
	 * @param cohort
	 * @param Calculation
	 * @param context
	 * @return
	 */
	public CohortResult evaluate(Cohort cohort, Calculation Calculation, CalculationContext context);
	
	/**
	 * @param cohort
	 * @param Calculation
	 * @return
	 */
	public CohortResult evaluate(Cohort cohort, Calculation Calculation);
	
}
