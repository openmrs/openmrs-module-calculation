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
package org.openmrs.calculation.api.db;

import java.util.List;

import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.api.PatientCalculationService;

/**
 * Database access methods for {@link PatientCalculationService}.
 */
public interface TokenRegistrationDAO {
	
	/**
	 * @see PatientCalculationService#getTokenRegistration(Integer)
	 */
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId);
	
	/**
	 * @see PatientCalculationService#getTokenRegistrationByUuid(String)
	 */
	public TokenRegistration getTokenRegistrationByUuid(String uuid);
	
	/**
	 * @see PatientCalculationService#getTokenRegistrationByName(String)
	 */
	public TokenRegistration getTokenRegistrationByName(String name);
	
	/**
	 * @see PatientCalculationService#getAllTokenRegistrations()
	 */
	public List<TokenRegistration> getAllTokenRegistrations();
	
	/**
	 * @see PatientCalculationService#findTokens(String)
	 */
	public List<TokenRegistration> findTokens(String partialName);
	
	/**
	 * @see PatientCalculationService#saveTokenRegistration(TokenRegistration)
	 */
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration);
	
	/**
	 * @see PatientCalculationService#purgeTokenRegistration(TokenRegistration)
	 */
	public void deleteTokenRegistration(TokenRegistration tokenRegistration);
	
}
