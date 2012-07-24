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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.CalculationContext;

/**
 * A {@link CalculationResult} backed by a {@link List} of results with convenience methods that get
 * the first and last results in the backing list
 */
public class ListResult implements CalculationResult {
	
	private CalculationContext calculationContext;
	
	private Calculation calculation;
	
	private List<CalculationResult> results;
	
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
		if (results == null)
			results = new ArrayList<CalculationResult>();
		return results;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(List<CalculationResult> value) {
		this.results = value;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return results == null || results.isEmpty();
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#asType(java.lang.Class)
	 */
	@Override
	public <T> T asType(Class<T> clazz) {
		return ResultUtil.convert(this, clazz);
	}
	
	/**
	 * Gets the first result in the backing list
	 * 
	 * @return the first result {@link CalculationResult}
	 */
	public CalculationResult getFirstResult() {
		return isEmpty() ? null : results.get(0);
	}
	
	/**
	 * Gets the last result in the backing list
	 * 
	 * @return the last result {@link CalculationResult}
	 */
	public CalculationResult getLastResult() {
		return isEmpty() ? null : results.get(results.size() - 1);
	}
	
	/**
	 * Adds a result to the list
	 * 
	 * @param result the result to add
	 * @return the added {@link CalculationResult}
	 */
	public CalculationResult add(CalculationResult result) {
		if (results == null) {
			results = new ArrayList<CalculationResult>();
		}
		
		results.add(result);
		
		return result;
	}
	
	/**
	 * Removes a given result from the list
	 * 
	 * @param result the result to remove
	 * @return true if this list contained the specified result and it was removed
	 */
	public boolean remove(CalculationResult result) {
		if (isEmpty()) {
			return false;
		}
		
		return results.remove(result);
	}
	
	/**
	 * Removes the result at a given position in the list
	 * 
	 * @param index the position
	 * @return the removed {@link CalculationResult}
	 */
	public CalculationResult remove(int index) {
		if (isEmpty()) {
			return null;
		}
		
		return results.remove(index);
	}
	
	/**
	 * Equivalent to: this.results.collect { it.value }
	 * 
	 * @return a list equal in length to this list, but whose elements are the values of this list's
	 *         elements
	 * @should return list of underlying values
	 */
	public List<Object> getValues() {
		List<Object> ret = new ArrayList<Object>();
		if (results != null) {
			for (CalculationResult r : results) {
				ret.add(r.getValue());
			}
		}
		return ret;
	}
	
	/**
	 * @see Collection#size()
	 * @return the number of results in this list
	 */
	public Object size() {
		return results == null ? 0 : results.size();
	}
}
