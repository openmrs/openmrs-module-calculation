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

/**
 * A CalculationContext contains any contextual information that may be shared across one or more
 * Calculation evaluations
 */
public interface CalculationContext {
	
	/**
	 * Gets the date on which the evaluation for this context should occur
	 * 
	 * @return the index date
	 */
	public Date getIndexDate();
	
	/**
	 * Sets the date on which the evaluation for this context should occur
	 * 
	 * @param date the index date to set to
	 */
	public void setIndexDate(Date date);
	
	//public CohortResult getFromCache(Cohort, Calculation, Map<String, Object>);
	
	/**
	 * Adds an object to the cache mapped with the specified key
	 * 
	 * @param key the key for object to cache
	 * @param value the value for the object to cache
	 */
	public void addToCache(String key, Object value);
	
	/**
	 * Retrieves an object from the cache mapped to the specified key
	 * 
	 * @param key the key for the cached object to look up
	 * @return the Cached object
	 */
	public Object getFromCache(String key);
	
	/**
	 * Discards the object mapped to the specified key from the cache
	 * 
	 * @param key the unique key for the object to remove
	 */
	public void removeFromCache(String key);
}
