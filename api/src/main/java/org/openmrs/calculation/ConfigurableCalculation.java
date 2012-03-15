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
 * Interface for Calculations that support configuration
 */
public interface ConfigurableCalculation extends Calculation {
	
	/**
	 * @param the configuration String that should be used to initialize this Calculation
	 */
	public void setConfiguration(String configuration) throws InvalidCalculationException;
}
