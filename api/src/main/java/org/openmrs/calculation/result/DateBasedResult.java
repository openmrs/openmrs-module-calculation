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

import org.openmrs.calculation.Calculation;

/**
 * Superclass of {@link Result}s with {@link Calculation}s that are evaluated on a specific date
 */
public interface DateBasedResult extends Result {
	
	/**
	 * The date when the calculation for this result should be evaluated
	 * 
	 * @return
	 */
	public Date getDateOfResult();
}
