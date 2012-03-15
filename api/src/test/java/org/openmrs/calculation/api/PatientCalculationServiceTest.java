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
package org.openmrs.calculation.api;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.MissingParameterException;
import org.openmrs.calculation.api.patient.PatientCalculationService;
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.definition.SimpleParameterDefinition;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.provider.ClasspathCalculationProvider;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * Contains test methods for {@link PatientCalculationService}
 */
public class PatientCalculationServiceTest extends BaseModuleContextSensitiveTest {
	
	private PatientCalculationService service;
	
	private static final String TEST_DATA_PATH = "org/openmrs/calculation/include/";
	
	private static final String MODULE_TEST_DATA_XML = TEST_DATA_PATH + "moduleTestData.xml";
	
	@Before
	public void before() throws Exception {
		executeDataSet(MODULE_TEST_DATA_XML);
		service = Context.getService(PatientCalculationService.class);
	}
	
	/**
	 * @see {@link
	 *      PatientCalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext )}
	 */
	@Test(expected = MissingParameterException.class)
	@Verifies(value = "should fail if any required parameter is not set", method = "evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext)")
	public void evaluate_shouldFailIfAnyRequiredParameterIsNotSet() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		ParameterDefinition requiredDefinition = new SimpleParameterDefinition("testParam", "my data type", null, true);
		ageCalculation.getParameterDefinitionSet().add(requiredDefinition);
		service.evaluate(2, ageCalculation);
	}
	
	/**
	 * @see {@link
	 *      PatientCalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext )}
	 */
	@Test(expected = MissingParameterException.class)
	@Verifies(value = "should fail for a blank value for a required parameter if datatype is a primitive wrapper", method = "evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext)")
	public void evaluate_shouldFailForABlankValueForARequiredParameterIfDatatypeIsAPrimitiveWrapper() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		ParameterDefinition requiredDefinition = new SimpleParameterDefinition("testParam", "java.lang.Integer", null, true);
		ageCalculation.getParameterDefinitionSet().add(requiredDefinition);
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(requiredDefinition.getKey(), "");
		service.evaluate(2, ageCalculation, values, null);
	}
	
	/**
	 * @see {@link
	 *      PatientCalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext )}
	 */
	@Test(expected = MissingParameterException.class)
	@Verifies(value = "should fail for a blank value for a required parameter if datatype is a String", method = "evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext)")
	public void evaluate_shouldFailForABlankValueForARequiredParameterIfDatatypeIsAString() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		ParameterDefinition requiredDefinition = new SimpleParameterDefinition("testParam", "java.lang.String", null, true);
		ageCalculation.getParameterDefinitionSet().add(requiredDefinition);
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(requiredDefinition.getKey(), "");
		service.evaluate(2, ageCalculation, values, null);
	}
	
	/**
	 * @return an Example calculation instance
	 */
	private PatientCalculation getAgeCalculation() throws InvalidCalculationException {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		return (PatientCalculation)p.getCalculation(AgeCalculation.class.getName(), null);
	}
}
