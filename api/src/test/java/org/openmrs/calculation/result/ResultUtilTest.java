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
import org.openmrs.test.Verifies;

public class ResultUtilTest {
	
	/**
	 * @see {@link ResultUtil#getFirst(CalculationResult)}
	 */
	@Test
	@Verifies(value = "should get the first result if the value of the result is a list", method = "getFirst(CalculationResult)")
	public void getFirst_shouldGetTheFirstResultIfTheValueOfTheResultIsAList() throws Exception {
		ListResult listResult = new ListResult();
		List<CalculationResult> results = new ArrayList<CalculationResult>();
		results.add(new SimpleResult("first", null));
		results.add(new SimpleResult("second", null));
		listResult.setValue(results);
		
		CalculationResult firstResult = ResultUtil.getFirst(listResult);
		Assert.assertEquals("first", firstResult.getValue());
	}
	
	/**
	 * @see {@link ResultUtil#getFirst(CalculationResult)}
	 */
	@Test
	@Verifies(value = "should return the same result if the value of the result is a not a list", method = "getFirst(CalculationResult)")
	public void getFirst_shouldReturnTheSameResultIfTheValueOfTheResultIsANotAList() throws Exception {
		CalculationResult result = new SimpleResult("result", null);
		Assert.assertEquals(result, ResultUtil.getFirst(result));
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@Test
	@Verifies(value = "should return null if the passed in result is null", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnNullIfThePassedInResultIsNull() throws Exception {
		Assert.assertNull(ResultUtil.convert(null, String.class));
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result has a null value and class is a list", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultHasANullValueAndClassIsAList() throws Exception {
		List list = ResultUtil.convert(new SimpleResult(null, null), List.class);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result is null and class is a list", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultIsNullAndClassIsAList() throws Exception {
		List list = ResultUtil.convert(null, List.class);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty map if the result has a null value and class is a map", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnAnEmptyMapIfTheResultHasANullValueAndClassIsAMap() throws Exception {
		Map map = ResultUtil.convert(new SimpleResult(null, null), Map.class);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty map if the result is null and class is a map", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnAnEmptyMapIfTheResultIsNullAndClassIsAMap() throws Exception {
		Map map = ResultUtil.convert(null, Map.class);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result has a null value and class is a set", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultHasANullValueAndClassIsASet() throws Exception {
		Set set = ResultUtil.convert(new SimpleResult(null, null), Set.class);
		Assert.assertNotNull(set);
		Assert.assertTrue(set.isEmpty());
	}
	
	/**
	 * @see {@link ResultUtil#convert(CalculationResult,Class<T>)}
	 */
	@SuppressWarnings("rawtypes")
	@Test
	@Verifies(value = "should return an empty collection if the result is null and class is a set", method = "convert(CalculationResult,Class<T>)")
	public void convert_shouldReturnAnEmptyCollectionIfTheResultIsNullAndClassIsASet() throws Exception {
		Set set = ResultUtil.convert(null, Set.class);
		Assert.assertNotNull(set);
		Assert.assertTrue(set.isEmpty());
	}
}
