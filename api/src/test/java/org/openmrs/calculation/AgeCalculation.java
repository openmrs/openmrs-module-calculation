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

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Chronology;
import org.joda.time.DateMidnight;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.chrono.ISOChronology;
import org.openmrs.Cohort;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.parameter.SimpleParameterDefinition;
import org.openmrs.calculation.patient.PatientAtATimeCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.util.OpenmrsUtil;

/**
 * A simple implementation of a calculation for calculating patient ages and is purely for testing
 * purposes
 */
public class AgeCalculation extends PatientAtATimeCalculation {
	
	/**
	 * Stores pre-processed birth date data for the input cohort
	 */
	public class BirthdateData extends HashMap<Integer, Date> implements EvaluationInstanceData {
		
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * Default {@link Constructor}
	 */
	public AgeCalculation() {
		addParameterDefinition(new SimpleParameterDefinition("units", "java.lang.String", "Units Of Age", false));
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientAtATimeCalculation#preprocess(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public EvaluationInstanceData preprocess(Collection<Integer> cohort, Map<String, Object> parameterValues,
	                                         PatientCalculationContext context) {
		
		BirthdateData data = new BirthdateData();
		if (cohort != null && !cohort.isEmpty()) {
			StringBuilder q = new StringBuilder();
			q.append("select p.patient_id, n.birthdate ");
			q.append("from patient p, person n ");
			q.append("where p.patient_id = n.person_id ");
			q.append("and p.voided = 0 and n.voided = 0 ");
			q.append("and p.patient_id in (" + OpenmrsUtil.join(cohort, ",") + ")");
			List<List<Object>> queryData = Context.getAdministrationService().executeSQL(q.toString(), true);
			for (List<Object> row : queryData) {
				Integer pId = (Integer) row.get(0);
				Date birthdate = (Date) row.get(1);
				data.put(pId, birthdate);
			}
		}
		return data;
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientAtATimeCalculation#evaluateForPatient(EvaluationInstanceData,
	 *      Integer, Map, PatientCalculationContext)
	 */
	@Override
	public CalculationResult evaluateForPatient(EvaluationInstanceData instanceData, Integer patientId,
	                                            Map<String, Object> parameterValues, PatientCalculationContext context) {
		BirthdateData data = (BirthdateData) instanceData;
		CalculationResult r = null;
		Date birthdate = data.get(patientId);
		if (birthdate != null) {
			Chronology isoChronology = ISOChronology.getInstance();
			DateMidnight birthDate = new DateMidnight(birthdate, isoChronology);
			DateMidnight asOfDate = new DateMidnight(context.getNow(), isoChronology);
			if (parameterValues != null && "months".equals(parameterValues.get("units"))) {
				int months = Months.monthsBetween(birthDate, asOfDate).getMonths();
				r = new SimpleResult(months, this, context);
			} else {
				int years = Years.yearsBetween(birthDate, asOfDate).getYears();
				r = new SimpleResult(years, this, context);
			}
		}
		return r;
	}
}
