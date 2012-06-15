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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Cohort;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.AgeCalculation;
import org.openmrs.calculation.ClasspathCalculationProvider;
import org.openmrs.calculation.InvalidCalculationException;
import org.openmrs.calculation.MostRecentEncounterCalculation;
import org.openmrs.calculation.MostRecentObsCalculation;
import org.openmrs.calculation.OuterCalculation;
import org.openmrs.calculation.RecentEncounterCalculation;
import org.openmrs.calculation.parameter.ParameterDefinition;
import org.openmrs.calculation.parameter.ParameterDefinitionSet;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.EncounterResult;
import org.openmrs.calculation.result.ListResult;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Contains behavior tests for patient calculations
 */
public class PatientBehaviorTest extends BaseModuleContextSensitiveTest {
	
	private static final String TEST_DATE_FORMAT = "yyyy-MM-dd";
	
	private PatientCalculationService service;
	
	private static final String TEST_DATA_XML = "org/openmrs/calculation/include/moduleTestData.xml";
	
	@Before
	public void before() throws Exception {
		service = Context.getService(PatientCalculationService.class);
	}
	
	protected PatientCalculationService getService() {
		return service;
	}
	
	/**
	 * 
	 */
	@Test
	public void shouldCalculateThePatientAge() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		int patientId = 2;
		int expected = Context.getPatientService().getPatient(patientId).getAge();
		CalculationResult calculated = getService().evaluate(patientId, ageCalculation);
		Assert.assertEquals(expected, calculated.asType(Integer.class).intValue());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void shouldCalculateThePatientAgeBasedOnContextualInfo() throws Exception {
		
		PatientCalculation ageCalculation = getAgeCalculation();
		
		int patientId = 2;
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		PatientCalculationContext ctxt = getService().createCalculationContext();
		ctxt.setNow(date);
		
		int expected = Context.getPatientService().getPatient(patientId).getAge(date);
		Assert.assertEquals(expected, getService().evaluate(patientId, ageCalculation, ctxt).asType(Integer.class)
		        .intValue());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void shouldCalculateThePatientAgeBasedOnContextualInfoAndParameterValues() throws Exception {
		
		PatientCalculation ageCalculation = getAgeCalculation();
		ParameterDefinitionSet pds = ageCalculation.getParameterDefinitionSet();
		ParameterDefinition pd = pds.getParameterByKey("units");
		Assert.assertNotNull(pd);
		Assert.assertEquals(pd.getName(), "Units Of Age");
		
		int patientId = 2;
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		PatientCalculationContext ctxt = getService().createCalculationContext();
		ctxt.setNow(date);
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(pd.getKey(), "months");
		
		Assert.assertEquals(296, getService().evaluate(patientId, ageCalculation, values, ctxt).asType(Integer.class)
		        .intValue());
	}
	
	@Test
	public void shouldCalculateTheAgesOfPatientsInACohort() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		int patientId1 = 2;
		int patientId2 = 7;
		List<Integer> cohort = Arrays.asList(patientId1, patientId2);
		
		PatientService ps = Context.getPatientService();
		int expected1 = ps.getPatient(patientId1).getAge();
		int expected2 = ps.getPatient(patientId2).getAge();
		CalculationResultMap cr = getService().evaluate(cohort, ageCalculation);
		Assert.assertEquals(expected1, cr.get(patientId1).asType(Integer.class).intValue());
		Assert.assertEquals(expected2, cr.get(patientId2).asType(Integer.class).intValue());
	}
	
	@Test
	public void shouldCalculateTheAgesOfPatientsInACohortBasedOnContextualInfo() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		int patientId1 = 2;
		int patientId2 = 7;
		List<Integer> cohort = Arrays.asList(patientId1, patientId2);
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		PatientCalculationContext ctxt = getService().createCalculationContext();
		ctxt.setNow(date);
		
		PatientService ps = Context.getPatientService();
		int expected1 = ps.getPatient(patientId1).getAge(date);
		int expected2 = ps.getPatient(patientId2).getAge(date);
		
