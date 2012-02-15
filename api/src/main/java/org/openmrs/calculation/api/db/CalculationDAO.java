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
import org.openmrs.calculation.api.CalculationService;

/**
 * Database access methods for {@link CalculationService}.
 */
public interface CalculationDAO {
	
	/**
	 * @see CalculationService#getTokenRegistration(Integer)
	 */
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId);
	
	/**
	 * @see CalculationService#getTokenRegistrationByUuid(String)
	 */
	public TokenRegistration getTokenRegistrationByUuid(String uuid);
	
	/**
	 * @see CalculationService#getTokenRegistrationByName(String)
	 */
	public TokenRegistration getTokenRegistrationByName(String name);
	
	/**
	 * @see CalculationService#getAllTokenRegistrations()
	 */
	public List<TokenRegistration> getAllTokenRegistrations();
	
	/**
	 * @see CalculationService#findTokens(String)
	 */
	public List<TokenRegistration> findTokens(String partialName);
	
	/**
	 * @see CalculationService#saveTokenRegistration(TokenRegistration)
	 */
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration);
	
	/**
	 * @see CalculationService#purgeTokenRegistration(TokenRegistration)
	 */
	public void deleteTokenRegistration(TokenRegistration tokenRegistration);
	
}
