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
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EmptyResult;
import org.openmrs.calculation.result.Result;

/**
 * The RuleService is the primary mechanism for evaluating Rules and for associating Rule instances
 * with saved tokens.
 */
public interface CalculationService extends OpenmrsService {
	
	/**
	 * @return
	 */
	public CalculationContext createCalculationContext();
	
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
	 * @should update an existing token
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
	 * @see CalculationService#evaluate(Integer, Calculation, CalculationContext)
	 */
	public Result evaluate(Integer patientId, Calculation calculation);
	
	/**
	 * @see CalculationService#evaluate(Integer, Calculation, Map, CalculationContext)
	 */
	public Result evaluate(Integer patientId, Calculation calculation, CalculationContext context);
	
	/**
	 * Evaluates the specified {@link Calculation} for the specified patient using basing on the
	 * provided contextual data and parameter values, it returns a {@link Result} containing the
	 * generated value or an {@link EmptyResult} if no value was generated
	 * 
	 * @param patientId the patientId for the patient
	 * @param calculation the calculation to evaluate
	 * @param parameterValues a map of {@link ParameterDefinition} keys and actual values to be used
	 *            by the calculation
	 * @param context the {@link CalculationContext} to be used by this evaluation
	 * @return A {@link Result}
	 */
	public Result evaluate(Integer patientId, Calculation calculation, Map<String, Object> parameterValues,
	                       CalculationContext context);
	
	/**
	 * @see CalculationService#evaluate(Cohort, Calculation, CalculationContext)
	 */
	public CohortResult evaluate(Cohort cohort, Calculation calculation);
	
	/**
	 * @see CalculationService#evaluate(Cohort, Calculation, Map, CalculationContext)
	 */
	public CohortResult evaluate(Cohort cohort, Calculation calculation, CalculationContext context);
	
	/**
	 * Evaluates the specified {@link Calculation} for the specified cohort of patients using basing
	 * on the provided contextual data and parameter values
	 * 
	 * @param cohort a cohort of patients
	 * @param calculation the calculation to evaluate
	 * @param parameterValues a map of {@link ParameterDefinition} keys and actual values to be used
	 *            by the calculation
	 * @param context the {@link CalculationContext} to be used by this evaluation
	 * @return A {@link Result}
	 */
	public CohortResult evaluate(Cohort cohort, Calculation calculation, Map<String, Object> parameters,
	                             CalculationContext context);
	
}
