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
package org.openmrs.calculation.patient;

import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.CalculationUtil;
import org.openmrs.calculation.ConversionException;
import org.openmrs.calculation.InvalidParameterValueException;
import org.openmrs.calculation.MissingParameterException;
import org.openmrs.calculation.parameter.ParameterDefinition;
import org.openmrs.calculation.parameter.ParameterDefinitionSet;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.util.HandlerUtil;

/**
 * It is a default implementation of {@link PatientCalculationService}.
 */
public class PatientCalculationServiceImpl extends BaseOpenmrsService implements PatientCalculationService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#createCalculationContext()
	 */
	@Override
	public PatientCalculationContext createCalculationContext() {
		return new SimplePatientCalculationContext();
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.patient.PatientCalculation)
	 */
	@Override
	public CalculationResult evaluate(Integer patientId, PatientCalculation calculation) throws APIException {
		return evaluate(patientId, calculation, null);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.patient.PatientCalculation,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResult evaluate(Integer patientId, PatientCalculation calculation, PatientCalculationContext context)
	    throws APIException {
		return evaluate(patientId, calculation, null, context);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(java.lang.Integer,
	 *      org.openmrs.calculation.patient.PatientCalculation, java.util.Map,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResult evaluate(Integer patientId, PatientCalculation calculation,
	                                  Map<String, Object> parameterValues, PatientCalculationContext context)
	    throws APIException {
		Cohort cohort = new Cohort(patientId);
		cohort.addMember(patientId);
		CohortResult cr = evaluate(cohort, calculation, parameterValues, context);
		if (cr.size() == 0)
			return null;
		
		return cr.get(patientId);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.patient.PatientCalculation)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation) throws APIException {
		return evaluate(cohort, calculation, null);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.patient.PatientCalculation,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, PatientCalculationContext context)
	    throws APIException {
		return evaluate(cohort, calculation, null, context);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(org.openmrs.Cohort,
	 *      org.openmrs.calculation.patient.PatientCalculation, java.util.Map,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CohortResult evaluate(Cohort cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) throws APIException {
		if (calculation == null)
			throw new IllegalArgumentException("Calculation cannot be null");
		
		ParameterDefinitionSet defs = calculation.getParameterDefinitionSet();
		//Check for missing values for required parameters
		if (defs != null) {
			for (ParameterDefinition parameter : defs) {
				String datatype = parameter.getDatatype();
				Object value = null;
				if (parameterValues != null)
					value = parameterValues.get(parameter.getKey());
				
				if (parameter.isRequired()) {
					boolean foundMissingValue = false;
					if (value == null) {
						foundMissingValue = true;
					} else {
						//the shouldn't be blank if the datatype is String or a primitive wrapper class
						if ((CalculationUtil.isPrimitiveWrapperClassName(datatype) || String.class.getName()
						        .equals(datatype)) && StringUtils.isBlank(value.toString())) {
							foundMissingValue = true;
						}
					}
					
					if (foundMissingValue)
						throw new MissingParameterException(parameter);
				}
				
				if (StringUtils.isNotBlank(datatype) && value != null) {
					try {
						CalculationUtil.cast(value, Context.loadClass(datatype));
					}
					catch (ConversionException e) {
						throw new InvalidParameterValueException(parameter, value);
					}
					catch (ClassNotFoundException e) {
						// ignore, we've done our best, may be they know how to find their class
					}
				}
			}
		}
		
		if (context == null)
			context = createCalculationContext();
		
		CohortResult cr = HandlerUtil.getPreferredHandler(PatientCalculationEvaluator.class, calculation.getClass())
		        .evaluate(cohort, calculation, parameterValues, context);
		
		if (cr == null)
			cr = new CohortResult();
		
		return cr;
	}
	
	/**
	 * Base class for {@link PatientCalculationContext}s
	 */
	public class SimplePatientCalculationContext implements PatientCalculationContext {
		
		private Date now = null;
		
		private Map<String, Object> contextCache = new WeakHashMap<String, Object>();
		
		public SimplePatientCalculationContext() {
			this.now = new Date();
		}
		
		/**
		 * @see org.openmrs.calculation.patient.PatientCalculationContext#getNow()
		 */
		@Override
		public Date getNow() {
			return now;
		}
		
		/**
		 * @see org.openmrs.calculation.patient.PatientCalculationContext#setNow(java.util.Date)
		 */
		@Override
		public void setNow(Date date) {
			now = date;
		}
		
		/**
		 * @see org.openmrs.calculation.patient.PatientCalculationContext#addToCache(java.lang.String,
		 *      java.lang.Object)
		 */
		@Override
		public void addToCache(String key, Object value) {
			contextCache.put(key, value);
		}
		
		/**
		 * @see org.openmrs.calculation.patient.PatientCalculationContext#getFromCache(java.lang.String)
		 */
		@Override
		public Object getFromCache(String key) {
			return contextCache.get(key);
		}
		
		/**
		 * @see org.openmrs.calculation.patient.PatientCalculationContext#removeFromCache(java.lang.String)
		 */
		@Override
		public void removeFromCache(String key) {
			contextCache.remove(key);
		}
	}
}
