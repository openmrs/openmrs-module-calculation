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

import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.calculation.api.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.Result;

/**
 * Base interface for all patient calculation evaluators, a PatientCalculationEvaluator is
 * responsible for evaluating one or more types of {@link PatientCalculation}s into {@link Result}s.
 * This is where the bulk of all calculations occur, either by performing these calculations
 * directly within the evaluator, or by delegating to service methods that perform calculations.
 * 
 * @see BaseCalculationEvaluator
 */
public interface PatientCalculationEvaluator {
	
	/**
	 * Evaluates a calculation for a cohort of patients taking into consideration any specified
	 * parameter values and contextual information
	 * 
	 * @param cohort a {@link Cohort} of patients for whom to evaluation the calculation
	 * @param calculation the {@link PatientCalculation} to evaluate
	 * @param parameterValues a map of parameter values, takes the form
	 *            Map&lt;ParameterDefinition.key, Object Value&gt;
	 * @param context the {@link PatientCalculationContext} to use while performing the evaluation
	 * @return a {@link CohortResult}
	 */
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context);
	
}
