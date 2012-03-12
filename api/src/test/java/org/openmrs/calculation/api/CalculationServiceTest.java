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
package org.openmrs.calculation.api;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.MissingParameterException;
import org.openmrs.calculation.PatientCalculation;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.provider.DemoCalculationProvider;
import org.openmrs.calculation.util.CalculationUtil;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * Contains test methods for {@link PatientCalculationService}
 */
public class CalculationServiceTest extends BaseModuleContextSensitiveTest {
	
	private PatientCalculationService service;
	
	private static final String TEST_DATA_PATH = "org/openmrs/calculation/include/";
	
	private static final String MODULE_TEST_DATA_XML = TEST_DATA_PATH + "moduleTestData.xml";
	
	private static final String TOKEN_UUID = "467dd0d8-5785-11e1-80a0-00248140a5eb";
	
	@Before
	public void before() throws Exception {
		executeDataSet(MODULE_TEST_DATA_XML);
		service = Context.getService(PatientCalculationService.class);
	}
	
	/**
	 * @see {@link PatientCalculationService#getTokenRegistration(Integer)}
	 */
	@Test
	@Verifies(value = "should return a token with a matching id", method = "getTokenRegistration(Integer)")
	public void getTokenRegistration_shouldReturnATokenWithAMatchingId() throws Exception {
		//Assert.assertEquals(TOKEN_UUID, service.getTokenRegistration(1).getUuid());
	}
	
	/**
	 * @see {@link PatientCalculationService#getTokenRegistrationByName(String)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching name", method = "getTokenRegistrationByName(String)")
	public void getTokenRegistrationByName_shouldFetchATokenWithAMatchingName() throws Exception {
		//Assert.assertEquals(TOKEN_UUID, service.getTokenRegistrationByName("gender").getUuid());
	}
	
	/**
	 * @see {@link PatientCalculationService#getTokenRegistrationByUuid(Integer)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching uuid", method = "getTokenRegistrationByUuid(Integer)")
	public void getTokenRegistrationByUuid_shouldFetchATokenWithAMatchingUuid() throws Exception {
		//Assert.assertEquals("gender", service.getTokenRegistrationByUuid(TOKEN_UUID).getName());
	}
	
	/**
	 * @see {@link PatientCalculationService#getAllTokenRegistrations()}
	 */
	@Test
	@Verifies(value = "should get all tokens in the database", method = "getAllTokenRegistrations()")
	public void getAllTokenRegistrations_shouldGetAllTokensInTheDatabase() throws Exception {
		//Assert.assertEquals(6, service.getAllTokenRegistrations().size());
	}
	
	/**
	 * @see {@link PatientCalculationService#saveTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should save the specified tokenRegistration to the database", method = "saveTokenRegistration(TokenRegistration)")
	public void saveTokenRegistration_shouldSaveTheSpecifiedTokenRegistrationToTheDatabase() throws Exception {
		/*int originalTokenCount = service.getAllTokenRegistrations().size();
		TokenRegistration token = new TokenRegistration();
		token.setName("test token registration");
		token.setProviderClassName("test.provider.class");
		service.saveTokenRegistration(token);
		Assert.assertNotNull(token.getTokenRegistrationId());
		Assert.assertEquals(originalTokenCount + 1, service.getAllTokenRegistrations().size());*/
	}
	
	/**
	 * @see {@link PatientCalculationService#purgeTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should purge the specified tokenRegistration from the database", method = "purgeTokenRegistration(TokenRegistration)")
	public void purgeTokenRegistration_shouldPurgeTheSpecifiedTokenRegistrationFromTheDatabase() throws Exception {
		/*TokenRegistration token = service.getTokenRegistrationByUuid(TOKEN_UUID);
		Assert.assertNotNull(service.getTokenRegistrationByUuid(TOKEN_UUID));//control check
		service.purgeTokenRegistration(token);
		Assert.assertNull(service.getTokenRegistrationByUuid(TOKEN_UUID));*/
	}
	
	/**
	 * @see {@link PatientCalculationService#findTokens(String)}
	 */
	@Test
	@Verifies(value = "should get all tokenRegistrations with a matching name", method = "findTokens(String)")
	public void findTokens_shouldGetAllTokenRegistrationsWithAMatchingName() throws Exception {
		//Assert.assertEquals(5, service.findTokens("name").size());
	}
	
	/**
	 * @see {@link PatientCalculationService#saveTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should update an existing token", method = "saveTokenRegistration(TokenRegistration)")
	public void saveTokenRegistration_shouldUpdateAnExistingToken() throws Exception {
		/*String newTokenName = "new test token name";
		TokenRegistration token = service.getTokenRegistrationByUuid(TOKEN_UUID);
		Assert.assertNull(token.getDateChanged());
		Assert.assertNull(token.getChangedBy());
		Assert.assertNotSame(newTokenName, token.getName());
		
		token.setName(newTokenName);
		service.saveTokenRegistration(token);
		
		TokenRegistration editedToken = service.getTokenRegistrationByUuid(TOKEN_UUID);
		Assert.assertNotNull(editedToken.getDateChanged());
		Assert.assertNotNull(editedToken.getChangedBy());
		Assert.assertSame(newTokenName, token.getName());*/
	}
	
	/**
	 * @see {@link
	 *      CalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext )}
	 */
	@Test(expected = MissingParameterException.class)
	@Verifies(value = "should fail if any required parameter is not set", method = "evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext)")
	public void evaluate_shouldFailIfAnyRequiredParameterIsNotSet() throws Exception {
		PatientCalculation ageCalculation = new DemoCalculationProvider().getCalculation("age", null);
		ParameterDefinition requiredDefinition = CalculationUtil.createParameterDefinition("testParam", "my data type",
		    null, true);
		ageCalculation.getParameterDefinitionSet().add(requiredDefinition);
		service.evaluate(2, ageCalculation);
	}
	
	/**
	 * @see {@link
	 *      CalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext )}
	 */
	@Test(expected = MissingParameterException.class)
	@Verifies(value = "should fail for a blank value for a required parameter if datatype is a primitive wrapper", method = "evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext)")
	public void evaluate_shouldFailForABlankValueForARequiredParameterIfDatatypeIsAPrimitiveWrapper() throws Exception {
		PatientCalculation ageCalculation = new DemoCalculationProvider().getCalculation("age", null);
		ParameterDefinition requiredDefinition = CalculationUtil.createParameterDefinition("testParam", "java.lang.Integer",
		    null, true);
		ageCalculation.getParameterDefinitionSet().add(requiredDefinition);
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(requiredDefinition.getKey(), "");
		service.evaluate(2, ageCalculation, values, null);
	}
	
	/**
	 * @see {@link
	 *      CalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext )}
	 */
	@Test(expected = MissingParameterException.class)
	@Verifies(value = "should fail for a blank value for a required parameter if datatype is a String", method = "evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext)")
	public void evaluate_shouldFailForABlankValueForARequiredParameterIfDatatypeIsAString() throws Exception {
		PatientCalculation ageCalculation = new DemoCalculationProvider().getCalculation("age", null);
		ParameterDefinition requiredDefinition = CalculationUtil.createParameterDefinition("testParam", "java.lang.String",
		    null, true);
		ageCalculation.getParameterDefinitionSet().add(requiredDefinition);
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(requiredDefinition.getKey(), "");
		service.evaluate(2, ageCalculation, values, null);
	}
}
