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
 * Constants used by the module.
 */
public final class CalculationConstants {
	
	public static final String MODULE_ID = "calculation";
	
	public static final String PRIV_MANAGE_TOKEN_REGISTRATIONS = "Manage Token Registrations";
	
	public static final String PRIV_VIEW_TOKEN_REGISTRATIONS = "View Token Registrations";
	
	public static final String PRIV_VIEW_CALCULATIONS = "View Calculations";
	
	//the maximum number of cohorts members to evaluate at a time, probably this should be configurable
	public static final int EVALUATION_BATCH_SIZE = 1000;
}
