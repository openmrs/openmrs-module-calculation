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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.DummyCalculation;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * Contains test methods for {@link TokenRegistrationService}
 */
public class TokenRegistrationServiceTest extends BaseModuleContextSensitiveTest {
	
	private static final String TOKEN_UUID = "467dd0d8-5785-11e1-80a0-00248140a5eb";
	
	private static final String TEST_DATA_PATH = "org/openmrs/calculation/include/";
	
	private static final String MODULE_TEST_DATA_XML = TEST_DATA_PATH + "moduleTestData.xml";
	
	private TokenRegistrationService service;
	
	@Before
	public void before() throws Exception {
		executeDataSet(MODULE_TEST_DATA_XML);
		service = Context.getService(TokenRegistrationService.class);
	}
	
	/**
	 * @see {@link TokenRegistrationService#getTokenRegistration(Integer)}
	 */
	@Test
	@Verifies(value = "should return a token with a matching id", method = "getTokenRegistration(Integer)")
	public void getTokenRegistration_shouldReturnATokenWithAMatchingId() throws Exception {
		Assert.assertEquals(TOKEN_UUID, service.getTokenRegistration(1).getUuid());
	}
	
	/**
	 * @see {@link TokenRegistrationService#getTokenRegistrationByName(String)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching name", method = "getTokenRegistrationByName(String)")
	public void getTokenRegistrationByName_shouldFetchATokenWithAMatchingName() throws Exception {
		Assert.assertEquals(TOKEN_UUID, service.getTokenRegistrationByName("age").getUuid());
	}
	
	/**
	 * @see {@link TokenRegistrationService#getTokenRegistrationByUuid(Integer)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching uuid", method = "getTokenRegistrationByUuid(Integer)")
	public void getTokenRegistrationByUuid_shouldFetchATokenWithAMatchingUuid() throws Exception {
		Assert.assertEquals("age", service.getTokenRegistrationByUuid(TOKEN_UUID).getName());
	}
	
	/**
	 * @see {@link TokenRegistrationService#getAllTokenRegistrations()}
	 */
	@Test
	@Verifies(value = "should get all tokens in the database", method = "getAllTokenRegistrations()")
	public void getAllTokenRegistrations_shouldGetAllTokensInTheDatabase() throws Exception {
		Assert.assertEquals(4, service.getAllTokenRegistrations().size());
	}
	
	/**
	 * @see {@link TokenRegistrationService#saveTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should save the specified tokenRegistration to the database", method = "saveTokenRegistration(TokenRegistration)")
	public void saveTokenRegistration_shouldSaveTheSpecifiedTokenRegistrationToTheDatabase() throws Exception {
		int originalTokenCount = service.getAllTokenRegistrations().size();
		TokenRegistration token = new TokenRegistration();
		token.setName("test token registration");
		token.setProviderClassName("test.provider.class");
		token.setCalculationName("testing");
		service.saveTokenRegistration(token);
		Assert.assertNotNull(token.getTokenRegistrationId());
		Assert.assertEquals(originalTokenCount + 1, service.getAllTokenRegistrations().size());
	}
	
	/**
	 * @see {@link TokenRegistrationService#purgeTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should purge the specified tokenRegistration from the database", method = "purgeTokenRegistration(TokenRegistration)")
	public void purgeTokenRegistration_shouldPurgeTheSpecifiedTokenRegistrationFromTheDatabase() throws Exception {
		TokenRegistration token = service.getTokenRegistrationByUuid(TOKEN_UUID);
		Assert.assertNotNull(service.getTokenRegistrationByUuid(TOKEN_UUID));//control check
		service.purgeTokenRegistration(token);
		Assert.assertNull(service.getTokenRegistrationByUuid(TOKEN_UUID));
	}
	
	/**
	 * @see {@link TokenRegistrationService#findTokenRegistrations(String)}
	 */
	@Test
	@Verifies(value = "should get all tokenRegistrations with a matching name", method = "findTokenRegistrations(String)")
	public void findTokens_shouldGetAllTokenRegistrationsWithAMatchingName() throws Exception {
		Assert.assertEquals(2, service.findTokenRegistrations("mostRecent").size());
	}
	
	/**
	 * @see {@link TokenRegistrationService#saveTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should update an existing token", method = "saveTokenRegistration(TokenRegistration)")
	public void saveTokenRegistration_shouldUpdateAnExistingToken() throws Exception {
		String newTokenName = "new test token name";
		TokenRegistration token = service.getTokenRegistrationByUuid(TOKEN_UUID);
		Assert.assertNotSame(newTokenName, token.getName());
		
		token.setName(newTokenName);
		service.saveTokenRegistration(token);
		
		Assert.assertSame(newTokenName, token.getName());
	}
	
	/**
	 * @see {@link TokenRegistrationService#getCalculation(java.lang.String)}
	 */
	@Test
	@Verifies(value = "should return the calculation associated to the tokenRegistration with the given name", method = "getCalculation(java.lang.String)")
	public void getCalculation_shouldReturnTheCalculationAssociatedToTheTokenRegistrationWithTheGivenName() throws Exception {
		TokenRegistration tokenRegistration = service.getTokenRegistrationByName("dummy");
		DummyCalculation calculation = service.getCalculation(tokenRegistration.getName(), DummyCalculation.class);
		Assert.assertNotNull(calculation);
		Assert.assertEquals(tokenRegistration.getConfiguration(), calculation.getConfiguration());
	}
}