		CalculationResultMap cr = getService().evaluate(cohort, ageCalculation, ctxt);
		Assert.assertEquals(expected1, cr.get(patientId1).asType(Integer.class).intValue());
		Assert.assertEquals(expected2, cr.get(patientId2).asType(Integer.class).intValue());
	}
	
	@Test
	public void shouldCalculateTheAgesOfPatientsInACohortBasedOnContextualInfoAndParameterValues() throws Exception {
		PatientCalculation ageCalculation = getAgeCalculation();
		int patientId1 = 2;
		int patientId2 = 7;
		List<Integer> cohort = Arrays.asList(patientId1, patientId2);
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		PatientCalculationContext ctxt = getService().createCalculationContext();
		ctxt.setNow(date);
		
		ParameterDefinition pd = ageCalculation.getParameterDefinitionSet().getParameterByKey("units");
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(pd.getKey(), "months");
		
		CalculationResultMap cr = getService().evaluate(cohort, ageCalculation, values, ctxt);
		Assert.assertEquals(296, cr.get(patientId1).asType(Integer.class).intValue());
		Assert.assertEquals(280, cr.get(patientId2).asType(Integer.class).intValue());
	}
	
	@Test
	public void shouldGetAnEncounterResultAsADateBasedResult() throws Exception {
		executeDataSet(TEST_DATA_XML);
		int patientId = 7;
		//probably this could be a sql stmt for getting the most recent encounter
		Encounter expectedEncounter = Context.getEncounterService().getEncounter(5);
		PatientCalculation calc = getMostRecentEncounterCalculation();
		EncounterResult result = (EncounterResult) getService().evaluate(patientId, calc);
		
		Assert.assertEquals(expectedEncounter, result.asType(Encounter.class));
		//Since this is a date-based result, check the date
		Assert.assertEquals(expectedEncounter.getEncounterDatetime(), result.getDateOfResult());
	}
	
	@Test
	public void shouldGetAnObsResultAsADateBasedResult() throws Exception {
		executeDataSet(TEST_DATA_XML);
		int patientId = 7;
		Obs expectedObs = Context.getObsService().getObs(103);
		PatientCalculation calc = getMostRecentWeightCalculation();
		ObsResult result = (ObsResult) getService().evaluate(patientId, calc);
		
		Assert.assertEquals(expectedObs, result.asType(Obs.class));
		Assert.assertEquals(expectedObs.getObsDatetime(), result.getDateOfResult());
	}
	
	@Test
	public void ShouldGetADateBasedResultBasingOnACachedResultInTheCalculationContext() throws Exception {
		executeDataSet(TEST_DATA_XML);
		
		Integer patientId = 7;
		Obs expectedMostRecentObs = Context.getObsService().getObs(103);
		String cacheKey = MostRecentObsCalculation.class.getName() + ".5089.7";
		
		PatientCalculationContext context = getService().createCalculationContext();
		Assert.assertTrue(context.getFromCache(cacheKey) == null);
		
		//sanity check, since the cache is empty, it should return the most recent obs amongst all obs for the patient
		PatientCalculation mostRecentObsCalculation = getMostRecentWeightCalculation();
		ObsResult firstTestResult = (ObsResult) getService().evaluate(patientId, mostRecentObsCalculation, context);
		Assert.assertEquals(expectedMostRecentObs, firstTestResult.asType(Obs.class));
		Assert.assertTrue(context.getFromCache(cacheKey) == firstTestResult);
		
		PatientCalculation anotherMostRecentObsCalculation = getMostRecentWeightCalculation();
		ObsResult secondTestResult = (ObsResult) getService().evaluate(patientId, anotherMostRecentObsCalculation, context);
		Assert.assertTrue(context.getFromCache(cacheKey) == secondTestResult);
		Assert.assertTrue(firstTestResult == secondTestResult);
	}
	
	@Test
	public void shouldGetListResultOfRecentEncounters() throws Exception {
		executeDataSet(TEST_DATA_XML);
		
		PatientCalculation calc = (PatientCalculation) new ClasspathCalculationProvider().getCalculation(
		    RecentEncounterCalculation.class.getName(), "2008-07-01");
		
		int patientId = 7;
		ListResult result = (ListResult) getService().evaluate(patientId, calc);
		
		Encounter expectedFirst = Context.getEncounterService().getEncounter(3);
		Assert.assertEquals(expectedFirst, result.getFirstResult().asType(Encounter.class));
		
		Encounter expectedLast = Context.getEncounterService().getEncounter(5);
		Assert.assertEquals(expectedLast, result.getLastResult().asType(Encounter.class));
	}
	
	@Test
	public void shouldNotFailWhenInnerCalculationMakesDBChangesForOuterCalculation() throws Exception {
		PatientCalculation calc = (PatientCalculation) new ClasspathCalculationProvider().getCalculation(
		    OuterCalculation.class.getName(), null);
		getService().evaluate(1, calc);
	}
	
	/**
	 * @return an Example calculation instance
	 */
	private PatientCalculation getAgeCalculation() throws InvalidCalculationException {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		return (PatientCalculation) p.getCalculation(AgeCalculation.class.getName(), null);
	}
	
	/**
	 * @return an Example calculation instance
	 */
	private PatientCalculation getMostRecentEncounterCalculation() throws InvalidCalculationException {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		return (PatientCalculation) p.getCalculation(MostRecentEncounterCalculation.class.getName(), null);
	}
	
	/**
	 * @return an Example calculation instance
	 */
	private PatientCalculation getMostRecentWeightCalculation() throws InvalidCalculationException {
		ClasspathCalculationProvider p = new ClasspathCalculationProvider();
		return (PatientCalculation) p.getCalculation(MostRecentObsCalculation.class.getName(), "5089");
	}
}
