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
	 * @param key
	 * @param datatype
	 * @return
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype) {
		return createParameterDefinition(key, datatype, null);
	}
	
	/**
	 * @param key
	 * @param datatype
	 * @param name
	 * @return
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype, String name) {
		return createParameterDefinition(key, datatype, name, false);
	}
	
	/**
	 * @param key
	 * @param datatype
	 * @param name
	 * @param isRequred
	 * @return
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype, String name, boolean isRequred) {
		return createParameterDefinition(key, datatype, name, isRequred, null);
	}
	
	/**
	 * @param key
	 * @param datatype
	 * @param name
	 * @param isRequred
	 * @param description
	 * @return
	 */
	public static ParameterDefinition createParameterDefinition(String key, String datatype, String name, boolean isRequred,
	                                                            String description) {
		ParameterDefinition pd = new BaseParameterDefinition() {};
		pd.setKey(key);
		pd.setName(name);
		pd.setDatatype(datatype);
		pd.setRequired(isRequred);
		return pd;
	}
}
