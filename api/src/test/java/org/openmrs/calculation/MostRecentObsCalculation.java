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

import org.openmrs.Cohort;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.definition.ParameterDefinitionSet;
import org.openmrs.calculation.evaluator.CalculationEvaluator;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EmptyResult;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.util.OpenmrsUtil;

/**
 * Calculation for most recent obs, this calculation also evaluates itself
 */
@Handler(supports = { MostRecentObsCalculation.class }, order = 50)
public class MostRecentObsCalculation implements Calculation, CalculationEvaluator {
	
	/**
	 * @see org.openmrs.calculation.Calculation#getParameterDefinitionSet()
	 */
	@Override
	public ParameterDefinitionSet getParameterDefinitionSet() {
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.evaluator.CalculationEvaluator#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation calculation, Map<String, Object> parameterValues,
	                             CalculationContext context) {
		CohortResult results = new CohortResult();
		if (cohort != null) {
			PatientService ps = Context.getPatientService();
			ObsService os = Context.getObsService();
			for (Integer patientId : cohort.getMemberIds()) {
				Patient patient = ps.getPatient(patientId);
				if (patient != null) {
					Obs mostRecentObsFound = null;
					//check in the cache if we have the most recent encounter
					Collection<Obs> observations = null;
					boolean foundCachedEncounter = false;
					Object obj = context.getFromCache(MostRecentEncounterCalculation.MOST_RECENT_ENCOUNTER_KEY_PREFIX
						        + patientId);
						if (obj != null) {
							Encounter mostRecentEncounter = (Encounter) obj;
							observations = mostRecentEncounter.getAllObs(false);
						foundCachedEncounter = true;
					}
					if (!foundCachedEncounter)
						observations = os.getObservationsByPerson(patient);
					
					if (observations != null) {
						for (Obs obs : observations) {
							if (mostRecentObsFound == null
							        || OpenmrsUtil.compare(obs.getObsDatetime(), mostRecentObsFound.getObsDatetime()) > 0) {
								mostRecentObsFound = obs;
							}
						}
					}
					
					if (mostRecentObsFound != null) {
						results.put(patientId, new ObsResult(mostRecentObsFound, calculation, context));
					} else
						results.put(patientId, new EmptyResult());
				}
			}
		}
		
		return results;
	}
}
