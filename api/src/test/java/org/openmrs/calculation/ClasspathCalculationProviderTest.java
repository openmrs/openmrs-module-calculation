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
package org.openmrs.calculation;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.test.annotation.ExpectedException;

/**
 * Tests the ClasspathCalculationProvider
 */
public class ClasspathCalculationProviderTest extends BaseModuleContextSensitiveTest {
	
	/**
	 * @see ClasspathCalculationProvider#getCalculation(String,String)
	 * @verifies retrieve a configured configurable calculation with a valid configuration string
	 */
	@Test
	public void getCalculation_shouldRetrieveAConfiguredConfigurableCalculationWithAValidConfigurationString()
	    throws Exception {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		Calculation c = p.getCalculation(MostRecentObsCalculation.class.getName(), "5089");
		Assert.assertNotNull(c);
	}
	
	/**
	 * @see ClasspathCalculationProvider#getCalculation(String,String)
	 * @verifies retrieve a non configurable calculation with a null configuration string
	 */
	@Test
	public void getCalculation_shouldRetrieveANonConfigurableCalculationWithANullConfigurationString() throws Exception {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		Calculation c = p.getCalculation(AgeCalculation.class.getName(), null);
		Assert.assertNotNull(c);
	}
	
	/**
	 * @see ClasspathCalculationProvider#getCalculation(String,String)
	 * @verifies throw an exception if a configurable calculation is passed an illegal configuration
	 */
	@Test
	@ExpectedException(InvalidCalculationException.class)
	public void getCalculation_shouldThrowAnExceptionIfAConfigurableCalculationIsPassedAnIllegalConfiguration()
	    throws Exception {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		p.getCalculation(MostRecentObsCalculation.class.getName(), "5o89");
	}
	
	/**
	 * @see ClasspathCalculationProvider#getCalculation(String,String)
	 * @verifies throw an exception if a non configurable calculation is passed a configuration
	 *           string
	 */
	@Test
	@ExpectedException(InvalidCalculationException.class)
	public void getCalculation_shouldThrowAnExceptionIfANonConfigurableCalculationIsPassedAConfigurationString()
	    throws Exception {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		p.getCalculation(AgeCalculation.class.getName(), "something");
	}
	
	/**
	 * @see {@link ClasspathCalculationProvider#getCalculation(String,String)}
	 */
	@Test
	@Verifies(value = "should always return a different instance of a calculation", method = "getCalculation(String,String)")
	public void getCalculation_shouldAlwaysReturnADifferentInstanceOfACalculation() throws Exception {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		String calculationName = InnerCalculation.class.getName();
		Assert.assertTrue(p.getCalculation(calculationName, null) != p.getCalculation(calculationName, null));
	}
}
