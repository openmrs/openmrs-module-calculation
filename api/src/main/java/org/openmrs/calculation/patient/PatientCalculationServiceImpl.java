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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.calculation.CalculationConstants;
import org.openmrs.calculation.CalculationUtil;
import org.openmrs.calculation.ConversionException;
import org.openmrs.calculation.InvalidParameterValueException;
import org.openmrs.calculation.MissingParameterException;
import org.openmrs.calculation.parameter.ParameterDefinition;
import org.openmrs.calculation.parameter.ParameterDefinitionSet;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;

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
		CalculationResultMap cr = evaluate(Arrays.asList(patientId), calculation, parameterValues, context);
		if (cr.size() == 0)
			return null;
		
		return cr.get(patientId);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(java.util.Collection, org.openmrs.calculation.patient.PatientCalculation)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, PatientCalculation calculation) throws APIException {
		return evaluate(cohort, calculation, null);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(java.util.Collection, org.openmrs.calculation.patient.PatientCalculation, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, PatientCalculation calculation, PatientCalculationContext context)
	    throws APIException {
		return evaluate(cohort, calculation, null, context);
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculationService#evaluate(java.util.Collection, org.openmrs.calculation.patient.PatientCalculation, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, PatientCalculation calculation, Map<String, Object> parameterValues,
	                             PatientCalculationContext context) throws APIException {
		if (calculation == null || cohort == null)
			throw new IllegalArgumentException("Calculation and cohort are both required");
		if (cohort.isEmpty())
			return new CalculationResultMap();
		
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
		
		CalculationResultMap cr = null;
		if (cohort.size() <= CalculationConstants.EVALUATION_BATCH_SIZE) {
			cr = calculation.evaluate(cohort, parameterValues, context);
		} else {
			cr = new CalculationResultMap();

			// we take cohort members one by one until we run out or hit the batch size, and evaluate the calculation on those batches
			// We could achieve some negligible performance speedup by special-casing the case where cohort instanceof List and using subList, but this doesn't seem worth it.
			Iterator<Integer> iter = cohort.iterator();
			List<Integer> batch = new ArrayList<Integer>();
			while (iter.hasNext()) {
				batch.add(iter.next());
				if (batch.size() == CalculationConstants.EVALUATION_BATCH_SIZE || !iter.hasNext()) {
					cr.putAll(calculation.evaluate(batch, parameterValues, context));
					batch = new ArrayList<Integer>();
				}
			}
		}
		
		if (cr == null)
			cr = new CalculationResultMap();
		
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
