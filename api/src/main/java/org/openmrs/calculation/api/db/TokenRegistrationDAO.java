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
import org.openmrs.calculation.api.TokenRegistrationService;

/**
 * Database access methods for {@link TokenRegistrationService}.
 */
public interface TokenRegistrationDAO {
	
	/**
	 * @see TokenRegistrationService#getTokenRegistration(Integer)
	 */
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId);
	
	/**
	 * @see TokenRegistrationService#getTokenRegistrationByUuid(String)
	 */
	public TokenRegistration getTokenRegistrationByUuid(String uuid);
	
	/**
	 * @see TokenRegistrationService#getTokenRegistrationByName(String)
	 */
	public TokenRegistration getTokenRegistrationByName(String name);
	
	/**
	 * @see TokenRegistrationService#getAllTokenRegistrations()
	 */
	public List<TokenRegistration> getAllTokenRegistrations();
	
	/**
	 * @see TokenRegistrationService#findTokenRegistrations(String)
	 */
	public List<TokenRegistration> findTokenRegistrations(String partialName);
	
	/**
	 * @see TokenRegistrationService#saveTokenRegistration(TokenRegistration)
	 */
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration);
	
	/**
	 * @see TokenRegistrationService#purgeTokenRegistration(TokenRegistration)
	 */
	public void deleteTokenRegistration(TokenRegistration tokenRegistration);
	
}
