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
import org.openmrs.calculation.api.CalculationContext;

/**
 * Provides a concrete and yet simple implementation of a {@link Result} that can be used out of the
 * box.
 */
public class DefaultResult extends BaseResult {
	
	/**
	 * Convenience constructor that takes in a value and a {@link Calculation}
	 * 
	 * @param value the value to set
	 * @param calculation the calculation to set
	 */
	public DefaultResult(Object value, Calculation calculation) {
		new DefaultResult(value, calculation, null);
	}
	
	/**
	 * Convenience constructor that takes in a value, a {@link Calculation} and
	 * {@link CalculationContext} in which it was evaluated
	 * 
	 * @param value the value to set
	 * @param calculation the calculation to set
	 * @param calculationContext the CalculationContext to set
	 */
	public DefaultResult(Object value, Calculation calculation, CalculationContext calculationContext) {
		setValue(value);
		setCalculation(calculation);
		setCalculationContext(calculationContext);
	}
}
