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

import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.result.CalculationResultMap;

/**
 * A PatientCalculation represents a definition that can be evaluated to produce data, this is the
 * base interface for all Patient calculations.
 */
public interface PatientCalculation extends Calculation {
	
	/**
	 * Evaluates a calculation for a cohort of patients taking into consideration any specified
	 * parameter values and contextual information. <br/>
	 * <b>NOTE:</b> implementations are not expected to do sophisticated memory management, so if you
	 * want to evaluate a calculation on a very large number of patients, you should use one of the
	 * evaluate methods in {@link PatientCalculationService} instead, since these will run the calculation
	 * on manageable batches.
	 * 
	 * @param cohort patientIds for the patients on whom to evaluation the calculation
	 * @param parameterValues a map of parameter values, takes the form
	 *            Map&lt;ParameterDefinition.key, Object Value&gt;
	 * @param context the {@link PatientCalculationContext} to use while performing the evaluation
	 * @return a {@link CalculationResultMap}
	 */
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context);
	
}
