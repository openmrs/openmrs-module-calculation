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
package org.openmrs.calculation.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.TokenRegistration;
import org.openmrs.calculation.api.TokenRegistrationService;
import org.openmrs.calculation.api.db.TokenRegistrationDAO;

/**
 * It is a default implementation of {@link TokenRegistrationService}.
 */
public class TokenRegistrationServiceImpl extends BaseOpenmrsService implements TokenRegistrationService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private TokenRegistrationDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(TokenRegistrationDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#getTokenRegistration(java.lang.Integer)
	 */
	@Override
	public TokenRegistration getTokenRegistration(Integer tokenRegistrationId) throws APIException {
		return dao.getTokenRegistration(tokenRegistrationId);
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#getTokenRegistrationByUuid(String)
	 */
	@Override
	public TokenRegistration getTokenRegistrationByUuid(String uuid) throws APIException {
		return dao.getTokenRegistrationByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#getTokenRegistrationByName(java.lang.String)
	 */
	@Override
	public TokenRegistration getTokenRegistrationByName(String name) throws APIException {
		return dao.getTokenRegistrationByName(name);
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#getAllTokenRegistrations()
	 */
	@Override
	public List<TokenRegistration> getAllTokenRegistrations() throws APIException {
		return dao.getAllTokenRegistrations();
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#findTokenRegistrations(java.lang.String)
	 */
	@Override
	public List<TokenRegistration> findTokenRegistrations(String partialName) throws APIException {
		return dao.findTokenRegistrations(partialName);
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#saveTokenRegistration(org.openmrs.calculation.TokenRegistration)
	 */
	@Override
	public TokenRegistration saveTokenRegistration(TokenRegistration tokenRegistration) throws APIException {
		return dao.saveTokenRegistration(tokenRegistration);
	}
	
	/**
	 * @see org.openmrs.calculation.api.TokenRegistrationService#purgeTokenRegistration(TokenRegistration)
	 */
	@Override
	public void purgeTokenRegistration(TokenRegistration tokenRegistration) throws APIException {
		dao.deleteTokenRegistration(tokenRegistration);
	}
}
