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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.calculation.ConversionException;

/**
 * Contains utility methods to handle {@link Result}s
 */
public class ResultUtil {
	
	private static final Log log = LogFactory.getLog(ResultUtil.class);
	
	/**
	 * Gets the first result if the value of the result is a list, or self if a single result
	 * 
	 * @param result
	 * @return
	 * @should get the first result if the value of the result is a list
	 * @should return the same result if the value of the result is a not a list
	 */
	public static Result getFirst(Result result) {
		//TODO Add implementation code
		throw new APIException("Not yet implemented");
	}
	
	/**
	 * Converts the value of the specified result by attempting to type cast it to the specified
	 * type.
	 * 
	 * @param result the result to convert
	 * @param clazz the class to convert to
	 * @return a value of the specified type
	 * @should convert a result with a string value to Boolean
	 * @should convert a result with a character value to Character
	 * @should convert a result with a string value to Short
	 * @should convert a result with an string value to Integer
	 * @should convert a result with a string value to Long
	 * @should convert a result with a string value to Float
	 * @should convert a result with a string value to Double
	 * @should convert a result with a string value to Byte
	 * @should convert a result with a single character value to Short
	 * @should convert a result with a single character value to Integer
	 * @should convert a result with a single character value to Long
	 * @should convert a result with an number value in the allowed range to byte
	 * @should return null if the passed in result is null
	 * @should return null if the passed in result has a null value
	 * @should return an empty map if the result is null and class is a map
	 * @should return an empty map if the result has a null value and class is a map
	 * @should return an empty collection if the result is null and class is a collection
	 * @should return an empty collection if the result has a null value and class is a collection
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(Result result, Class<T> clazz) {
		if (result == null || result.isEmpty())
			throw new ConversionException("Cannot convert a null result nor a result with a null value");
		if (clazz == null)
			throw new ConversionException("Please specify a class to which to convert the result");
		
		Object valueToConvert = result.getValue();
		if (log.isDebugEnabled())
			log.debug("Attempting to type cast the value '" + valueToConvert + "' of type '" + valueToConvert.getClass()
			        + "' to '" + clazz + "'");
		
		T castValue = null;
		// We should be able to convert any value to a String		
		if (String.class.isAssignableFrom(clazz)) {
			castValue = (T) valueToConvert.toString();
		} else {
			/*
			 * TODO Add improved conversions to wrapper classes for primitive types, see snippet below;
			 * 
			 * if (Integer.class.isAssignableFrom(clazz)) {
			 * 		castValue = Integer.valueOf(valueToConvert.toString()); 
			 * }
			 */
			try {
				castValue = clazz.cast(valueToConvert);
			}
			catch (ClassCastException e) {
				throw new ConversionException(result.getValue(), clazz);
			}
		}
		
		return castValue;
	}
}
