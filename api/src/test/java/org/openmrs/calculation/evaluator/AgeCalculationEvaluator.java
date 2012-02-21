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
package org.openmrs.calculation.evaluator;

import java.util.Date;
import java.util.Map;

import org.joda.time.Chronology;
import org.joda.time.DateMidnight;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.chrono.ISOChronology;
import org.openmrs.Cohort;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EmptyResult;
import org.openmrs.calculation.result.SimpleResult;

/**
 * Test Age calculation evaluator that calculates the age of every single patient in a cohort
 */
@Handler(supports = { AgeCalculation.class }, order = 50)
public class AgeCalculationEvaluator extends BaseCalculationEvaluator {
	
	/**
	 * Calculates the age of a patient in years, months weekd etc. depending on the units specified
	 * as a parameter value
	 * 
	 * @see org.openmrs.calculation.evaluator.CalculationEvaluator#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation calculation, Map<String, Object> parameterValues,
	                             CalculationContext context) {
		if (calculation == null)
			throw new IllegalArgumentException("Calculation cannot be null");
		
		CohortResult results = new CohortResult();
		if (cohort != null) {
			PatientService ps = Context.getPatientService();
			Chronology isoChronology = ISOChronology.getInstance();
			for (Integer patientId : cohort.getMemberIds()) {
				Patient patient = ps.getPatient(patientId);
				if (patient != null) {
					DateMidnight birthDate = new DateMidnight(patient.getBirthdate().getTime(), isoChronology);
					Date date = new Date();//default to current date
					if (context != null) {
						if (context.getNow() != null)
							date = context.getNow();
					}
					
					DateMidnight asOfDate = new DateMidnight(date.getTime(), isoChronology);
					Object value = null;
					if (parameterValues != null) {
						Object units = parameterValues.get("units");
						if (units != null) {
							if ("months".toString().equalsIgnoreCase(units.toString()))
								value = Months.monthsBetween(birthDate, asOfDate).getMonths();
							
							//Can as well get age in weeks, days etc
						}
					}
					
					//Defaults to years
					if (value == null)
						value = Years.yearsBetween(birthDate, asOfDate).getYears();
					
					if (value != null)
						results.put(patientId, new SimpleResult(value, calculation, context));
					else
						results.put(patientId, new EmptyResult());
				}
			}
		}
		
		return results;
	}
}
