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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.calculation.CalculationUtil;
import org.openmrs.calculation.ConversionException;
import org.openmrs.util.OpenmrsConstants;

/**
 * Contains utility methods to handle {@link CalculationResult}s
 */
public class ResultUtil {
	
	private static final Log log = LogFactory.getLog(ResultUtil.class);
	
	/**
	 * Gets the first result if the passed in result is a {@link ListResult}, or self if it is not a
	 * {@link ListResult}
	 * 
	 * @param result
	 * @return
	 * @should get the first result if the value of the result is a list
	 * @should return the same result if the value of the result is a not a list
	 */
	public static CalculationResult getFirst(CalculationResult result) {
		if (result instanceof ListResult)
			return ((ListResult) result).getFirstResult();
		
		return result;
	}
	
	/**
	 * Converts the value of the specified result by attempting to type cast it to the specified
	 * type.
	 * 
	 * @param result the result to convert
	 * @param clazz the class to convert to
	 * @return a value of the specified type
	 * @should return null if the passed in result is null
	 * @should return an empty map if the result is null and class is a map
	 * @should return an empty map if the result has a null value and class is a map
	 * @should return an empty collection if the result is null and class is a list
	 * @should return an empty collection if the result has a null value and class is a list
	 * @should return an empty collection if the result is null and class is a set
	 * @should return an empty collection if the result has a null value and class is a set
	 * @should return true when converting an arbitrary object to Boolean
	 * @should return false when converting falsey values to Boolean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(CalculationResult result, Class<T> clazz) {
		if (clazz == null)
			throw new ConversionException("Please specify a class to which to convert the result");
		
		if (Boolean.class.equals(clazz)) {
			return (T) Boolean.valueOf(isTrue(result));
		}
		
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
		
		return CalculationUtil.cast(valueToConvert, clazz);
	}
	
	/**
	 * If you pass in a CalculationResult, this method will operate upon its getValue, otherwise it will operate on the
	 * object itself.
	 * 
	 * "Truthy" is defined as "not falsey".
	 * @see #isFalse(Object)
	 * 
	 * @param o
	 * @return whether the given object is "truthy"
	 */
	public static boolean isTrue(Object o) {
		return !isFalse(o);
	}
	
	/**
	 * If you pass in a CalculationResult, this method will operate upon its getValue, otherwise it will operate on the
	 * object itself.
	 * 
	 * The following are treated as false:
	 * <ul>
	 * <li>null</li>
	 * <li>empty collections/maps</li>
	 * <li>the number 0</li>
	 * <li>the empty string</li>
	 * <li>numeric Obs whose value is 0</li>
	 * <li>text Obs whose value is ""</li>
	 * <li>coded Obs whose value is the GlobalProperty concept.false</li>
	 * <li>boolean Obs whose value is false</li>
	 * </ul>
	 * * the number 0
	 * * the empty string
	 * * numeric Ob
	 * 
	 * @param o
	 * @return whether the given object is "falsey"
	 * @should return true for null
	 * @should return true for an empty collection
	 * @should return false for a non-empty collection
	 * @should return true for an empty map
	 * @should return false for a non-empty map
	 * @should return true for an empty string
	 * @should return false for a non-empty string
	 * @should return true for the number 0
	 * @should return false for a non-zero number
	 * @should return true for a numeric Obs whose value is 0
	 * @should return false for a numeric Obs whose value is not 0
	 * @should return true for a text Obs whose value is the empty string
	 * @should return false for a text Obs whose value is not the empty string
	 * @should return true for a coded Obs whose value is the GlobalProperty concept.false
	 * @should return false for a coded Obs whose value is not the GlobalProperty concept.false
	 * @should return true for a boolean Obs whose value is false
	 * @should return false for a boolean Obs whose value is true
	 * @should return true for an empty ListResult
	 * @should return false for non-empty ListResult
	 * @should return true for an empty SimpleResult
	 * @should return false for a non-empty SimpleResult
	 */
	public static boolean isFalse(Object o) {
		if (o == null) {
			return true;
		} else if (o instanceof CalculationResult) {
			CalculationResult cr = (CalculationResult) o;
			return cr.isEmpty() || isFalse(cr.getValue());
		} else if (o instanceof Boolean) {
			return Boolean.FALSE.equals(o);
		} else if (o instanceof String) {
			return "".equals(o);
		} else if (o instanceof Collection<?>) {
			return ((Collection<?>) o).isEmpty();
		} else if (o instanceof Map<?, ?>) {
			return ((Map<?, ?>) o).isEmpty();
		} else if (o instanceof Integer || o instanceof Long) {
			return ((Number) o).longValue() == 0l;
		} else if (o instanceof BigDecimal) {
			return o.equals(BigDecimal.ZERO);
		} else if (o instanceof BigInteger) {
			return o.equals(BigInteger.ZERO);
		} else if (o instanceof Number) {
			// Double, Float, and anything else
			return ((Number) o).doubleValue() == 0d;
		} else if (o instanceof Obs) {
			Obs obs = (Obs) o;
			if (obs.getConcept().getDatatype().isNumeric()) {
				return isFalse(obs.getValueNumeric());
			} else if (obs.getConcept().getDatatype().isBoolean()) {
				// ideally we'd want to use obs.getValueBoolean, but this isn't introduced until 1.7 and this module requires 1.6.5.
				return isFalse(obs.getValueAsBoolean());
			} else if (obs.getConcept().getDatatype().isText()) {
				return isFalse(obs.getValueText());
			} else if (obs.getConcept().getDatatype().isCoded()) {
				if (OpenmrsConstants.OPENMRS_VERSION_SHORT.startsWith("1.6.")) {
					// prior to 1.7 booleans were represented as numeric 0 or 1, so if this is 1.6.x, we treat all non-null coded obs as true
					return obs.getValueCoded() == null;
				} else {
					// in 1.7+, there is a GlobalProperty for concept.false, and we should treat that as falsey
					Boolean b = obs.getValueAsBoolean();
					if (b == null) {
						// the obs.getValueAsBoolean method returns null for any valueCoded that isn't explicitly True or False
						return obs.getValueCoded() == null;
					} else {
						// if the value is explicitly the GP concept.true or concept.false, we'll get back a value here
						return !b;
					}
				}
			}
		} else if (o instanceof Concept) {
			// TODO handle this if we're on OpenMRS 1.7+ when ConceptService.getFalseConcept() was introduced 
		}
		return false;
	}
	
	/**
	 * Similar to {@link #isFalse(Object)}, but some false values (the number 0, the empty string, and coded
	 * values of GP:concept.false) are <em>not</em> treated as empty.
	 * 
	 * @param o
	 * @return for CalculationResults, delegates to {@link CalculationResult#isEmpty()}, otherwise returns true for null, or for empty Collections and Maps
	 * @should return true for null
	 * @should return true for empty collections
	 * @should return true for empty maps
	 * @should return false for non-empty collections
	 * @should return false for non-empty maps
	 * @should return false for plain objects
	 */
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		} else if (o instanceof CalculationResult) {
			return ((CalculationResult) o).isEmpty();
		} else if (o instanceof Collection<?>) {
			return ((Collection<?>) o).isEmpty();
		} else if (o instanceof Map<?, ?>) {
			return ((Map<?, ?>) o).isEmpty();
		}
		return false;
	}

}
