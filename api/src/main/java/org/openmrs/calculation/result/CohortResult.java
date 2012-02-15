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
package org.openmrs.calculation.result;

import java.util.HashMap;

import org.openmrs.Cohort;
import org.openmrs.Patient;
import org.openmrs.calculation.Calculation;

/**
 * A CohortResult is the data that is produced from evaluating a {@link Calculation} for a
 * {@link Cohort} of {@link Patient}s.
 */
public class CohortResult extends HashMap<Integer, Result> {
	
	private static final long serialVersionUID = 1L;
}
