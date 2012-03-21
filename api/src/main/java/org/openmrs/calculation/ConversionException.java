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

import org.openmrs.api.APIException;

/**
 * Represents an error that is encountered while attempting to convert a value to a specified type
 */
public class ConversionException extends APIException {
	
	private static final long serialVersionUID = 1L;
	
	public ConversionException() {
		this("Cannot convert the specfied value to the specified type");
	}
	
	@SuppressWarnings("rawtypes")
	public ConversionException(Object value, Class toClass) {
		this("Cannot cast the value '" + value + "'" + (value != null ? " of type '" + value.getClass() + "'" : "")
		        + " to '" + toClass + "'");
	}
	
	public ConversionException(String message) {
		super(message);
	}
}
