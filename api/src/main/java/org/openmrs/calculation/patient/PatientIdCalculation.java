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
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;

/**
 * A very simple Patient Calculation, mainly to use as an example and for testing purposes
 * 
 * @should return a result containing the patient id for each member of the input cohort
 */
public class PatientIdCalculation extends BaseCalculation implements PatientCalculation {
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		CalculationResultMap r = new CalculationResultMap();
		if (cohort != null) {
			for (Integer pId : cohort) {
				r.put(pId, new SimpleResult(pId, this, context));
			}
		}
		return r;
	}
}
