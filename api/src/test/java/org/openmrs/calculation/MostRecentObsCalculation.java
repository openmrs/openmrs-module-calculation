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
import java.util.List;
import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ObsResult;

/**
 * Calculation for most recent obs, this calculation also evaluates itself
 */
public class MostRecentObsCalculation extends BaseCalculation implements ConfigurableCalculation, PatientCalculation {
	
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
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		
		CalculationResultMap results = new CalculationResultMap();
		Map<Integer, List<Obs>> patientObs = Context.getPatientSetService().getObservations(new Cohort(cohort), whichConcept);
		for (Integer pId : patientObs.keySet()) {
			String cacheKey = this.getClass().getName() + "." + whichConcept + "." + pId;
			CalculationResult r = (CalculationResult) context.getFromCache(cacheKey);
			if (r == null) {
				r = new ObsResult(patientObs.get(pId).get(0), this);
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
