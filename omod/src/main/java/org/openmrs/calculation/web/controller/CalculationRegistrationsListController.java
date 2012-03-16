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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.CalculationRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controls the page that lists all existing Token Registrations
 */
@Controller
public class CalculationRegistrationsListController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Shows the page to list token registrations
	 */
	@RequestMapping(value = "/module/calculation/calculationRegistrations")
	public void showForm(Model model) {
		CalculationRegistrationService calculationRegistrationService = Context.getService(CalculationRegistrationService.class);
		model.addAttribute("calculationRegistrations", calculationRegistrationService.getAllCalculationRegistrations());
	}
}
