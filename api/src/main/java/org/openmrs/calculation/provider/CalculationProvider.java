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
package org.openmrs.calculation.provider;

import org.openmrs.calculation.Calculation;

/**
 * Base interface for classes responsible for retrieving a {@link Calculation} instance given a
 * calculation name and an optional configuration string
 */
public interface CalculationProvider {
	
	public Calculation getCalculationInstance(String calculationName, String configuration);
}
