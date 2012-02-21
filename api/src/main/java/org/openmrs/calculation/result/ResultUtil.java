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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.calculation.ConversionException;
import org.openmrs.calculation.util.CalculationUtil;

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
	public static Result getFirst(ListResult result) {
		if (result == null || result.size() == 0)
			return null;
		
		return result.get(0);
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
	 * @should return an empty collection if the result is null and class is a list
	 * @should return an empty collection if the result has a null value and class is a list
	 * @should return an empty collection if the result is null and class is a set
	 * @should return an empty collection if the result has a null value and class is a set
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(Result result, Class<T> clazz) {
		if (clazz == null)
			throw new ConversionException("Please specify a class to which to convert the result");
		
		if (result == null || result.isEmpty()) {
			Collection<T> coll = null;
			if (List.class.isAssignableFrom(clazz))
				coll = Collections.emptyList();
			else if (Set.class.isAssignableFrom(clazz))
				coll = Collections.emptySet();
			
			if (coll != null)
				return (T) coll;
			
			if (Map.class.isAssignableFrom(clazz))
				return (T) Collections.emptyMap();
			
			return null;
		}
		Object valueToConvert = result.getValue();
		if (log.isDebugEnabled())
			log.debug("Attempting to type cast the value '" + valueToConvert + "' of type '" + valueToConvert.getClass()
			        + "' to '" + clazz + "'");
		
		T castValue = null;
		// We should be able to convert any value to a String		
		if (String.class.isAssignableFrom(clazz)) {
			castValue = (T) valueToConvert.toString();
		} else if (CalculationUtil.isPrimitiveWrapperType(clazz) || String.class.isAssignableFrom(clazz)) {
			String stringValue = valueToConvert.toString();
			try {
				if (Character.class.equals(clazz) && stringValue.length() == 1) {
					valueToConvert = stringValue.charAt(0);
				} else {
					Method method = clazz.getMethod("valueOf", new Class<?>[] { String.class });
					valueToConvert = method.invoke(valueToConvert, stringValue);
				}
				
				castValue = clazz.cast(valueToConvert);
			}
			catch (ClassCastException e) {
				throw new ConversionException(result.getValue(), clazz);
			}
			catch (Throwable e) {
				throw new ConversionException(result.getValue(), clazz);
			}
		}
		
		return castValue;
	}
}
