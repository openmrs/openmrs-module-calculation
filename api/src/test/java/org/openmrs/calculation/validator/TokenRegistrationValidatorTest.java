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
package org.openmrs.calculation.validator;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.api.TokenRegistrationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Contains tests for {@link TokenRegistrationValidator} class.
 */
public class TokenRegistrationValidatorTest extends BaseModuleContextSensitiveTest {
	
	private TokenRegistrationService tokenRegistrationService = null;
	
	private static final String TEST_DATA_PATH = "org/openmrs/calculation/include/";
	
	private static final String MODULE_TEST_DATA_XML = TEST_DATA_PATH + "moduleTestData.xml";
	
	@Before
	public void before() throws Exception {
		tokenRegistrationService = Context.getService(TokenRegistrationService.class);
		executeDataSet(MODULE_TEST_DATA_XML);
	}
	
	/**
	 * @see {@link TokenRegistrationValidator#validate(Object,Errors)}
	 * 
	 */
	@Test
	@Verifies(value = "pass valid token registration", method = "validate(Object,Errors)")
	public void validate_shouldPassValidTokenRegistration() throws Exception {
		TokenRegistration tokenRegistration = new TokenRegistration();
		tokenRegistration.setName("test");
		tokenRegistration.setProviderClassName("test");
		tokenRegistration.setCalculationName("A1");
		Errors errors = new BindException(tokenRegistration, "tokenRegistration");
		new TokenRegistrationValidator().validate(tokenRegistration, errors);
		Assert.assertEquals(Boolean.FALSE, errors.hasErrors());
	}
	
	/**
	 * @see {@link TokenRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "fail if token registration name is empty or whitespace", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationNameIsEmptyOrWhitespace() throws Exception {
		TokenRegistration tokenRegistration = new TokenRegistration();
		tokenRegistration.setName(null);
		tokenRegistration.setProviderClassName("test");
		tokenRegistration.setCalculationName("A1");
		Errors errors = new BindException(tokenRegistration, "tokenRegistration");
		new TokenRegistrationValidator().validate(tokenRegistration, errors);
		Assert.assertEquals(Boolean.TRUE, errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see {@link TokenRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "fail if token registration provider class name is empty or whitespace", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationProviderClassNameIsEmptyOrWhitespace() throws Exception {
		TokenRegistration tokenRegistration = new TokenRegistration();
		tokenRegistration.setName("test");
		tokenRegistration.setProviderClassName("");
		tokenRegistration.setCalculationName("A1");
		Errors errors = new BindException(tokenRegistration, "tokenRegistration");
		new TokenRegistrationValidator().validate(tokenRegistration, errors);
		Assert.assertEquals(Boolean.TRUE, errors.hasFieldErrors("providerClassName"));
	}
	
	/**
	 * @see {@link TokenRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "fail if token registration calculation name is empty or whitespace", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationCalculationNameIsEmptyOrWhitespace() throws Exception {
		TokenRegistration tokenRegistration = new TokenRegistration();
		tokenRegistration.setName("test");
		tokenRegistration.setProviderClassName("test");
		tokenRegistration.setCalculationName(" ");
		Errors errors = new BindException(tokenRegistration, "tokenRegistration");
		new TokenRegistrationValidator().validate(tokenRegistration, errors);
		Assert.assertEquals(Boolean.TRUE, errors.hasFieldErrors("calculationName"));
	}
	
	/**
	 * @see {@link TokenRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "fail if token registration name is not unique amongst all tokens", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTokenRegistrationNameIsNotUniqueAmongstAllTokens() throws Exception {
		TokenRegistration tokenRegistration = new TokenRegistration();
		tokenRegistration.setName("age");
		Errors errors = new BindException(tokenRegistration, "tokenRegistration");
		new TokenRegistrationValidator().validate(tokenRegistration, errors);
		Assert.assertEquals(Boolean.TRUE, errors.hasFieldErrors("name"));
	}
	
	/**
	 * @see {@link TokenRegistrationValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "pass existing token registration", method = "validate(Object,Errors)")
	public void validate_shouldPassExistingTokenRegistration() throws Exception {
		TokenRegistration tokenRegistration = tokenRegistrationService.getTokenRegistration(1);
		Errors errors = new BindException(tokenRegistration, "tokenRegistration");
		new TokenRegistrationValidator().validate(tokenRegistration, errors);
		Assert.assertEquals(Boolean.FALSE, errors.hasFieldErrors("name"));
	}
}