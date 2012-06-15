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
package org.openmrs.calculation.patient;

import java.util.Collection;
import java.util.Map;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.calculation.parameter.ParameterDefinition;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;

/**
 * The PatientCalculationService provides a mechanism for evaluating {@link PatientCalculation}s in
 * a more flexible and robust way and for associating Calculation instances with saved tokens. The
 * benefits of evaluating a {@link PatientCalculation} through this service is that parameter
 * datatypes and required parameters get enforced before the evaluation is done. It also provides
 * convenience methods to process evaluations for a single patient.
 */
public interface PatientCalculationService extends OpenmrsService {
	
	/**
	 * @return
	 */
	public PatientCalculationContext createCalculationContext();
	
	/**
	 * @see PatientCalculationService#evaluate(Integer, PatientCalculation,
	 *      PatientCalculationContext)
	 */
	public CalculationResult evaluate(Integer patientId, PatientCalculation calculation);
	
	/**
	 * @see PatientCalculationService#evaluate(Integer, PatientCalculation, Map,
	 *      PatientCalculationContext)
	 */
	public CalculationResult evaluate(Integer patientId, PatientCalculation calculation, PatientCalculationContext context)
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
	 * @return A {@link CalculationResult}
	 */
	public CalculationResult evaluate(Integer patientId, PatientCalculation calculation,
	                                  Map<String, Object> parameterValues, PatientCalculationContext context)
	    throws APIException;
	
	/**
	 * @see PatientCalculationService#evaluate(Collection<Integer>, PatientCalculation,
	 *      PatientCalculationContext)
	 */
	public CalculationResultMap evaluate(Collection<Integer> cohort, PatientCalculation calculation) throws APIException;
	
	/**
	 * @see PatientCalculationService#evaluate(Collection<Integer>, PatientCalculation, Map,
	 *      PatientCalculationContext)
	 */
	public CalculationResultMap evaluate(Collection<Integer> cohort, PatientCalculation calculation, PatientCalculationContext context)
	    throws APIException;
	
	/**
	 * Evaluates the specified {@link PatientCalculation} for the specified cohort of patients based
	 * on the provided contextual data and parameter values. <br/>
	 * <b>NOTE:</b> For memory and performance reasons, this method may call the calculation's evaluate method
	 * multiple times with smaller batches than the whole input cohort
	 * 
	 * @param cohort patientIds of the patients to evaluate the rule on
	 * @param calculation the calculation to evaluate
	 * @param parameterValues a map of {@link ParameterDefinition} keys and actual values to be used
	 *            by the calculation
	 * @param context the {@link PatientCalculationContext} to be used by this evaluation
	 * @return A {@link CalculationResultMap}, with results for all all patients in cohort. Note that if cohort contains duplicate patient ids, this result's size will differ from cohort.size()
	 * @should fail if any required parameter is not set
	 * @should fail for a blank value for a required parameter if datatype is a primitive wrapper
	 * @should fail for a blank value for a required parameter if datatype is a String
	 * @should fail if the a parameter value doesnt match the allowed datatype
	 * @should pass if the passed in datatype cannot be loaded
	 * @should return the expected result size for cohort with a large number of patient
	 */
	public CalculationResultMap evaluate(Collection<Integer> cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) throws APIException;
	
}
