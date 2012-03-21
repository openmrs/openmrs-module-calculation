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

import java.util.List;
import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationEvaluator;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.calculation.result.CalculationResult;

/**
 * Calculation for most recent obs, this calculation also evaluates itself
 */
@Handler(supports = { MostRecentObsCalculation.class }, order = 50)
public class MostRecentObsCalculation extends BaseCalculation implements ConfigurableCalculation, PatientCalculation, PatientCalculationEvaluator {
	
	private Concept whichConcept;
	
	/**
	 * @see Calculation#setConfiguration(String)
	 */
	@Override
	public void setConfiguration(String configuration) throws InvalidCalculationException {
		try {
			whichConcept = Context.getConceptService().getConcept(configuration);
		}
		catch (Exception e) {}
		if (whichConcept == null) {
			throw new InvalidCalculationException(this, configuration);
		}
	}
	
	/**
	 * @see PatientCalculationEvaluator#evaluate(Cohort, PatientCalculation, Map, PatientCalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) {
		
		CohortResult results = new CohortResult();
		MostRecentObsCalculation calc = (MostRecentObsCalculation) calculation;
		Map<Integer, List<Obs>> patientObs = Context.getPatientSetService().getObservations(cohort, calc.whichConcept);
		for (Integer pId : patientObs.keySet()) {
			String cacheKey = calc.getClass().getName() + "." + calc.whichConcept + "." + pId;
			CalculationResult r = (CalculationResult)context.getFromCache(cacheKey);
			if (r == null) {
				r = new ObsResult(patientObs.get(pId).get(0), calculation);
				context.addToCache(cacheKey, r);
			}
			results.put(pId, r);
		}
		return results;
	}

	/**
	 * @return the whichConcept
	 */
	public Concept getWhichConcept() {
		return whichConcept;
	}
}
