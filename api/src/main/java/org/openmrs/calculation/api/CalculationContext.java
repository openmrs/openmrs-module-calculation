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
package org.openmrs.calculation.api;

import java.util.Date;

import org.openmrs.Cohort;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.result.CohortResult;

/**
 * A CalculationContext contains any contextual information that may be shared across one or more
 * Calculation evaluations
 */
public interface CalculationContext {
	
	/**
	 * Gets the date against which the evaluation for this context should be based
	 * 
	 * @return the index date
	 */
	public Date getNow();
	
	/**
	 * Sets the date against which the evaluation for this context should be based
	 * 
	 * @param date the index date to set to
	 */
	public void setNow(Date date);
	
	/**
	 * Adds an object to the cache mapped to the specified key
	 * 
	 * @param key the key for object to cache
	 * @param value the value for the object to cache
	 */
	public void addToCache(String key, Object value);
	
	/**
	 * Retrieves an object from the cache mapped to the specified key, or null if this context
	 * contains no mapping for the key.
	 * 
	 * @param key the key for the cached object to look up
	 * @return the Cached object
	 */
	public Object getFromCache(String key);
	
	/**
	 * Fetches a {@link CohortResult} from the cache that was a result of an earlier evaluation of
	 * the specified calculation for the specified {@link Cohort} of patients, or null if this
	 * context contains no match
	 * 
	 * @param cohort
	 * @param calculation
	 * @return
	 */
	public CohortResult getFromCache(Cohort cohort, Calculation calculation);
	
	/**
	 * Discards the object mapped to the specified key from the cache
	 * 
	 * @param key the unique key for the object to remove
	 */
	public void removeFromCache(String key);
}
