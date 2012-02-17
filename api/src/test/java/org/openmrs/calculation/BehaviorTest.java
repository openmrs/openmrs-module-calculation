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

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.CalculationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Superclass for Behavior tests
 */
public class BehaviorTest extends BaseModuleContextSensitiveTest {
	
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:MM:SS";
	
	private CalculationService service;
	
	@Before
	public void before() throws Exception {
		service = Context.getService(CalculationService.class);
	}
	
	protected CalculationService getService() {
		return service;
	}
}
