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
package org.openmrs.calculation.provider;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.PatientCalculation;
import org.openmrs.calculation.MostRecentEncounterCalculation;
import org.openmrs.calculation.MostRecentObsCalculation;

/**
 * A simple {@link CalculationProvider} for testing purposes
 */
public class DemoCalculationProvider implements CalculationProvider {
	
	private Map<String, Class<? extends PatientCalculation>> calculations = new HashMap<String, Class<? extends PatientCalculation>>();
	
	/**
	 * Convenience constructor that registers the {@link PatientCalculation}s this provider provides
	 */
	public DemoCalculationProvider() {
		calculations.put("age", AgeCalculation.class);
		calculations.put("mostRecentEncounter", MostRecentEncounterCalculation.class);
		calculations.put("mostRecentObs", MostRecentObsCalculation.class);
	}
	
	/**
	 * @see org.openmrs.calculation.provider.CalculationProvider#getCalculation(String, String)
	 */
	@Override
	public PatientCalculation getCalculation(String calculationName, String configuration) {
		if (calculationName == null)
			throw new IllegalArgumentException("calculationName cannot be null");
		
		Class<? extends PatientCalculation> clazz = calculations.get(calculationName);
		if (clazz != null) {
			try {
				PatientCalculation calculation = clazz.newInstance();
				if (StringUtils.isNotBlank(configuration)) {
					//do further initialization
				}
				return calculation;
			}
			catch (Exception e) {}
		}
		return null;
	}
}
