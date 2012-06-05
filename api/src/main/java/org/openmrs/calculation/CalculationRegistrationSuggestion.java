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

import java.util.List;


/**
 * This interface provides a mechanism for implementations to provide one or more pre-configured
 * Calculation Registrations for a consumer to choose from, in order to facilitate the registration process.
 * These are not meant to provide the superset of all available Calculation Registration options that
 * are available, but rather as a convenience mechanism for registering a subset of all 
 * possible Calculations more easily.
 */
public interface CalculationRegistrationSuggestion {
	
	/**
	 * @return a user-friendly name or message code for the supplied suggestions
	 */
	public String getName();
	
	/**
	 * @return a List of Calculation Registrations that an end-user could choose to auto-register
	 */
	public List<CalculationRegistration> getSuggestions();
}
