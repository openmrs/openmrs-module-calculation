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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.CalculationRegistrationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Contains tests for {@link CalculationRegistrationValidator} class.
 */
public class CalculationRegistrationValidatorTest extends BaseModuleContextSensitiveTest {
	
	private CalculationRegistrationService calculationRegistrationService = null;
	
	private static final String TEST_DATA_PATH = "org/openmrs/calculation/include/";
	
	private static final String MODULE_TEST_DATA_XML = TEST_DATA_PATH + "moduleTestData.xml";
	
	@Before
	public void before() throws Exception {
		calculationRegistrationService = Context.getService(CalculationRegistrationService.class);
		executeDataSet(MODULE_TEST_DATA_XML);
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if token registration calculation name is empty or whitespace", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationCalculationNameIsEmptyOrWhitespace() throws Exception {
		CalculationRegistration calculationRegistration = new CalculationRegistration();
		calculationRegistration.setToken("test");
		calculationRegistration.setProviderClassName("test");
		calculationRegistration.setCalculationName(" ");
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("calculationName"));
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if token registration name is empty or whitespace", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationNameIsEmptyOrWhitespace() throws Exception {
		CalculationRegistration calculationRegistration = new CalculationRegistration();
		calculationRegistration.setToken(null);
		calculationRegistration.setProviderClassName("test");
		calculationRegistration.setCalculationName("A1");
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("token"));
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if token registration name is not unique amongst all tokens", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationNameIsNotUniqueAmongstAllTokens() throws Exception {
		CalculationRegistration calculationRegistration = new CalculationRegistration();
		calculationRegistration.setToken("age");
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("token"));
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if token registration provider class name is empty or whitespace", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationProviderClassNameIsEmptyOrWhitespace() throws Exception {
		CalculationRegistration calculationRegistration = new CalculationRegistration();
		calculationRegistration.setToken("test");
		calculationRegistration.setProviderClassName("");
		calculationRegistration.setCalculationName("A1");
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("providerClassName"));
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass existing token registration", method = "validate(Object,Errors)")
	public void validate_shouldPassExistingTokenRegistration() throws Exception {
		CalculationRegistration calculationRegistration = calculationRegistrationService.getCalculationRegistration(1);
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(false, errors.hasFieldErrors("token"));
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass valid token registration", method = "validate(Object,Errors)")
	public void validate_shouldPassValidTokenRegistration() throws Exception {
		CalculationRegistration calculationRegistration = new CalculationRegistration();
		calculationRegistration.setToken("ageCalc");
		calculationRegistration.setProviderClassName(ClasspathCalculationProvider.class.getName());
		calculationRegistration.setCalculationName(AgeCalculation.class.getName());
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link CalculationRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if token registration does not represent a valid calculation", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationDoesNotRepresentAValidCalculation() throws Exception {
		CalculationRegistration calculationRegistration = new CalculationRegistration();
		calculationRegistration.setToken("ageCalc");
		calculationRegistration.setProviderClassName(ClasspathCalculationProvider.class.getName());
		calculationRegistration.setCalculationName("org.invalid.calculationClassName");
		Errors errors = new BindException(calculationRegistration, "calculationRegistration");
		new CalculationRegistrationValidator().validate(calculationRegistration, errors);
		Assert.assertEquals(true, errors.hasErrors());
	}
}
