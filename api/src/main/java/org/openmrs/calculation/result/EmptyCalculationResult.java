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

import org.openmrs.calculation.ConversionException;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;

/**
 * Represents a {@link CalculationResult} with no data or has a null value
 */
public class EmptyCalculationResult implements CalculationResult {
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#getCalculation()
	 */
	@Override
	public PatientCalculation getCalculation() {
		throw null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#getCalculationContext()
	 */
	@Override
	public PatientCalculationContext getCalculationContext() {
		throw null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#getValue()
	 */
	@Override
	public Object getValue() {
		return null;
	}
	
	/**
	 * @see org.openmrs.calculation.result.CalculationResult#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public <T> T asType(Class<T> clazz) {
		throw new ConversionException("Cannot convert an empty result");
	}
}
