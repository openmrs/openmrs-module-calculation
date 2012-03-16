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
package org.openmrs.calculation.api.patient;

import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.Result;

/**
 * The PatientCalculationService is the primary mechanism for evaluating {@link PatientCalculation}s
 * and for associating Calculation instances with saved tokens.
 */
public interface PatientCalculationService extends OpenmrsService {
	
	/**
	 * @return
	 */
	public PatientCalculationContext createCalculationContext();
	
	/**
	 * @see PatientCalculationService#evaluate(Integer, PatientCalculation, PatientCalculationContext)
	 */
	public Result evaluate(Integer patientId, PatientCalculation calculation);
	
	/**
	 * @see PatientCalculationService#evaluate(Integer, PatientCalculation, Map, PatientCalculationContext)
	 */
	public Result evaluate(Integer patientId, PatientCalculation calculation, PatientCalculationContext context)
	    throws APIException;
	
	/**
	 * Evaluates the specified {@link PatientCalculation} for the specified patient based on the
	 * provided contextual data and parameter values.
	 * 
	 * @param patientId the patientId for the patient
	 * @param calculation the calculation to evaluate
	 * @param parameterValues a map of {@link ParameterDefinition} keys and actual values to be used
	 *            by the calculation
	 * @param context the {@link PatientCalculationContext} to be used by this evaluation
	 * @return A {@link Result}
	 */
	public Result evaluate(Integer patientId, PatientCalculation calculation, Map<String, Object> parameterValues,
	                       PatientCalculationContext context) throws APIException;
	
	/**
	 * @see PatientCalculationService#evaluate(Cohort, PatientCalculation, PatientCalculationContext)
	 */
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation) throws APIException;
	
	/**
	 * @see PatientCalculationService#evaluate(Cohort, PatientCalculation, Map, PatientCalculationContext)
	 */
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, PatientCalculationContext context)
	    throws APIException;
	
	/**
	 * Evaluates the specified {@link PatientCalculation} for the specified cohort of patients based
	 * on the provided contextual data and parameter values.
	 * 
	 * @param cohort a cohort of patients
	 * @param calculation the calculation to evaluate
	 * @param parameterValues a map of {@link ParameterDefinition} keys and actual values to be used
	 *            by the calculation
	 * @param context the {@link PatientCalculationContext} to be used by this evaluation
	 * @return A {@link Result}
	 * @should fail if any required parameter is not set
	 * @should fail for a blank value for a required parameter if datatype is a primitive wrapper
	 * @should fail for a blank value for a required parameter if datatype is a String
	 */
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) throws APIException;
	
}
