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

import org.openmrs.calculation.BaseCalculation;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.EvaluationInstanceData;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;

/**
 * Abstract implementation of a {@link PatientCalculation} that makes it easier to support
 * Cohort-based calculations by defining the calculation for a single patient. Also provides
 * mechanisms to enable pre-processing and cleanup steps which might be particularly useful for
 * maximizing performance. Any instance data that this Calculation needs to store during the course
 * of it's evaluation should be done within an EvaluationInstanceData object, as these
 * {@link Calculation}s are not thread-safe
 * 
 * @see PatientCalculation
 * @see BaseCalculation
 */
public abstract class PatientAtATimeCalculation extends BaseCalculation implements PatientCalculation {
	
	/**
	 * Optional method that allows for the Evaluator to perform some pre-processing activities prior
	 * to running the evaluate method for each individual patient.
	 */
	public EvaluationInstanceData preprocess(Collection<Integer> cohort, Map<String, Object> parameterValues,
	                                         PatientCalculationContext context) {
		return null;
	}
	
	/**
	 * Required method which encapsulates the actual calculation that will be performed on each
	 * patient
	 */
	public abstract CalculationResult evaluateForPatient(EvaluationInstanceData instanceData, Integer patientId,
	                                                     Map<String, Object> parameterValues,
	                                                     PatientCalculationContext context);
	
	/**
	 * Optional method that allows for any necessary cleanup activities to be performed This method
	 * will be called whether or not the evaluation succeeds,
	 */
	public void cleanup(EvaluationInstanceData instanceData, Collection<Integer> cohort, Map<String, Object> parameterValues,
	                    PatientCalculationContext context) {
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public final CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		CalculationResultMap result = new CalculationResultMap();
		EvaluationInstanceData instanceData = preprocess(cohort, parameterValues, (PatientCalculationContext) context);
		try {
			for (Integer patientId : cohort) {
				result.put(patientId,
				    evaluateForPatient(instanceData, patientId, parameterValues, (PatientCalculationContext) context));
			}
		}
		finally {
			cleanup(instanceData, cohort, parameterValues, (PatientCalculationContext) context);
		}
		return result;
	}
	
}
