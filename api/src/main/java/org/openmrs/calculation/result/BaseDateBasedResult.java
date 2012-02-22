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
 * Base class for {@link BaseDateBasedResult}s
 */
public abstract class BaseDateBasedResult implements DateBasedResult {
	
	private CalculationContext calculationContext;
	
	private Calculation calculation;
	
	/**
	 * @see org.openmrs.calculation.result.Result#getCalculation()
	 */
	@Override
	public Calculation getCalculation() {
		return calculation;
	}
	
	/**
	 * @param calculation the calculation to set
	 */
	public void setCalculation(Calculation calculation) {
		this.calculation = calculation;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#getCalculationContext()
	 */
	@Override
	public CalculationContext getCalculationContext() {
		return calculationContext;
	}
	
	/**
	 * @param calculationContext the calculationContext to set
	 */
	public void setCalculationContext(CalculationContext calculationContext) {
		this.calculationContext = calculationContext;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#asType(java.lang.Class)
	 */
	@Override
	public <T> T asType(Class<T> clazz) {
		return ResultUtil.convert(this, clazz);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (getValue() == null)
			return super.toString();
		
		return getValue().toString();
	}
}
