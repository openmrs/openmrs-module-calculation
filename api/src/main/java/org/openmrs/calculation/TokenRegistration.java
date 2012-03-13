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

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.util.OpenmrsUtil;

/**
 * A TokenRegistration represents a saved Calculation instance. The intention is to allow a fully
 * configured Calculation instance to be retrieved given a unique name String.
 * The "name" property of the TokenRegistration is the unique Token that is Registered.
 * The "providerClassName" + "calculationName" + "configuration" properties fully describe how
 * the Calculation instance should be constructed and returned for this token
 */
public class TokenRegistration extends BaseOpenmrsMetadata implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer tokenRegistrationId;
	private String providerClassName;
	private String calculationName;
	private String configuration;
	
	/**
	 * @return the tokenRegistrationId
	 */
	public Integer getTokenRegistrationId() {
		return tokenRegistrationId;
	}
	
	/**
	 * @param tokenRegistrationId the tokenRegistrationId to set
	 */
	public void setTokenRegistrationId(Integer tokenRegistrationId) {
		this.tokenRegistrationId = tokenRegistrationId;
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
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	@Override
	public Integer getId() {
		return getTokenRegistrationId();
	}
	
	/**
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		setTokenRegistrationId(id);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TokenRegistration))
			return false;
		TokenRegistration other = (TokenRegistration) obj;
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
		if (getName() == null)
			return "";
		return getName();
	}
	
}
