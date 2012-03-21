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


/**
 * Represents an error that is thrown when an invalid {@link Calculation} is requested.
 * This could be if an invalid calculation name is requested of a {@link CalculationProvider}
 * or if an invalid configuration string is specified for a particular {@link Calculation}
 */
public class InvalidCalculationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default Constructor
	 */
	public InvalidCalculationException(String message) {
		super(message);
	}
	
	/**
	 * Default Constructor with cause
	 */
	public InvalidCalculationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * This constructor should be called when an illegal configuration is requested from a valid {@link CalculationProvider}
	 */
	public InvalidCalculationException(CalculationProvider provider, String calculationName, String configuration) {
		this("Calculation '" + calculationName + "' " +
			  "with configuration '" + configuration + "' " +
			  "is invalid for provider '" + provider.getClass().getSimpleName() + "'");
	}
	
	/**
	 * This constructor should be called when an illegal configuration string is passed into a valid {@link Calculation}
	 */
	public InvalidCalculationException(Calculation calculation, String configuration) {
		this("Calculation '" + calculation.getClass().getSimpleName() + "' " +
			  "with configuration '" + configuration + "' is invalid");
	}
}
