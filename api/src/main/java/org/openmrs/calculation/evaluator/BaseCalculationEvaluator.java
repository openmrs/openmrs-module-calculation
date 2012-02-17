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
package org.openmrs.calculation.evaluator;

import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.result.CohortResult;

/**
 * Base class for all calculation evaluators
 */
public abstract class BaseCalculationEvaluator implements CalculationEvaluator {
	
	/**
	 * @see org.openmrs.calculation.evaluator.CalculationEvaluator#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.Calculation, java.util.Map,
	 *      org.openmrs.calculation.api.CalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, Calculation calculation, Map<String, Object> parameterMap,
	                             CalculationContext context) {
		return new CohortResult();
	}
	
}
