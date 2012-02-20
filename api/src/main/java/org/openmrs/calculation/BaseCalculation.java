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

import org.openmrs.calculation.definition.ParameterDefinitionSet;

/**
 * Base class for Calculations, this class provides a default and very basic implementation of the
 * {@link #getParameterDefinitionSet()} method by returning a new instance.
 */
public abstract class BaseCalculation implements Calculation {
	
	private ParameterDefinitionSet pds;
	
	/**
	 * @see org.openmrs.calculation.Calculation#getParameterDefinitionSet()
	 */
	@Override
	public ParameterDefinitionSet getParameterDefinitionSet() {
		if (pds == null)
			pds = new ParameterDefinitionSet();
		
		return pds;
	}
}
