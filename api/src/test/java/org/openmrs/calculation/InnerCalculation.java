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
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.result.CohortResult;

/**
 * Calculation used by {@link OuterCalculation}.
 */
public class InnerCalculation extends BaseCalculation implements PatientCalculation {
	
	/**
	 * @see Calculation#evaluate(Cohort, Map, CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Map<String, Object> parameterValues, CalculationContext context) {
		Concept concept = Context.getConceptService().getConcept(3);
		if (concept.isRetired()) {
			throw new RuntimeException("Concept must not be retired");
		}
		
		Context.getConceptService().retireConcept(concept, "Retiring for test purposes");
		
		return new CohortResult();
	}
	
}
