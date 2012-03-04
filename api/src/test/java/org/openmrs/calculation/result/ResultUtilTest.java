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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.calculation.ConversionException;
import org.openmrs.test.Verifies;

public class ResultUtilTest {
	
	/**
	 * @see {@link ResultUtil#getFirst(Result)}
	 */
	@Test
	@Verifies(value = "should get the first result if the value of the result is a list", method = "getFirst(Result)")
	public void getFirst_shouldGetTheFirstResultIfTheValueOfTheResultIsAList() throws Exception {
		ListResult listResult = new ListResult();
		List<Result> results = new ArrayList<Result>();
		results.add(new SimpleResult("first", null));
		results.add(new SimpleResult("second", null));
		listResult.setValue(results);
		
		Result firstResult = ResultUtil.getFirst(listResult);
		Assert.assertEquals("first", firstResult.getValue());
	}
	
	/**
	 * @see {@link ResultUtil#getFirst(Result)}
	 */
	@Test
	@Verifies(value = "should return the same result if the value of the result is a not a list", method = "getFirst(Result)")
	public void getFirst_shouldReturnTheSameResultIfTheValueOfTheResultIsANotAList() throws Exception {
		Result result = new SimpleResult("result", null);
		Assert.assertEquals(result, ResultUtil.getFirst(result));
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a character value to Character", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithACharacterValueToCharacter() throws Exception {
		Assert.assertEquals('c', ResultUtil.convert(new SimpleResult("c", null), Character.class).charValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a string value to Boolean", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAStringValueToBoolean() throws Exception {
		Assert.assertEquals(true, ResultUtil.convert(new SimpleResult("true", null), Boolean.class).booleanValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a string value to Byte", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAStringValueToByte() throws Exception {
		Assert.assertEquals(2, ResultUtil.convert(new SimpleResult("2", null), Byte.class).byteValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a string value to Double", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAStringValueToDouble() throws Exception {
		Assert.assertEquals(1.7976931348623157e+308,
		    ResultUtil.convert(new SimpleResult("1.7976931348623157e+308", null), Double.class).doubleValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a string value to Float", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAStringValueToFloat() throws Exception {
		Assert.assertEquals(3.4028235e+1f, ResultUtil.convert(new SimpleResult("3.4028235e+1f", null), Float.class)
		        .floatValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a string value to Long", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAStringValueToLong() throws Exception {
		Assert.assertEquals(122, ResultUtil.convert(new SimpleResult("122", null), Long.class).longValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a string value to Short", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAStringValueToShort() throws Exception {
		Assert.assertEquals(122, ResultUtil.convert(new SimpleResult(new Short("122").toString(), null), Short.class)
		        .shortValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with an string value to Integer", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAnStringValueToInteger() throws Exception {
		Assert.assertEquals(122, ResultUtil.convert(new SimpleResult(new Integer("122").toString(), null), Integer.class)
		        .intValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a single character value to Integer", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithASingleCharacterValueToInteger() throws Exception {
		Assert.assertEquals(2, ResultUtil.convert(new SimpleResult('2', null), Integer.class).intValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a single character value to Long", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithASingleCharacterValueToLong() throws Exception {
		Assert.assertEquals(2, ResultUtil.convert(new SimpleResult('2', null), Long.class).longValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with a single character value to Short", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithASingleCharacterValueToShort() throws Exception {
		Assert.assertEquals(2, ResultUtil.convert(new SimpleResult('2', null), Short.class).shortValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with an number value in the allowed range to byte", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertAResultWithAnNumberValueInTheAllowedRangeToByte() throws Exception {
		Assert.assertEquals(120, ResultUtil.convert(new SimpleResult(120, null), Byte.class).byteValue());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should return null if the passed in result has a null value", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnNullIfThePassedInResultHasANullValue() throws Exception {
		Assert.assertNull(ResultUtil.convert(new SimpleResult(null, null), String.class));
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should return null if the passed in result is null", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnNullIfThePassedInResultIsNull() throws Exception {
		Assert.assertNull(ResultUtil.convert(null, String.class));
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result has a null value and class is a list", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultHasANullValueAndClassIsAList() throws Exception {
		List list = ResultUtil.convert(new SimpleResult(null, null), List.class);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result is null and class is a list", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultIsNullAndClassIsAList() throws Exception {
		List list = ResultUtil.convert(null, List.class);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty map if the result has a null value and class is a map", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnAnEmptyMapIfTheResultHasANullValueAndClassIsAMap() throws Exception {
		Map map = ResultUtil.convert(new SimpleResult(null, null), Map.class);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty map if the result is null and class is a map", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnAnEmptyMapIfTheResultIsNullAndClassIsAMap() throws Exception {
		Map map = ResultUtil.convert(null, Map.class);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result has a null value and class is a set", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultHasANullValueAndClassIsASet() throws Exception {
		Set set = ResultUtil.convert(new EmptyResult(), Set.class);
		Assert.assertNotNull(set);
		Assert.assertTrue(set.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result is null and class is a set", method = "convert(Result,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultIsNullAndClassIsASet() throws Exception {
		Set set = ResultUtil.convert(null, Set.class);
		Assert.assertNotNull(set);
		Assert.assertTrue(set.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert the value of a result to the specified type if it is compatible", method = "convert(Result,Class<T>)")
	public void convert_shouldConvertTheValueOfAResultToTheSpecifiedTypeIfItIsCompatible() throws Exception {
		Person convertedObject = ResultUtil.convert(new SimpleResult(new Patient(), null), Person.class);
		Assert.assertNotNull(convertedObject);
		Assert.assertTrue(Person.class.isAssignableFrom(convertedObject.getClass()));
	}
	
	/**
	 * @see {@link ResultUtil#convert(Result,Class<T>)}
	 */
	@Test(expected = ConversionException.class)
	@Verifies(value = "should fail if the value of a result is not of a compatible type", method = "convert(Result,Class<T>)")
	public void convert_shouldFailIfTheValueOfAResultIsNotOfACompatibleType() throws Exception {
		ResultUtil.convert(new SimpleResult(new Person(), null), Patient.class);
	}
}
