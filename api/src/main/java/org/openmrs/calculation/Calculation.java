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
package org.openmrs.calculation;

import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.calculation.parameter.ParameterDefinition;
import org.openmrs.calculation.parameter.ParameterDefinitionSet;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.result.CohortResult;

/**
 * Super interface for all calculations
 */
public interface Calculation {
	
	/**
	 * Gets the {@link ParameterDefinitionSet} for this {@link PatientCalculation}
	 * 
	 * @return a list of {@link ParameterDefinition}s
	 * @see ParameterDefinitionSet
	 */
	public ParameterDefinitionSet getParameterDefinitionSet();
	
	/**
	 * Evaluates a calculation for a cohort of patients taking into consideration any specified
	 * parameter values and contextual information
	 * 
	 * @param cohort a {@link Cohort} of patients for whom to evaluation the calculation
	 * @param parameterValues a map of parameter values, takes the form
	 *            Map&lt;ParameterDefinition.key, Object Value&gt;
	 * @param context the {@link CalculationContext} to use while performing the evaluation
	 * @return a {@link CohortResult}
	 */
	public CohortResult evaluate(Cohort cohort, Map<String, Object> parameterValues, CalculationContext context);
}
