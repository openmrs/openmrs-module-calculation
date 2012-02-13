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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.BaseModuleActivator;

/**
 * The activator for the module
 */
public class CalculationActivator extends BaseModuleActivator {
	
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * @see org.openmrs.module.BaseModuleActivator#started()
	 */
	@Override
	public void started() {
		if (log.isDebugEnabled())
			log.debug("Started Calculation Module...");
	}
	
	/**
	 * @see org.openmrs.module.BaseModuleActivator#stopped()
	 */
	@Override
	public void stopped() {
		if (log.isDebugEnabled())
			log.debug("Stopped Calculation Module...");
	}
	
	/**
	 * @see org.openmrs.module.BaseModuleActivator#willStart()
	 */
	@Override
	public void willStart() {
		if (log.isDebugEnabled())
			log.debug("Starting Calculation Module...");
	}
	
	/**
	 * @see org.openmrs.module.BaseModuleActivator#willStop()
	 */
	@Override
	public void willStop() {
		if (log.isDebugEnabled())
			log.debug("Stopping Calculation Module...");
	}
}
