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
import org.openmrs.calculation.api.exception.ConversionException;

/**
 * Contains utility methods to handle {@link Result}s
 */
public class ResultUtil {
	
	private static final Log log = LogFactory.getLog(ResultUtil.class);
	
	/**
	 * Gets the first result if a list, or self if a single result
	 * 
	 * @param result
	 * @return
	 */
	public static Result getFirst(Result result) {
		//TODO Add implementation code
		throw new APIException("Not yet implemented");
	}
	
	/**
	 * Converts the specified result to the specified type
	 * 
	 * @param result the result to convert
	 * @param clazz the class to convert to
	 * @return
	 */
	public static <T> T convert(Result result, Class<T> clazz) {
		//TODO Add implementation code
		throw new ConversionException("Not yet implemented");
	}
}
