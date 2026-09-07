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

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.MostRecentObsCalculation;
import org.openmrs.test.jupiter.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * Contains test methods for {@link CalculationRegistrationService}
 */
public class CalculationRegistrationServiceTest extends BaseModuleContextSensitiveTest {
	
	private static final String TOKEN_UUID = "467dd0d8-5785-11e1-80a0-00248140a5eb";
	
	private static final String TEST_DATA_PATH = "org/openmrs/calculation/include/";
	
	private static final String MODULE_TEST_DATA_XML = TEST_DATA_PATH + "moduleTestData.xml";
	
	private CalculationRegistrationService service;
	
	@BeforeEach
	public void before() throws Exception {
		executeDataSet(MODULE_TEST_DATA_XML);
		service = Context.getService(CalculationRegistrationService.class);
	}
	
	/**
	 * @see {@link CalculationRegistrationService#getCalculationRegistration(Integer)}
	 */
	@Test
	@Verifies(value = "should return a token with a matching id", method = "getCalculationRegistration(Integer)")
	public void getCalculationRegistration_shouldReturnATokenWithAMatchingId() throws Exception {
		Assertions.assertEquals(TOKEN_UUID, service.getCalculationRegistration(1).getUuid());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#getCalculationRegistrationByUuid(Integer)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching uuid", method = "getCalculationRegistrationByUuid(Integer)")
	public void getCalculationRegistrationByUuid_shouldFetchATokenWithAMatchingUuid() throws Exception {
		Assertions.assertEquals("age", service.getCalculationRegistrationByUuid(TOKEN_UUID).getToken());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#getAllCalculationRegistrations()}
	 */
	@Test
	@Verifies(value = "should get all tokens in the database", method = "getAllCalculationRegistrations()")
	public void getAllCalculationRegistrations_shouldGetAllTokensInTheDatabase() throws Exception {
		Assertions.assertEquals(3, service.getAllCalculationRegistrations().size());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#saveCalculationRegistration(CalculationRegistration)}
	 */
	@Test
	@Verifies(value = "should save the specified calculationRegistration to the database", method = "saveCalculationRegistration(CalculationRegistration)")
	public void saveCalculationRegistration_shouldSaveTheSpecifiedCalculationRegistrationToTheDatabase() throws Exception {
		int originalTokenCount = service.getAllCalculationRegistrations().size();
		CalculationRegistration token = new CalculationRegistration();
		token.setToken("test token registration");
		token.setProviderClassName("org.openmrs.calculation.ClasspathCalculationProvider");
		token.setCalculationName("org.openmrs.calculation.AgeCalculation");
		service.saveCalculationRegistration(token);
		Assertions.assertNotNull(token.getId());
		Assertions.assertEquals(originalTokenCount + 1, service.getAllCalculationRegistrations().size());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#purgeCalculationRegistration(CalculationRegistration)}
	 */
	@Test
	@Verifies(value = "should purge the specified calculationRegistration from the database", method = "purgeCalculationRegistration(CalculationRegistration)")
	public void purgeCalculationRegistration_shouldPurgeTheSpecifiedCalculationRegistrationFromTheDatabase()
	    throws Exception {
		CalculationRegistration token = service.getCalculationRegistrationByUuid(TOKEN_UUID);
		Assertions.assertNotNull(service.getCalculationRegistrationByUuid(TOKEN_UUID));//control check
		service.purgeCalculationRegistration(token);
		Assertions.assertNull(service.getCalculationRegistrationByUuid(TOKEN_UUID));
	}
	
	/**
	 * @see {@link CalculationRegistrationService#saveCalculationRegistration(CalculationRegistration)}
	 */
	@Test
	@Verifies(value = "should update an existing token", method = "saveCalculationRegistration(CalculationRegistration)")
	public void saveCalculationRegistration_shouldUpdateAnExistingToken() throws Exception {
		String newTokenName = "new test token name";
		CalculationRegistration token = service.getCalculationRegistrationByUuid(TOKEN_UUID);
		Assertions.assertNotSame(newTokenName, token.getToken());
		
		token.setToken(newTokenName);
		service.saveCalculationRegistration(token);
		
		Assertions.assertSame(newTokenName, token.getToken());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#getCalculation(java.lang.String)}
	 */
	@Test
	@Verifies(value = "should return the calculation associated to the calculationRegistration with the given name", method = "getCalculation(java.lang.String)")
	public void getCalculation_shouldReturnTheCalculationAssociatedToTheCalculationRegistrationWithTheGivenName()
	    throws Exception {
		MostRecentObsCalculation calculation = service.getCalculation("mostRecentCD4", MostRecentObsCalculation.class);
		Assertions.assertNotNull(calculation);
		Assertions.assertEquals(5497, calculation.getWhichConcept().getConceptId().intValue());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#findCalculationRegistrations(String)}
	 */
	@Test
	@Verifies(value = "should get all calculationRegistrations with a matching name", method = "findCalculationRegistrations(String)")
	public void findCalculationRegistrations_shouldGetAllCalculationRegistrationsWithAMatchingName() throws Exception {
		Assertions.assertEquals(2, service.findCalculationRegistrations("mostRecent").size());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#getCalculationRegistrationByToken(String)}
	 */
	@Test
	@Verifies(value = "should fetch a token with a matching name", method = "getCalculationRegistrationByToken(String)")
	public void getCalculationRegistrationByToken_shouldFetchATokenWithAMatchingName() throws Exception {
		Assertions.assertEquals(TOKEN_UUID, service.getCalculationRegistrationByToken("age").getUuid());
	}
	
	/**
	 * @see {@link CalculationRegistrationService#saveCalculationRegistration(CalculationRegistration)}
	 */
	@Test
	@Verifies(value = "should update the cached token registration", method = "saveCalculationRegistration(CalculationRegistration)")
	public void saveCalculationRegistration_shouldUpdateTheCachedTokenRegistration() throws Exception {
		final String tokenName = "age";
		
		//This effectively loads the object into the session, so that on the line after it is 
		//what gets cached in our registration cache when fetching the calculation from the service
		//since we will still be in the same hibernate session
		CalculationRegistration originalTokenReg = service.getCalculationRegistrationByToken(tokenName);
		try {
			//This should lead to the token registration getting cached
			Assertions.assertNotNull(service.getCalculation(tokenName, AgeCalculation.class));
			
			//now we need to remove it from the session so that we effectively 
			//mimic our cache and DB being out of sync when changes are made
			Context.evictFromSession(originalTokenReg);
		}
		catch (APIException e) {
			Assertions.fail(e.getMessage());
		}
		
		CalculationRegistration newTokenRegInstance = service.getCalculationRegistrationByToken(tokenName);
		newTokenRegInstance.setCalculationName(MostRecentObsCalculation.class.getName());
		newTokenRegInstance.setConfiguration("5089");
		
		//this should synchronize our cache with the DB
		service.saveCalculationRegistration(newTokenRegInstance);

		//the cache is now in sync with the DB, so the token resolves to the newly saved calculation
		MostRecentObsCalculation updated = service.getCalculation(tokenName, MostRecentObsCalculation.class);
		Assertions.assertEquals(5089, updated.getWhichConcept().getConceptId().intValue());
	}
}
