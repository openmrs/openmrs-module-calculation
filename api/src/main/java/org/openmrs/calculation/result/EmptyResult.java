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
import org.openmrs.calculation.ConversionException;
import org.openmrs.calculation.api.CalculationContext;

/**
 * Represents a {@link Result} with no data
 */
public class EmptyResult implements Result {
	
	/**
	 * @see org.openmrs.calculation.result.Result#getCalculation()
	 */
	@Override
	public Calculation getCalculation() {
		throw null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#getCalculationContext()
	 */
	@Override
	public CalculationContext getCalculationContext() {
		throw null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#getValue()
	 */
	@Override
	public Object getValue() {
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.Result#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public <T> T asType(Class<T> clazz) throws ConversionException {
		throw new ConversionException("Cannot convert an empty result");
	}
}
