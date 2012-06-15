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

import java.util.Collection;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;

/**
 * Calculation using results of {@link InnerCalculation}.
 */
public class OuterCalculation extends BaseCalculation implements PatientCalculation {
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		try {
			PatientCalculation calc = (PatientCalculation) new ClasspathCalculationProvider().getCalculation(
			    InnerCalculation.class.getName(), null);
			calc.evaluate(cohort, parameterValues, context);
		}
		catch (InvalidCalculationException e) {
			throw new RuntimeException(e);
		}
		
		Concept concept = Context.getConceptService().getConcept(3);
		if (!concept.isRetired()) {
			throw new RuntimeException("Concept should have been retired by inner calculation");
		}
		
		return new CalculationResultMap();
	}
	
}
