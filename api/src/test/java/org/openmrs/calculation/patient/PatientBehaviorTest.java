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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.BehaviorTest;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.definition.ParameterDefinitionSet;
import org.openmrs.calculation.provider.CalculationProvider;
import org.openmrs.calculation.provider.TestCalculationProvider;

/**
 * Contains behaviour tests for patient calculations
 */
public class PatientBehaviorTest extends BehaviorTest {
	
	private static final String TEST_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 
	 */
	@Test
	public void shouldCalculateThePatientAge() throws Exception {
		CalculationProvider p = new TestCalculationProvider();
		Calculation ageCalculation = p.getCalculationInstance("age", null);
		
		int patientId = 2;
		
		int expected = Context.getPatientService().getPatient(patientId).getAge();
		Assert.assertNotNull(getService().evaluate(patientId, ageCalculation));
		Assert.assertEquals(expected, getService().evaluate(patientId, ageCalculation).asType(Integer.class).intValue());
		
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void shouldCalculateThePatientAgeBasedOnContextualInfo() throws Exception {
		CalculationProvider p = new TestCalculationProvider();
		Calculation ageCalculation = p.getCalculationInstance("age", null);
		
		int patientId = 2;
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		CalculationContext ctxt = getService().createCalculationContext();
		ctxt.setIndexDate(date);
		
		int expected = Context.getPatientService().getPatient(patientId).getAge(date);
		Assert.assertEquals(expected, getService().evaluate(patientId, ageCalculation, ctxt).asType(Integer.class)
		        .intValue());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void shouldCalculateThePatientAgeBasedOnContextualInfoAndParameterValues() throws Exception {
		CalculationProvider p = new TestCalculationProvider();
		Calculation ageCalculation = p.getCalculationInstance("age", null);
		ParameterDefinitionSet pds = ageCalculation.getParameterDefinitionsSet();
		ParameterDefinition pd = pds.getParameterByKey("units");
		Assert.assertNotNull(pd);
		Assert.assertEquals(pd.getName(), "Units Of Age");
		
		int patientId = 2;
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		CalculationContext ctxt = getService().createCalculationContext();
		ctxt.setIndexDate(date);
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(pd.getKey(), "months");
		
		Assert.assertEquals(296, getService().evaluate(patientId, ageCalculation, values, ctxt).asType(Integer.class)
		        .intValue());
	}
}
