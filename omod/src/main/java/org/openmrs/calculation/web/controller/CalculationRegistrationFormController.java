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

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.CalculationProvider;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.CalculationRegistrationValidator;
import org.openmrs.calculation.api.CalculationRegistrationService;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * Controls the Calculation Registration form
 */
@Controller
public class CalculationRegistrationFormController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	private CalculationRegistrationValidator calculationRegistrationValidator;
	
	/**
	 * Default constructor
	 */
	public CalculationRegistrationFormController() {
	}
	
	/**
	 * Constructor that takes in a validator
	 * 
	 * @param calculationRegistrationValidator
	 */
	@Autowired
	public CalculationRegistrationFormController(CalculationRegistrationValidator calculationRegistrationValidator) {
		this.calculationRegistrationValidator = calculationRegistrationValidator;
	}
	
	/**
	 * Gets registered calculation providers as model attribute
	 * 
	 * @return not-null collection of calculation providers
	 */
	@ModelAttribute("providers")
	public Collection<CalculationProvider> getCalculationProviders() {
		return Context.getRegisteredComponents(CalculationProvider.class);
	}
	
	/**
	 * Prepares form backing object to be used within jsp-page for editing/creating tokens
	 * 
	 * @param id (optional) the id of token to be used as backing object (if not specified, new
	 *            {@link CalculationRegistration} instance will be created
	 * @return backing object for associated view form
	 */
	@ModelAttribute("calculationRegistration")
	public CalculationRegistration formBackingObject(@RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			CalculationRegistrationService calculationRegistrationService = Context
			        .getService(CalculationRegistrationService.class);
			return calculationRegistrationService.getCalculationRegistration(id);
		} else {
			return new CalculationRegistration();
		}
	}
	
	/**
	 */
	@RequestMapping(value = "/module/calculation/calculationRegistration", method = RequestMethod.GET)
	public void showCalculationRegistration() {
		// Intentionally left blank. All work is done in the formBackingObject() method 
	}
	
	/**
	 * Save changes which were made within token registration. If any validation error has been
	 * occurred it will populate binding result with corresponding error messages and return back to
	 * edit page.
	 */
	@RequestMapping(value = "/module/calculation/calculationRegistration", method = RequestMethod.POST)
	public String saveCalculationRegistration(@ModelAttribute("calculationRegistration") CalculationRegistration calculationRegistration,
	                                          BindingResult result, WebRequest request) {
		
		// Validate CalculationRegistration
		calculationRegistrationValidator.validate(calculationRegistration, result);
		if (result.hasErrors()) {
			return null;
		}
		
		CalculationRegistrationService calculationRegistrationService = Context
		        .getService(CalculationRegistrationService.class);
		try {
			calculationRegistration = calculationRegistrationService.saveCalculationRegistration(calculationRegistration);
		}
		catch (Exception e) {
			log.error("Unable to save token registration, because of error:", e);
			request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "calculation.CalculationRegistration.errorSaving",
			    WebRequest.SCOPE_SESSION);
			return null;
		}
		
		updateSessionMessage(request, "calculation.CalculationRegistration.saved", calculationRegistration);
		return "redirect:calculationRegistrations.list";
	}
	
	/**
	 * Purges given token registration and redirects to the list of existing ones
	 */
	@RequestMapping(value = "/module/calculation/deleteCalculationRegistration")
	public String purgeCalculationRegistration(@ModelAttribute(value = "calculationRegistration") CalculationRegistration calculationRegistration) {
		Context.getService(CalculationRegistrationService.class).purgeCalculationRegistration(calculationRegistration);
		return "redirect:calculationRegistrations.list";
	}
	
	private void updateSessionMessage(WebRequest request, String code, Object... args) {
		String msg = Context.getMessageSourceService().getMessage(code, args, Context.getLocale());
		request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, msg, WebRequest.SCOPE_SESSION);
	}
}
