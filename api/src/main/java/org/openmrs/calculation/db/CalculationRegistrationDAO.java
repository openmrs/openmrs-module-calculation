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
package org.openmrs.calculation.db;

import java.util.List;

import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.api.CalculationRegistrationService;

/**
 * Database access methods for {@link CalculationRegistrationService}.
 */
public interface CalculationRegistrationDAO {
	
	/**
	 * @see CalculationRegistrationService#getCalculationRegistration(Integer)
	 */
	public CalculationRegistration getCalculationRegistration(Integer calculationRegistrationId);
	
	/**
	 * @see CalculationRegistrationService#getCalculationRegistrationByUuid(String)
	 */
	public CalculationRegistration getCalculationRegistrationByUuid(String uuid);
	
	/**
	 * @see CalculationRegistrationService#getCalculationRegistrationByToken(String)
	 */
	public CalculationRegistration getCalculationRegistrationByToken(String token);
	
	/**
	 * @see CalculationRegistrationService#getAllCalculationRegistrations()
	 */
	public List<CalculationRegistration> getAllCalculationRegistrations();
	
	/**
	 * @see CalculationRegistrationService#findCalculationRegistrations(String)
	 */
	public List<CalculationRegistration> findCalculationRegistrations(String partialToken);
	
	/**
	 * @see CalculationRegistrationService#saveCalculationRegistration(CalculationRegistration)
	 */
	public CalculationRegistration saveCalculationRegistration(CalculationRegistration calculationRegistration);
	
	/**
	 * @see CalculationRegistrationService#purgeCalculationRegistration(CalculationRegistration)
	 */
	public void deleteCalculationRegistration(CalculationRegistration calculationRegistration);
	
}
