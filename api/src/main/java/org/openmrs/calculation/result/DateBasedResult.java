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

import java.util.Date;

import org.openmrs.calculation.PatientCalculation;
import org.openmrs.calculation.api.CalculationContext;

/**
 * Base class for {@link Result}s with values that occur on specific dates e.g Encounter has an
 * encounterDatetime
 */
public abstract class DateBasedResult extends SimpleResult {
	
	/**
	 * @see SimpleResult
	 */
	public DateBasedResult(Object value, PatientCalculation calculation) {
		this(value, calculation, null);
	}
	
	/**
	 * @see SimpleResult
	 */
	public DateBasedResult(Object value, PatientCalculation calculation, CalculationContext calculationContext) {
		super(value, calculation, calculationContext);
	}
	
	/**
	 * Returns the date of occurrence of the value that is associated to the result e.g if this is
	 * an EncounterResult, it would be encounterDatetime
	 * 
	 * @return the date when the calculation occurred
	 */
	public abstract Date getDateOfResult();
}
