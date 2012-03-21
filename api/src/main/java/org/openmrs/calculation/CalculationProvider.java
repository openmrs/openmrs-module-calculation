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


/**
 * Base interface for classes responsible for retrieving a {@link Calculation} instance given a
 * calculation name and an optional configuration string, a typical implementation of this class
 * could be a spring bean that holds a list of calculations. It should know how to instantiate each
 * one of them based on the name and configuration string.  It is the responsibility of implementing
 * subclasses to utilize the configuration property as appropriate when providing new Calculation
 * instances.  If a configuration string is passed in that is not valid for a given {@link CalculationProvider}
 * then it should throw an {@link InvalidCalculationException} to indicate this.
 */
public interface CalculationProvider {
	
	/**
	 * Returns a {@link Calculation} instance with the specified calculationName and an optional
	 * configuration string
	 * 
	 * @param calculationName the name to match against
	 * @param configuration an optional configuration string to be used while instantiating the calculation
	 * @return a {@link Calculation}
	 */
	public Calculation getCalculation(String calculationName, String configuration) throws InvalidCalculationException;
}
