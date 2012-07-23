package org.openmrs.calculation.result;


import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ListResultTest {
	
	/**
	 * @see ListResult#getValues()
	 * @verifies return list of underlying values
	 */
	@Test
	public void getValues_shouldReturnListOfUnderlyingValues() throws Exception {
		Date date = new Date();
		
		ListResult lr = new ListResult();
		lr.add(new SimpleResult("A string", null));
		lr.add(new SimpleResult(Double.valueOf(2), null));
		lr.add(new SimpleResult(date, null));
		
		List<Object> values = lr.getValues();
		Assert.assertEquals(lr.size(), values.size());
		Assert.assertEquals("A string", values.get(0));
		Assert.assertEquals(Double.valueOf(2), values.get(1));
		Assert.assertEquals(date, values.get(2));
	}
}