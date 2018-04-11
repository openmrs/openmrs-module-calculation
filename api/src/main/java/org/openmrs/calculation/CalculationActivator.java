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

import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.CalculationRegistrationService;
import org.openmrs.module.BaseModuleActivator;

/**
 * The activator for the module
 */
public class CalculationActivator extends BaseModuleActivator {
	
	protected Log log = LogFactory.getLog(getClass());

	@Override
	public void started() {
		registerCalculations(
				Context.getRegisteredComponents(ImplementationConfiguredCalculationProvider.class).get(0),
				Context.getService(CalculationRegistrationService.class));
		if (log.isDebugEnabled())
			log.debug("Started Calculation Module...");

	}

	void registerCalculations(ImplementationConfiguredCalculationProvider provider,
	                                  CalculationRegistrationService service) {
		provider.loadCalculationsFromDirectory();
		for (Map.Entry<String, Calculation> e : provider.getCalculations().entrySet()) {
			CalculationRegistration registration = service.getCalculationRegistrationByToken(e.getKey());
			if (registration == null) {
				registration = new CalculationRegistration();
			}
			registration.setToken(e.getKey());
			registration.setProviderClassName(ImplementationConfiguredCalculationProvider.class.getName());
			registration.setCalculationName(e.getKey());
			try {
				service.saveCalculationRegistration(registration);
			}
			catch (InvalidCalculationException ex) {
				log.error("Error registering implementation-configured calculation for " + e.getKey(), ex);
			}
		}

		// remove any previously-registered calculations that no longer exist (e.g. if a file was deleted from the
		// implementation config
		Set<String> tokensForExistingCalculations = provider.getCalculations().keySet();
		for (CalculationRegistration registered : service.getCalculationRegistrationsByProviderClassname(
				ImplementationConfiguredCalculationProvider.class.getName())) {
			if (!tokensForExistingCalculations.contains(registered.getToken())) {
				service.purgeCalculationRegistration(registered);
			}
		}

	}

	@Override
	public void stopped() {
		if (log.isDebugEnabled())
			log.debug("Stopped Calculation Module...");
	}
}