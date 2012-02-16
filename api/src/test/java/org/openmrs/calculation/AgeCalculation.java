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
package org.openmrs.calculation;

import java.util.Set;

import org.openmrs.calculation.definition.ParameterDefinition;

/**
 * This is implementation of an age calculation that is purely for testing purposes
 */
public class AgeCalculation implements Calculation {
	
	/**
	 * @see org.openmrs.calculation.Calculation#getParameterDefinitions()
	 */
	@Override
	public Set<ParameterDefinition> getParameterDefinitions() {
		throw new RuntimeException("Not yet implemented");
	}
}
