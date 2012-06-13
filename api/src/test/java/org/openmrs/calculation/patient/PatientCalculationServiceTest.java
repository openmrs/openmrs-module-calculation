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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Cohort;
import org.openmrs.ConceptNumeric;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.CalculationConstants;
import org.openmrs.calculation.ClasspathCalculationProvider;
import org.openmrs.calculation.CountingCalculation;
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.InvalidParameterValueException;
import org.openmrs.calculation.MissingParameterException;
import org.openmrs.calculation.parameter.ParameterDefinition;
import org.openmrs.calculation.parameter.SimpleParameterDefinition;
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
	 *      PatientCalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext
	 *      )}
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
	 *      PatientCalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext
	 *      )}
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
	 *      PatientCalculationService#evaluate(Cohort,Calculation,Map<String,Object>,CalculationContext
	 *      )}
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
		return (PatientCalculation) p.getCalculation(AgeCalculation.class.getName(), null);
	}
	
	/**
	 * @see {@link PatientCalculationService#evaluate(Cohort,PatientCalculation,Map<String,Object>,
	 *      PatientCalculationContext)}
	 */
	@Test(expected = InvalidParameterValueException.class)
	@Verifies(value = "should fail if the a parameter value doesnt match the allowed datatype", method = "evaluate(Cohort,PatientCalculation,Map<String,Object>,PatientCalculationContext)")
	public void evaluate_shouldFailIfTheAParameterValueDoesntMatchTheAllowedDatatype() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		ParameterDefinition param = new SimpleParameterDefinition("testParam", "java.lang.Integer", null, false);
		ageCalculation.getParameterDefinitionSet().add(param);
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(param.getKey(), "2s");
		service.evaluate(2, ageCalculation, values, null);
	}
	
	/**
	 * @see {@link PatientCalculationService#evaluate(Cohort,PatientCalculation,Map<String,Object>,
	 *      PatientCalculationContext)}
	 */
	@Test
	@Verifies(value = "should pass if the passed in datatype cannot be loaded", method = "evaluate(Cohort,PatientCalculation,Map<String,Object>,PatientCalculationContext)")
	public void evaluate_shouldPassIfThePassedInDatatypeCannotBeLoaded() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		ParameterDefinition conceptDefinition = new SimpleParameterDefinition("conceptParam", "numericConcepts", null, false);
		ageCalculation.getParameterDefinitionSet().add(conceptDefinition);
		
		Map<String, Object> values = new HashMap<String, Object>();
		List<ConceptNumeric> numericConcepts = new ArrayList<ConceptNumeric>();
		values.put(conceptDefinition.getKey(), numericConcepts);
		service.evaluate(2, ageCalculation, values, null);
	}
	
	/**
	 * This test is aimed to make sure our batching mechanism is semantically correct
	 * 
	 * @see {@link PatientCalculationService#evaluate(Cohort,PatientCalculation,Map<String,Object>,
	 *      PatientCalculationContext)}
	 */
	@Test
	@Verifies(value = "should return the expected result size for cohort with a large number of patient", method = "evaluate(Cohort,PatientCalculation,Map<String,Object>,PatientCalculationContext)")
	public void evaluate_shouldReturnTheExpectedResultSizeForCohortWithALargeNumberOfPatient() throws Exception {
		final int patientCount = 1001012;
		Assert.assertTrue(patientCount > CalculationConstants.EVALUATION_BATCH_SIZE);
		List<Integer> cohort = new ArrayList<Integer>();
		//create a large cohort for testing purposes
		for (int i = 1; i <= patientCount; ++i) {
			cohort.add(i);
		}
		
		Assert.assertEquals(patientCount, service.evaluate(cohort, new CountingCalculation()).size());
		//the cohort members should remain unchanged
		Assert.assertEquals(patientCount, cohort.size());
	}
}
