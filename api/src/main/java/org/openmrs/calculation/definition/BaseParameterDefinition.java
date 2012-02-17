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

import org.openmrs.util.OpenmrsUtil;

/**
 * Base class for {@link ParameterDefinition}s.
 */
public abstract class BaseParameterDefinition implements ParameterDefinition {
	
	/**
	 * Unique identifier for a calculation and and expected to be a valid java variable name.
	 */
	private String key;
	
	/**
	 * The parameter's display label, can be any logical string e.g Start Date
	 */
	private String name;
	
	/**
	 * The description for the parameter
	 */
	private String description;
	
	/**
	 * The Java class of this parameter
	 */
	private String datatype;
	
	/**
	 * Specifies whether the the parameter is nullable or not. If true, this parameter must have a
	 * non-null value for evaluation to occur
	 */
	private boolean required;
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#setKey(java.lang.String)
	 */
	@Override
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#getDatatype()
	 */
	@Override
	public String getDatatype() {
		return datatype;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#setDatatype(java.lang.String)
	 */
	@Override
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#isRequired()
	 */
	@Override
	public boolean isRequired() {
		return required;
	}
	
	/**
	 * @see org.openmrs.calculation.definition.ParameterDefinition#setRequired(boolean)
	 */
	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ParameterDefinition))
			return false;
		ParameterDefinition other = (ParameterDefinition) obj;
		return OpenmrsUtil.nullSafeEquals(this.getKey(), other.getKey());
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (getKey() == null)
			return super.hashCode();
		return getKey().hashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (getName() == null)
			return "";
		return getName();
	}
	
}
