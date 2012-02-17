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
package org.openmrs.calculation.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.calculation.definition.BaseParameterDefinition;
import org.openmrs.calculation.definition.ParameterDefinition;

/**
 * Contains utility methods for the module
 */
public class CalculationUtil {
	
	private static final Log log = LogFactory.getLog(CalculationUtil.class);
	
	/**
	 * @see #createParameterDefinition(String, String, String)
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype) {
		return createParameterDefinition(key, datatype, null);
	}
	
	/**
	 * @see #createParameterDefinition(String, String, String, boolean)
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype, String name) {
		return createParameterDefinition(key, datatype, name, false);
	}
	
	/**
	 * @see #createParameterDefinition(String, String, String, boolean, String)
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype, String name, boolean isRequred) {
		return createParameterDefinition(key, datatype, name, isRequred, null);
	}
	
	/**
	 * Utility method that creates a {@link ParameterDefinition} instance and initializes it
	 * properties to the specified values
	 * 
	 * @param key the key to set
	 * @param datatype the datatype to set
	 * @param name the name to set
	 * @param isRequred whether this property should be marked as required or not for the evaluation
	 *            of the associated calculation to occur
	 * @param description the description to set
	 * @return a {@link ParameterDefinition}
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype, String name, boolean isRequred,
	                                                            String description) {
		if (log.isDebugEnabled())
			log.debug("Creating parameter with key:" + key);
		
		ParameterDefinition pd = new BaseParameterDefinition() {};
		pd.setKey(key);
		pd.setName(name);
		pd.setDatatype(datatype);
		pd.setRequired(isRequred);
		return pd;
	}
}
