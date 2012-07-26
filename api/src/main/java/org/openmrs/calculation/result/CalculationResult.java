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
package org.openmrs.calculation.result;

import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.CalculationContext;

/**
 * A CalculationResult is the data that is produced from evaluating a {@link Calculation} for a
 * single patient.
 */
public interface CalculationResult {
	
	/**
	 * @return the Calculation that was evaluated to produce this result
	 */
	public Calculation getCalculation();
	
	/**
	 * @return the CalculationContext used when the Calculation was evaluated
	 */
	public CalculationContext getCalculationContext();
	
	/**
	 * @return the raw object value (eg. a Patient or an Obs)
	 */
	public Object getValue();
	
	/**
	 * @return true if the object value is null, an empty list otherwise false
	 * @see ResultUtil#isEmpty(Object)
	 */
	public boolean isEmpty();
	
	/**
	 * Attempts to convert the result to the specified type
	 * 
	 * @param clazz the class to convert to
	 * @return the converted result
	 */
	public <T> T asType(Class<T> clazz);
}
