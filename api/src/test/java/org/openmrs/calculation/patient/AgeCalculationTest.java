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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.api.CalculationContext;
import org.openmrs.calculation.api.CalculationService;
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.definition.ParameterDefinitionSet;
import org.openmrs.calculation.provider.CalculationProvider;
import org.openmrs.calculation.provider.TestCalculationProvider;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Contains behaviour tests for {@link AgeCalculation}
 * 
 * @see AgeCalculation
 */
public class AgeCalculationTest extends BaseModuleContextSensitiveTest {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private CalculationService service;
	
	@Before
	public void before() throws Exception {
		service = Context.getService(CalculationService.class);
	}
	
	/**
	 * 
	 */
	@Test
	public void shouldCalculateThePatientAge() {
		CalculationProvider p = new TestCalculationProvider();
		Calculation ageCalculation = p.getCalculationInstance("age", null);
		
		int patientId = 2;
		
		int expected = Context.getPatientService().getPatient(patientId).getAge();
		Assert.assertEquals(expected, service.evaluate(patientId, ageCalculation).asType(Integer.class).intValue());
		
	}
	
	/**
	 * @throws ParseException
	 */
	@Test
	public void shouldCalculateThePatientAgeBasedOnContextualInfo() throws ParseException {
		CalculationProvider p = new TestCalculationProvider();
		Calculation ageCalculation = p.getCalculationInstance("age", null);
		
		int patientId = 2;
		
		Date date = new SimpleDateFormat(DATE_FORMAT).parse("2000-01-01");
		CalculationContext ctxt = service.createCalculationContext();
		ctxt.setIndexDate(date);
		
		int expected = Context.getPatientService().getPatient(patientId).getAge(date);
		Assert.assertEquals(expected, service.evaluate(patientId, ageCalculation, ctxt).asType(Integer.class).intValue());
	}
	
	/**
	 * @throws ParseException
	 */
	@Test
	public void shouldCalculateThePatientAgeBasedOnContextualInfoAndParameterValues() throws ParseException {
		CalculationProvider p = new TestCalculationProvider();
		Calculation ageCalculation = p.getCalculationInstance("age", null);
		ParameterDefinitionSet pds = ageCalculation.getParameterDefinitionsSet();
		ParameterDefinition pd = pds.getParameterByKey("units");
		Assert.assertNotNull(pd);
		Assert.assertEquals(pd.getName(), "Units Of Age");
		
		int patientId = 2;
		
		Date date = new SimpleDateFormat(DATE_FORMAT).parse("2000-01-01");
		CalculationContext ctxt = service.createCalculationContext();
		ctxt.setIndexDate(date);
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("units", "months");
		
		Assert.assertEquals(296, service.evaluate(patientId, ageCalculation, values, ctxt).asType(Integer.class).intValue());
	}
}
