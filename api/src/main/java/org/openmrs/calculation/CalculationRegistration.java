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

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.util.OpenmrsUtil;

/**
 * A CalculationRegistration represents a saved Calculation instance. The intention is to allow a fully
 * configured Calculation instance to be retrieved given a unique token String.
 * The "token" property of the CalculationRegistration is the unique String that identifies this saved Calculation
 * The "providerClassName" + "calculationName" + "configuration" properties fully describe how
 * the Calculation instance should be constructed and returned for this token
 */
public class CalculationRegistration extends BaseOpenmrsObject implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String token;
	private String providerClassName;
	private String calculationName;
	private String configuration;
	
	/**
	 * Default Constructor
	 */
	public CalculationRegistration() {}
	
	/**
	 * Full Constructor
	 */
	public CalculationRegistration(String token, String providerClassName, String calculationName, String configuration) {
		this.token = token;
		this.providerClassName = providerClassName;
		this.calculationName = calculationName;
		this.configuration = configuration;
	}
	
	/**
	 * @see OpenmrsObject#getId()
	 */
	@Override
	public Integer getId() {
		return id;
	}
	
	/**
	 * @see OpenmrsObject#setId(Integer)
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the providerClassName
	 */
	public String getProviderClassName() {
		return providerClassName;
	}
	
	/**
	 * @param providerClassName the providerClassName to set
	 */
	public void setProviderClassName(String providerClassName) {
		this.providerClassName = providerClassName;
	}
	
	/**
	 * @return the calculationName
	 */
	public String getCalculationName() {
		return calculationName;
	}

	/**
	 * @param calculationName the calculationName to set
	 */
	public void setCalculationName(String calculationName) {
		this.calculationName = calculationName;
	}

	/**
	 * @return the configuration
	 */
	public String getConfiguration() {
		return configuration;
	}
	
	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CalculationRegistration))
			return false;
		CalculationRegistration other = (CalculationRegistration) obj;
		return OpenmrsUtil.nullSafeEquals(this.getUuid(), other.getUuid());
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (getUuid() == null)
			return super.hashCode();
		return getUuid().hashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (getToken() == null)
			return super.toString();
		return getToken();
	}
	
}
