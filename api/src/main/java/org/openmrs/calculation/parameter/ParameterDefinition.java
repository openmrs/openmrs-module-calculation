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

import org.openmrs.calculation.patient.PatientCalculationContext;

/**
 * This is the base interface for all parameter definitions, a class that implements this interface
 * indicates that it represents a definition that can be evaluated in a {@link PatientCalculationContext}
 * It is highly recommended to extend {@link SimpleParameterDefinition} rather implementing this
 * interface.
 */
public interface ParameterDefinition {
	
	/**
	 * @return the key
	 */
	public String getKey();
	
	/**
	 * @param key the key to set to
	 */
	public void setKey(String key);
	
	/**
	 * @return the name for this parameter
	 */
	public String getName();
	
	/**
	 * @param name the name to set to
	 */
	public void setName(String name);
	
	/**
	 * @return the description for this parameter
	 */
	public String getDescription();
	
	/**
	 * @param description the description to set to
	 */
	public void setDescription(String description);
	
	/**
	 * @return the Java class of this parameter
	 */
	public String getDatatype();
	
	/**
	 * @param datatype the datatype to set to
	 */
	public void setDatatype(String datatype);
	
	/**
	 * @return true if this parameter is nullable otherwise false
	 */
	public boolean isRequired();
	
	/**
	 * @param require whether or not this parameter is nullable
	 */
	public void setRequired(boolean require);
}
