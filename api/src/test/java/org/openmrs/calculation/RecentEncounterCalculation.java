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

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
import org.openmrs.calculation.result.EmptyCalculationResult;
import org.openmrs.calculation.result.EncounterResult;
import org.openmrs.calculation.result.ListResult;
import org.openmrs.util.OpenmrsUtil;

/**
 * Evaluates encounters since a given date.
 */
@Handler(supports = { RecentEncounterCalculation.class }, order = 50)
public class RecentEncounterCalculation extends BaseCalculation implements ConfigurableCalculation, PatientCalculation, PatientCalculationEvaluator {
	
	private Date since;
	
	/**
	 * @see org.openmrs.calculation.Calculation#setConfiguration(java.lang.String)
	 */
	@Override
	public void setConfiguration(String configuration) throws InvalidCalculationException {
		try {
			since = new SimpleDateFormat("yyyy-MM-dd").parse(configuration);
		}
		catch (Exception e) {}
		if (since == null) {
			throw new InvalidCalculationException(this, configuration);
		}
	}
	
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
					ListResult list = new ListResult();
					
					TreeSet<Encounter> sortedEncounters = new TreeSet<Encounter>(new Comparator<Encounter>() {
						
						@Override
						public int compare(Encounter o1, Encounter o2) {
							return OpenmrsUtil.compareWithNullAsEarliest(o1.getEncounterDatetime(), o2.getEncounterDatetime());
						}
						
					});
					
					List<Encounter> encounters = es.getEncountersByPatient(patient);
					for (Encounter encounter : encounters) {
						if (OpenmrsUtil.compareWithNullAsEarliest(encounter.getEncounterDatetime(), since) > 0) {
							sortedEncounters.add(encounter);
						}
					}
					
					if (!sortedEncounters.isEmpty()) {
						for (Encounter encounter : sortedEncounters) {
							list.add(new EncounterResult(encounter, calculation, context));
						}
						results.put(patientId, list);
					} else {
						results.put(patientId, new EmptyCalculationResult());
					}
				}
			}
		}
		
		return results;
	}
	
}
