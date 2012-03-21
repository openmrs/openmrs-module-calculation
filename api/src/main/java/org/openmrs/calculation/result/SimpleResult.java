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
 * Provides a simple concrete implementation of a {@link CalculationResult} that can be used out of the box.
 */
public class SimpleResult implements CalculationResult {
	
	protected CalculationContext calculationContext;
	
	protected Calculation calculation;
	
	protected Object value;
	
	/**
	 * Convenience constructor that takes in a value and a {@link Calculation}
	 * 
	 * @param value the value to set
	 * @param calculation the calculation to set
	 */
	public SimpleResult(Object value, Calculation calculation) {
		this(value, calculation, null);
	}
	
	/**
	 * Convenience constructor that takes in a value, a {@link Calculation} and
	 * {@link CalculationContext} in which it was evaluated
	 * 
	 * @param value the value to set
	 * @param calculation the calculation to set
	 * @param calculationContext the CalculationContext to set
	 */
	public SimpleResult(Object value, Calculation calculation, CalculationContext calculationContext) {
		this.value = value;
		this.calculation = calculation;
		this.calculationContext = calculationContext;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#getCalculation()
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
	 * @see org.openmrs.calculation.result.CalculationResult#getCalculationContext()
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
	 * @see org.openmrs.calculation.result.CalculationResult#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#asType(java.lang.Class)
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
		if (value == null)
			return "";
		
		return value.toString();
	}
}
