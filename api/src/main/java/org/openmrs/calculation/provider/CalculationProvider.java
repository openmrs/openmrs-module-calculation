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

import org.openmrs.calculation.patient.PatientCalculation;

/**
 * Base interface for classes responsible for retrieving a {@link PatientCalculation} instance given a
 * calculation name and an optional configuration string, a typical implementation of this class
 * could be a spring bean that holds a list of calculations. It should know how to instantiate each
 * one of them based on the name and configuration string
 * {@link #getPatientCalculation(String, String)} is called. The configuration string argument
 * provides an extensibility mechanism for CalculationProviders
 */
public interface CalculationProvider {
	
	/**
	 * Returns a {@link PatientCalculation} instance with the specified calculationName and an optional
	 * configuration string
	 * 
	 * @param calculationName the name to match against
	 * @param configuration an optional configuration string to be used while instantiating the
	 *            calculation
	 * @return a {@link PatientCalculation}
	 */
	public PatientCalculation getPatientCalculation(String calculationName, String configuration);
}
