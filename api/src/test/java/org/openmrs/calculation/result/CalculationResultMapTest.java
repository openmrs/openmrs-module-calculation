package org.openmrs.calculation.result;


import org.junit.Assert;
import org.junit.Test;
import org.openmrs.PatientProgram;

public class CalculationResultMapTest {
	
	/**
	 * @see CalculationResultMap#getAsBoolean(Integer)
	 * @verifies return false if key is not mapped
	 */
	@Test
	public void getAsBoolean_shouldReturnFalseIfKeyIsNotMapped() throws Exception {
		CalculationResultMap map = new CalculationResultMap();
		Assert.assertFalse(map.getAsBoolean(999));
	}
	
	/**
	 * @see CalculationResultMap#getAsBoolean(Integer)
	 * @verifies return false if key is mapped to a falsey value
	 */
	@Test
	public void getAsBoolean_shouldReturnFalseIfKeyIsMappedToAFalseyValue() throws Exception {
		Object[] falsey = new Object[] { "", 0, 0d, Boolean.FALSE, null };
		CalculationResultMap map = new CalculationResultMap();
		for (Object f : falsey) {
			map.put(1, new SimpleResult(f, null));
			Assert.assertFalse(map.getAsBoolean(1));
		}
	}
	
	/**
	 * @see CalculationResultMap#getAsBoolean(Integer)
	 * @verifies return true if key is mapped to a truthy value
	 */
	@Test
	public void getAsBoolean_shouldReturnTrueIfKeyIsMappedToATruthyValue() throws Exception {
		Object[] truthy = new Object[] { "not empty", -1, 2d, Boolean.TRUE, new PatientProgram() };
		CalculationResultMap map = new CalculationResultMap();
		for (Object t : truthy) {
			map.put(1, new SimpleResult(t, null));
			Assert.assertTrue(map.getAsBoolean(1));
		}
	}
	
	/**
	 * @see CalculationResultMap#isEmpty(Integer)
	 * @verifies return true if key is not mapped
	 */
	@Test
	public void isEmpty_shouldReturnTrueIfKeyIsNotMapped() throws Exception {
		CalculationResultMap map = new CalculationResultMap();
		Assert.assertTrue(map.isEmpty(999));
	}
	
	/**
	 * @see CalculationResultMap#isEmpty(Integer)
	 * @verifies return true if key is mapped to an empty value
	 */
	@Test
	public void isEmpty_shouldReturnTrueIfKeyIsMappedToAnEmptyValue() throws Exception {
		CalculationResult[] empty = new CalculationResult[] { new ListResult(), new SimpleResult(null, null), null };
		CalculationResultMap map = new CalculationResultMap();
		for (CalculationResult e : empty) {
			map.put(1, e);
			Assert.assertTrue(map.isEmpty(1));
		}
	}
	
	/**
	 * @see CalculationResultMap#isEmpty(Integer)
	 * @verifies return false if key is mapped to a non-empty value
	 */
	@Test
	public void isEmpty_shouldReturnFalseIfKeyIsMappedToANonemptyValue() throws Exception {
		ListResult lr = new ListResult();
		lr.add(new SimpleResult("not empty", null));
		CalculationResult[] notEmpty = new CalculationResult[] { lr, new SimpleResult("not empty", null) };
		CalculationResultMap map = new CalculationResultMap();
		for (CalculationResult ne : notEmpty) {
			map.put(1, ne);
			Assert.assertFalse(map.isEmpty(1));
		}
	}
}