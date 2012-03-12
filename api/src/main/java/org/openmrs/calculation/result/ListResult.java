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

import java.util.List;

import org.openmrs.calculation.PatientCalculation;
import org.openmrs.calculation.api.patient.PatientCalculationContext;

/**
 * A {@link Result} backed by a {@link List} of results with convenience methods that get the first
 * and last results in the backing list
 */
public class ListResult implements Result {
	
	private static final long serialVersionUID = 1L;
	
	private PatientCalculationContext calculationContext;
	
	private PatientCalculation calculation;
	
	private List<Result> results;
	
	/**
	 * @see org.openmrs.calculation.result.Result#getCalculation()
	 */
	@Override
	public PatientCalculation getCalculation() {
		return calculation;
	}
	
	/**
	 * @param calculation the calculation to set
	 */
	public void setCalculation(PatientCalculation calculation) {
		this.calculation = calculation;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#getCalculationContext()
	 */
	@Override
	public PatientCalculationContext getCalculationContext() {
		return calculationContext;
	}
	
	/**
	 * @param calculationContext the calculationContext to set
	 */
	public void setCalculationContext(PatientCalculationContext calculationContext) {
		this.calculationContext = calculationContext;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#getValue()
	 */
	@Override
	public Object getValue() {
		return results;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(List<Result> value) {
		this.results = value;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return results == null || results.isEmpty();
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#asType(java.lang.Class)
	 */
	@Override
	public <T> T asType(Class<T> clazz) {
		return ResultUtil.convert(this, clazz);
	}
	
	/**
	 * Gets the first result in the backing list
	 * 
	 * @return the first result {@link Result}
	 */
	public Result getFirstResult() {
		return isEmpty() ? new EmptyResult() : results.get(0);
	}
	
	/**
	 * Gets the last result in the backing list
	 * 
	 * @return the last result {@link Result}
	 */
	public Result getLastResult() {
		return isEmpty() ? new EmptyResult() : results.get(results.size() - 1);
	}
}
