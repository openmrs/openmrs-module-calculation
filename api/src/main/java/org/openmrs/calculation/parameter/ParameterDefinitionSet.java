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
package org.openmrs.calculation.parameter;

import java.util.HashSet;


/**
 * Represents a set of {@link ParameterDefinition}s, it's technically a wrapper of a {@link HashSet}
 * that adds a convenience method to find a {@link ParameterDefinition} by its unique key
 */
public class ParameterDefinitionSet extends HashSet<ParameterDefinition> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the {@link ParameterDefinition} with a matching key
	 * 
	 * @param key the key to match against
	 * @return {@link ParameterDefinition}
	 */
	public ParameterDefinition getParameterByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException("Parameter key cannot be null");
		
		for (ParameterDefinition pd : this) {
			if (key.equalsIgnoreCase(pd.getKey()))
				return pd;
		}
		return null;
	}
}
