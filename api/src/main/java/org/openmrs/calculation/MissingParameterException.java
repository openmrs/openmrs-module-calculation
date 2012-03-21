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

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.calculation.parameter.ParameterDefinition;

/**
 * Represents an error that is thrown when a required parameter value is not found or is blank when
 * evaluating a calculation
 */
public class MissingParameterException extends APIException {
	
	private static final long serialVersionUID = 1L;
	
	public MissingParameterException() {
		this("Found one or more missing values for required parameter(s)");
	}
	
	public MissingParameterException(ParameterDefinition parameter) {
		this("The value for the parameter definition '"
		        + (StringUtils.isNotBlank(parameter.getName()) ? parameter.getName() : parameter.getKey()) + "' is required");
	}
	
	public MissingParameterException(String message) {
		super(message);
	}
}
