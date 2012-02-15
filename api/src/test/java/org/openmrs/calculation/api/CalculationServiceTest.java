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
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * Contains test methods for {@link CalculationService}
 */
public class CalculationServiceTest extends BaseModuleContextSensitiveTest {
	
	private CalculationService service;
	
	private static final String TOKEN_UUID = "467dd0d8-5785-11e1-80a0-00248140a5eb";
	
	@Before
	public void before() {
		service = Context.getService(CalculationService.class);
	}
	
	/**
	 * @see {@link CalculationService#getTokenRegistration(Integer)}
	 */
	@Test
	@Verifies(value = "should return a token with a matching id", method = "getTokenRegistration(Integer)")
	public void getTokenRegistration_shouldReturnATokenWithAMatchingId() throws Exception {
		Assert.assertEquals(TOKEN_UUID, service.getTokenRegistration(1).getUuid());
	}
	
	/**
	 * @see {@link CalculationService#getTokenRegistrationByName(String)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching name", method = "getTokenRegistrationByName(String)")
	public void getTokenRegistrationByName_shouldFetchATokenWithAMatchingName() throws Exception {
		Assert.assertEquals(TOKEN_UUID, service.getTokenRegistrationByName("gender").getUuid());
	}
	
	/**
	 * @see {@link CalculationService#getTokenRegistrationByUuid(Integer)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching uuid", method = "getTokenRegistrationByUuid(Integer)")
	public void getTokenRegistrationByUuid_shouldFetchATokenWithAMatchingUuid() throws Exception {
		Assert.assertEquals("gender", service.getTokenRegistrationByUuid(TOKEN_UUID).getName());
	}
	
	/**
	 * @see {@link CalculationService#getAllTokenRegistrations()}
	 */
	@Test
	@Verifies(value = "should get all tokens in the database", method = "getAllTokenRegistrations()")
	public void getAllTokenRegistrations_shouldGetAllTokensInTheDatabase() throws Exception {
		Assert.assertEquals(6, service.getAllTokenRegistrations().size());
	}
	
	/**
	 * @see {@link CalculationService#saveTokenRegistration(TokenRegistration)}
	 */
	@Test
	@Verifies(value = "should save the specified tokenRegistration to the database", method = "saveTokenRegistration(TokenRegistration)")
	public void saveTokenRegistration_shouldSaveTheSpecifiedTokenRegistrationToTheDatabase() throws Exception {
		int originalTokenCount = service.getAllTokenRegistrations().size();
		TokenRegistration token = new TokenRegistration();
		token.setName("test token registration");
		token.setProviderClassName("test.provider.Class");
		service.saveTokenRegistration(token);
		Assert.assertNotNull(token.getTokenRegistrationId());
		Assert.assertEquals(originalTokenCount + 1, service.getAllTokenRegistrations().size());
	}
	
	/**
	 * @see {@link CalculationService#purgeTokenRegistration(TokenRegistration)}
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
	 * @see {@link CalculationService#findTokens(String)}
	 */
	@Test
	@Verifies(value = "should get all tokenRegistrations with a matching name", method = "findTokens(String)")
	public void findTokens_shouldGetAllTokenRegistrationsWithAMatchingName() throws Exception {
		Assert.assertEquals(5, service.findTokens("name").size());
	}
}
