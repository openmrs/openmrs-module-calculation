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
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationEvaluator;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EmptyResult;
import org.openmrs.calculation.result.EncounterResult;
import org.openmrs.util.OpenmrsUtil;

/**
 * This is an example of the 'short-hand' way of writing a calculation where it evaluates itself
 */
@Handler(supports = { MostRecentEncounterCalculation.class }, order = 50)
public class MostRecentEncounterCalculation extends BaseCalculation implements PatientCalculation, PatientCalculationEvaluator {

	//Prefix for keys used to map each patient to their most recent encounter in the current context
	public static final String MOST_RECENT_ENCOUNTER_KEY_PREFIX = "mostRecentEncounter";
	
	/**
	 * @see org.openmrs.calculation.evaluator.patient.PatientCalculationEvaluator#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.patient.PatientCalculation, java.util.Map,
	 *      org.openmrs.calculation.api.patient.PatientCalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) {
		CohortResult results = new CohortResult();
		if (cohort != null) {
			PatientService ps = Context.getPatientService();
			EncounterService es = Context.getEncounterService();
			for (Integer patientId : cohort.getMemberIds()) {
				Patient patient = ps.getPatient(patientId);
				if (patient != null) {
					Encounter mostRecentEncounterFound = null;
					List<Encounter> encounters = es.getEncountersByPatient(patient);
					for (Encounter encounter : encounters) {
						if (mostRecentEncounterFound == null
						        || OpenmrsUtil.compare(encounter.getEncounterDatetime(),
						            mostRecentEncounterFound.getEncounterDatetime()) > 0) {
							mostRecentEncounterFound = encounter;
						}
					}
					
					if (mostRecentEncounterFound != null) {
						// (As a test usecase) cache the most recent encounter for later use incase the 
						//caller's next call is for its most recent obs and they share contextual data
						context.addToCache(MOST_RECENT_ENCOUNTER_KEY_PREFIX + patientId, mostRecentEncounterFound);
						results.put(patientId, new EncounterResult(mostRecentEncounterFound, calculation, context));
					} else {
						results.put(patientId, new EmptyResult());
					}
				}
			}
		}
		
		return results;
	}
}
