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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.CalculationRegistrationSuggestion;
import org.openmrs.calculation.api.CalculationRegistrationService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * Controls the Calculation Auto Registration form
 */
@Controller
public class CalculationAutoRegistrationFormController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Default constructor
	 */
	public CalculationAutoRegistrationFormController() { }
	
	/**
	 */
	@RequestMapping(value = "/module/calculation/calculationAutoRegistration", method = RequestMethod.GET)
	public void showForm(Model model) {
		List<CalculationRegistrationSuggestion> l  = Context.getRegisteredComponents(CalculationRegistrationSuggestion.class);
		model.addAttribute("suggestions", l);
	}
	
	/**
	 * Attempt to auto-register any select groups of Calculations
	 */
	@RequestMapping(value = "/module/calculation/calculationAutoRegistration", method = RequestMethod.POST)
	public String handleSubmission(WebRequest request,
			@RequestParam(value="conflictMode", required=true) String conflictMode) {
		
		boolean overwrite = "overwrite".equals(conflictMode);
		CalculationRegistrationService service = Context.getService(CalculationRegistrationService.class);
		
		Map<String, CalculationRegistration> existingRegistrations = new HashMap<String, CalculationRegistration>();
		for (CalculationRegistration r : service.getAllCalculationRegistrations()) {
			existingRegistrations.put(r.getToken(), r);
		}
		
		Map<String, Integer> results = new HashMap<String, Integer>();
		results.put("created", 0);
		results.put("skipped", 0);
		results.put("replaced", 0);
		results.put("failed", 0);
		
		List<CalculationRegistrationSuggestion> suggestions = Context.getRegisteredComponents(CalculationRegistrationSuggestion.class);
		for (CalculationRegistrationSuggestion suggestion : suggestions) {
			if (suggestion.getSuggestions() != null) {
				boolean selected = "t".equals(request.getParameter(suggestion.getClass().getName() + "_" + suggestion.getName()));
				if (selected) {
					for (CalculationRegistration toRegister : suggestion.getSuggestions()) {
						try {
							CalculationRegistration r = existingRegistrations.get(toRegister.getToken());
							if (r != null) {
								if (overwrite) {
									r.setProviderClassName(toRegister.getProviderClassName());
									r.setCalculationName(toRegister.getCalculationName());
									r.setConfiguration(toRegister.getConfiguration());
									service.saveCalculationRegistration(r);
									results.put("replaced", results.get("replaced") + 1);
								}
								else {
									results.put("skipped", results.get("skipped") + 1);
								}
							}
							else {
								r = new CalculationRegistration();
								r.setToken(toRegister.getToken());
								r.setProviderClassName(toRegister.getProviderClassName());
								r.setCalculationName(toRegister.getCalculationName());
								r.setConfiguration(toRegister.getConfiguration());
								service.saveCalculationRegistration(r);
								results.put("created", results.get("created") + 1);
							}
						}
						catch (Exception e) {
							log.error("Failed to auto-register: " + toRegister, e);
							results.put("failed", results.get("failed") + 1);
						}
					}
				}
			}
		}
		
		StringBuilder msg = new StringBuilder();
		appendToMessage(msg, results, "created");
		appendToMessage(msg, results, "skipped");
		appendToMessage(msg, results, "replaced");
		appendToMessage(msg, results, "failed");
		request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, msg.toString(), WebRequest.SCOPE_SESSION);

		return "redirect:calculationRegistrations.list";
	}
	
	private void appendToMessage(StringBuilder sb, Map<String, Integer> results, String key) {
		Integer num = results.get(key);
		if (num != null && num > 0) {
			String code = "calculation.autoRegistration." + key;
			String s = Context.getMessageSourceService().getMessage(code, new Object[] {results.get(key)}, Context.getLocale());
			sb.append(s + "<br/>");
		}
	}
}
