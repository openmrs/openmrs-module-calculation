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

import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.Calculation;

/**
 * Implementation of {@link CalculationProvider} which retrieves a {@link Calculation}
 * given a fully qualified class name of the {@link Calculation} to instantiate
 */
public class ClasspathCalculationProvider {
	
	/**
	 * @see CalculationProvider#getCalculation(String, String)
	 */
	public Calculation getCalculation(String calculationName, String configuration) {
		Calculation calculation = null;
		try {
			Class<?> c = Context.loadClass(calculationName);
			calculation = (Calculation)c.newInstance();
		}
		catch (Exception e) {
			throw new APIException("Unable to load Calculation class with name '" + calculationName + "'");
		}
		calculation.setConfiguration(configuration);
		return calculation;
	}
}
