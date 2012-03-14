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
import org.openmrs.calculation.patient.PatientCalculation;

/**
 * A very simple implementation of a calculation for testing purposes
 */
public class DummyCalculation implements PatientCalculation {
	
	
	/** This field added for testing */
	private String configuartion = null;
	
	public DummyCalculation() {
	}
	
	/**
	 * @see Calculation#setConfiguration(String)
	 */
	@Override
	public void setConfiguration(String configuration) { 
		this.configuartion = configuration;
	}

	/**
	 * @see org.openmrs.calculation.BaseCalculation#getParameterDefinitionSet()
	 */
	@Override
	public ParameterDefinitionSet getParameterDefinitionSet() {
		return null;
	}
	
	/**
	 * NOTE: this method added only for testing purposes
	 *  
	 * @return configuration that was set to this calculation
	 */
	public String getConfiguration() {
		return configuartion;
	}
}
