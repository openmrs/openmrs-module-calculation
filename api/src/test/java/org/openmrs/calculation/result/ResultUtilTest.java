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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.openmrs.Concept;
import org.openmrs.ConceptDatatype;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
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

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for null
     */
    @Test
    public void isFalse_shouldReturnTrueForNull() throws Exception {
	    Assert.assertTrue(ResultUtil.isFalse(null));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for an empty collection
     */
    @Test
    public void isFalse_shouldReturnTrueForAnEmptyCollection() throws Exception {
    	Assert.assertTrue(ResultUtil.isFalse(new ArrayList<String>()));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a non-empty collection
     */
    @Test
    public void isFalse_shouldReturnFalseForANonemptyCollection() throws Exception {
    	Assert.assertFalse(ResultUtil.isFalse(Collections.singleton("Not Empty")));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for an empty map
     */
    @Test
    public void isFalse_shouldReturnTrueForAnEmptyMap() throws Exception {
    	Assert.assertTrue(ResultUtil.isFalse(new HashMap<String, Object>()));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a non-empty map
     */
    @Test
    public void isFalse_shouldReturnFalseForANonemptyMap() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("not", "empty");
    	Assert.assertFalse(ResultUtil.isFalse(map));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for an empty string
     */
    @Test
    public void isFalse_shouldReturnTrueForAnEmptyString() throws Exception {
    	Assert.assertTrue(ResultUtil.isFalse(""));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a non-empty string
     */
    @Test
    public void isFalse_shouldReturnFalseForANonemptyString() throws Exception {
    	Assert.assertFalse(ResultUtil.isFalse("Not empty"));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for the number 0
     */
    @Test
    public void isFalse_shouldReturnTrueForTheNumber0() throws Exception {
    	Assert.assertTrue(ResultUtil.isFalse(0));
    	Assert.assertTrue(ResultUtil.isFalse(0d));
    	Assert.assertTrue(ResultUtil.isFalse(BigDecimal.ZERO));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a non-zero number
     */
    @Test
    public void isFalse_shouldReturnFalseForANonzeroNumber() throws Exception {
    	Assert.assertFalse(ResultUtil.isFalse(-1));
    	Assert.assertFalse(ResultUtil.isFalse(10d));
    	Assert.assertFalse(ResultUtil.isFalse(BigDecimal.ONE));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for a numeric Obs whose value is 0
     */
    @Test
    public void isFalse_shouldReturnTrueForANumericObsWhoseValueIs0() throws Exception {
    	Obs obs = new Obs();
    	obs.setConcept(mockConcept(ConceptDatatype.NUMERIC_UUID));
    	obs.setValueNumeric(0d);
    	Assert.assertTrue(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a numeric Obs whose value is not 0
     */
    @Test
    public void isFalse_shouldReturnFalseForANumericObsWhoseValueIsNot0() throws Exception {
    	Obs obs = new Obs();
    	obs.setConcept(mockConcept(ConceptDatatype.NUMERIC_UUID));
    	obs.setValueNumeric(-1d);
    	Assert.assertFalse(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for a text Obs whose value is the empty string
     */
    @Test
    public void isFalse_shouldReturnTrueForATextObsWhoseValueIsTheEmptyString() throws Exception {
    	Obs obs = new Obs();
    	obs.setConcept(mockConcept(ConceptDatatype.TEXT_UUID));
    	obs.setValueText("");
    	Assert.assertTrue(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a text Obs whose value is not the empty string
     */
    @Test
    public void isFalse_shouldReturnFalseForATextObsWhoseValueIsNotTheEmptyString() throws Exception {
    	Obs obs = new Obs();
    	obs.setConcept(mockConcept(ConceptDatatype.TEXT_UUID));
    	obs.setValueText("not empty");
    	Assert.assertFalse(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for a coded Obs whose value is the GlobalProperty concept.false
     */
    @Test
    public void isFalse_shouldReturnTrueForACodedObsWhoseValueIsTheGlobalPropertyConceptfalse() throws Exception {
    	// this test should be different for 1.7+ 
    	Obs obs = Mockito.mock(Obs.class);
    	Mockito.when(obs.getValueAsBoolean()).thenReturn(Boolean.FALSE);
    	Mockito.when(obs.getConcept()).thenReturn(mockConcept(ConceptDatatype.CODED_UUID));
    	Assert.assertTrue(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a coded Obs whose value is not the GlobalProperty concept.false
     */
    @Test
    public void isFalse_shouldReturnFalseForACodedObsWhoseValueIsNotTheGlobalPropertyConceptfalse() throws Exception {
    	Obs obs = Mockito.mock(Obs.class);
    	Mockito.when(obs.getValueCoded()).thenReturn(new Concept());
    	// in 1.7 the test should do this instead on mocking getValueCoded
    	// Mockito.when(obs.getValueAsBoolean()).thenReturn(null);
    	Mockito.when(obs.getConcept()).thenReturn(mockConcept(ConceptDatatype.CODED_UUID));
    	Assert.assertFalse(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for a boolean Obs whose value is false
     */
    @Test
    public void isFalse_shouldReturnTrueForABooleanObsWhoseValueIsFalse() throws Exception {
    	Obs obs = Mockito.mock(Obs.class);
    	Mockito.when(obs.getValueAsBoolean()).thenReturn(Boolean.FALSE);
    	Mockito.when(obs.getConcept()).thenReturn(mockConcept(ConceptDatatype.BOOLEAN_UUID));
    	Assert.assertTrue(ResultUtil.isFalse(obs));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a boolean Obs whose value is true
     */
    @Test
    public void isFalse_shouldReturnFalseForABooleanObsWhoseValueIsTrue() throws Exception {
    	Obs obs = Mockito.mock(Obs.class);
    	Mockito.when(obs.getValueAsBoolean()).thenReturn(Boolean.TRUE);
    	Mockito.when(obs.getConcept()).thenReturn(mockConcept(ConceptDatatype.BOOLEAN_UUID));
    	Assert.assertFalse(ResultUtil.isFalse(obs));
    }
    
	/**
     * Creates a mock concept with a mock concept datatype with the given uuid
     * 
     * @param numericUuid
     * @return
     */
    private Concept mockConcept(String conceptDatatypeUuid) {
    	ConceptDatatype dt = new ConceptDatatype();
    	dt.setUuid(conceptDatatypeUuid);
    	Concept mock = new Concept();
    	mock.setDatatype(dt);
    	return mock;
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for an empty ListResult
     */
    @Test
    public void isFalse_shouldReturnTrueForAnEmptyListResult() throws Exception {
    	Assert.assertTrue(ResultUtil.isFalse(new ListResult()));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for non-empty ListResult
     */
    @Test
    public void isFalse_shouldReturnFalseForNonemptyListResult() throws Exception {
    	ListResult r = new ListResult();
    	r.add(new SimpleResult("not empty", null));
    	Assert.assertFalse(ResultUtil.isFalse(r));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return true for an empty SimpleResult
     */
    @Test
    public void isFalse_shouldReturnTrueForAnEmptySimpleResult() throws Exception {
    	// it's bad practice to have a SimpleResult whose value is null, but we should still handle the case 
    	Assert.assertTrue(ResultUtil.isFalse(new SimpleResult(null, null)));
    }

	/**
     * @see ResultUtil#isFalse(Object)
     * @verifies return false for a non-empty SimpleResult
     */
    @Test
    public void isFalse_shouldReturnFalseForANonemptySimpleResult() throws Exception {
    	Assert.assertFalse(ResultUtil.isFalse(new SimpleResult(new PatientProgram(), null)));
    }

	/**
     * @see ResultUtil#isEmpty(Object)
     * @verifies return true for null
     */
    @Test
    public void isEmpty_shouldReturnTrueForNull() throws Exception {
	    Assert.assertTrue(ResultUtil.isEmpty(null));
    }

	/**
     * @see ResultUtil#isEmpty(Object)
     * @verifies return true for empty collections
     */
    @Test
    public void isEmpty_shouldReturnTrueForEmptyCollections() throws Exception {
    	Assert.assertTrue(ResultUtil.isEmpty(Collections.EMPTY_LIST));
    }

	/**
     * @see ResultUtil#isEmpty(Object)
     * @verifies return true for empty maps
     */
    @Test
    public void isEmpty_shouldReturnTrueForEmptyMaps() throws Exception {
    	Assert.assertTrue(ResultUtil.isEmpty(Collections.EMPTY_MAP));
    }

	/**
     * @see ResultUtil#isEmpty(Object)
     * @verifies return false for non-empty collections
     */
    @Test
    public void isEmpty_shouldReturnFalseForNonemptyCollections() throws Exception {
    	Assert.assertFalse(ResultUtil.isEmpty(Collections.singleton("not empty")));
    }

	/**
     * @see ResultUtil#isEmpty(Object)
     * @verifies return false for non-empty maps
     */
    @Test
    public void isEmpty_shouldReturnFalseForNonemptyMaps() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("not", "empty");
    	Assert.assertFalse(ResultUtil.isEmpty(map));
    }

	/**
     * @see ResultUtil#isEmpty(Object)
     * @verifies return false for plain objects
     */
    @Test
    public void isEmpty_shouldReturnFalseForPlainObjects() throws Exception {
    	Assert.assertFalse(ResultUtil.isEmpty(-1));
    }

	/**
     * @see ResultUtil#convert(CalculationResult,Class)
     * @verifies return true when converting an arbitrary object to Boolean
     */
    @Test
    public void convert_shouldReturnTrueWhenConvertingAnArbitraryObjectToBoolean() throws Exception {
	    Assert.assertTrue(ResultUtil.convert(new SimpleResult(new PatientProgram(), null), Boolean.class));
    }

	/**
     * @see ResultUtil#convert(CalculationResult,Class)
     * @verifies return false when converting falsey values to Boolean
     */
    @Test
    public void convert_shouldReturnFalseWhenConvertingFalseyValuesToBoolean() throws Exception {
    	Object[] falsey = new Object[] { "", 0, 0d, Boolean.FALSE, null };
    	for (Object f : falsey) {
    		Assert.assertFalse(ResultUtil.convert(new SimpleResult(f, null), Boolean.class));
    	}
    }
    
}
