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
package org.openmrs.calculation.definition;

/**
 * Base class for date {@link ParameterDefinition}s
 */
public class DateParameterDefinition extends BaseParameterDefinition {
	
	/**
	 * Convenience constructor the takes in a key for the definition
	 * 
	 * @param key the key to the set for the definition
	 */
	public DateParameterDefinition(String key) {
		new DateParameterDefinition(key, null);
	}
	
	/**
	 * Convenience constructor the takes in a key and the name for the definition
	 * 
	 * @param key the key to the set for the definition
	 * @param name the name to the set for the definition
	 */
	public DateParameterDefinition(String key, String name) {
		setKey(key);
		setName(name);
		super.setDatatype("java.util.Date");
	}
	
	/**
	 * Subclasses can't override this method
	 * 
	 * @see org.openmrs.calculation.definition.BaseParameterDefinition#setDatatype(java.lang.String)
	 */
	@Override
	public final void setDatatype(String datatype) {
	}
	
}
