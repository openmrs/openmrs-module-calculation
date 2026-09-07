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
package org.openmrs.calculation.web.controller;

import org.junit.jupiter.api.Test;
import org.openmrs.web.test.jupiter.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculationWebControllerTest extends BaseModuleWebContextSensitiveTest {

	@Autowired
	@Qualifier("requestMappingHandlerMapping")
	private RequestMappingHandlerMapping handlerMapping;

	@Test
	public void uiUrls_shouldResolveToTheirControllers() throws Exception {
		assertHandler("GET", "/module/calculation/calculationRegistrations.list",
		    CalculationRegistrationController.class, "listCalculationRegistrations");
		assertHandler("GET", "/module/calculation/calculationRegistration.form",
		    CalculationRegistrationFormController.class, "showCalculationRegistration");
		assertHandler("POST", "/module/calculation/calculationRegistration.form",
		    CalculationRegistrationFormController.class, "saveCalculationRegistration");
		assertHandler("GET", "/module/calculation/deleteCalculationRegistration.form",
		    CalculationRegistrationFormController.class, "purgeCalculationRegistration");
		assertHandler("GET", "/module/calculation/patientCalculationTest.form",
		    CalculationRegistrationController.class, "patientCalculationTest");
		assertHandler("GET", "/module/calculation/calculationAutoRegistration.form",
		    CalculationAutoRegistrationFormController.class, "showForm");
		assertHandler("POST", "/module/calculation/calculationAutoRegistration.form",
		    CalculationAutoRegistrationFormController.class, "handleSubmission");
	}

	private void assertHandler(String httpMethod, String url, Class<?> controllerType, String methodName) throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest(httpMethod, url);
		HandlerExecutionChain executionChain = handlerMapping.getHandler(request);

		assertNotNull(executionChain, () -> httpMethod + " " + url + " should resolve to a handler");
		HandlerMethod handlerMethod = assertInstanceOf(HandlerMethod.class, executionChain.getHandler());
		assertEquals(controllerType, handlerMethod.getBeanType());
		assertEquals(methodName, handlerMethod.getMethod().getName());
	}
}
