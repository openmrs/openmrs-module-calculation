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
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.SimpleResult;

/**
 * This calculation does nothing special, but it is used to test that batching processing of a
 * cohort with a large number of member ids actually returns the expected count of results without
 * necessarily having a patient matching every single memberId in the passed in cohort
 */
public class CountingCalculation extends BaseCalculation implements PatientCalculation {
	
	/**
	 * @see Calculation#evaluate(Cohort, Map, PatientCalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		CohortResult results = new CohortResult();
		for (Integer patientId : cohort.getMemberIds())
			results.put(patientId, new SimpleResult(patientId, this));
		
		return results;
	}
	
}
