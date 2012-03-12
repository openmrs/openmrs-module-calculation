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
package org.openmrs.calculation.util;

import org.apache.commons.lang.ArrayUtils;

/**
 * Contains utility methods for the module
 */
public class CalculationUtil {
	
	//private static final Log log = LogFactory.getLog(CalculationUtil.class);
	
	private static final Class<?>[] PRIMITIVE_TYPES = { Boolean.class, Character.class, Byte.class, Short.class,
	        Integer.class, Float.class, Double.class, Long.class };
	
	/**
	 * Checks if the specified type is a wrapper class for a primitive type
	 * 
	 * @param clazz the class to check
	 * @return true if the class is a wrapper class for a primitive type otherwise false
	 */
	public static boolean isPrimitiveWrapperType(Class<?> clazz) {
		return ArrayUtils.contains(PRIMITIVE_TYPES, clazz);
	}
	
	/**
	 * Checks if the specified class name is for a wrapper class for a primitive type
	 * 
	 * @param className the class name to check
	 * @return true if the class name is for a wrapper class for a primitive type otherwise false
	 */
	public static boolean isPrimitiveWrapperClassName(String className) {
		if (className != null) {
			for (Class<?> type : PRIMITIVE_TYPES) {
				if (className.equals(type.getName()))
					return true;
			}
		}
		return false;
	}
}
