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

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Cohort;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.api.patient.PatientCalculationContext;
import org.openmrs.calculation.api.patient.PatientCalculationService;
import org.openmrs.calculation.definition.ParameterDefinition;
import org.openmrs.calculation.definition.ParameterDefinitionSet;
import org.openmrs.calculation.provider.CalculationProvider;
import org.openmrs.calculation.provider.DemoCalculationProvider;
import org.openmrs.calculation.result.CohortResult;
import org.openmrs.calculation.result.EncounterResult;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Contains behaviour tests for patient calculations
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
		CalculationProvider p = new DemoCalculationProvider();
		PatientCalculation ageCalculation = (PatientCalculation) p.getCalculation("age", null);
		
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
		CalculationProvider p = new DemoCalculationProvider();
		PatientCalculation ageCalculation = (PatientCalculation) p.getCalculation("age", null);
		
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
		CalculationProvider p = new DemoCalculationProvider();
		PatientCalculation ageCalculation = (PatientCalculation) p.getCalculation("age", null);
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
		PatientCalculation ageCalculation = (PatientCalculation) new DemoCalculationProvider().getCalculation("age", null);
		int patientId1 = 2;
		int patientId2 = 7;
		Cohort cohort = new Cohort();
		cohort.addMember(patientId1);
		cohort.addMember(patientId2);
		
		PatientService ps = Context.getPatientService();
		int expected1 = ps.getPatient(patientId1).getAge();
		int expected2 = ps.getPatient(patientId2).getAge();
		CohortResult cr = getService().evaluate(cohort, ageCalculation);
		Assert.assertEquals(expected1, cr.get(patientId1).asType(Integer.class).intValue());
		Assert.assertEquals(expected2, cr.get(patientId2).asType(Integer.class).intValue());
	}
	
	@Test
	public void shouldCalculateTheAgesOfPatientsInACohortBasedOnContextualInfo() throws Exception {
		PatientCalculation ageCalculation = (PatientCalculation) new DemoCalculationProvider().getCalculation("age", null);
		int patientId1 = 2;
		int patientId2 = 7;
		Cohort cohort = new Cohort();
		cohort.addMember(patientId1);
		cohort.addMember(patientId2);
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		PatientCalculationContext ctxt = getService().createCalculationContext();
		ctxt.setNow(date);
		
		PatientService ps = Context.getPatientService();
		int expected1 = ps.getPatient(patientId1).getAge(date);
		int expected2 = ps.getPatient(patientId2).getAge(date);
		
		CohortResult cr = getService().evaluate(cohort, ageCalculation, ctxt);
		Assert.assertEquals(expected1, cr.get(patientId1).asType(Integer.class).intValue());
		Assert.assertEquals(expected2, cr.get(patientId2).asType(Integer.class).intValue());
	}
	
	@Test
	public void shouldCalculateTheAgesOfPatientsInACohortBasedOnContextualInfoAndParameterValues() throws Exception {
		PatientCalculation ageCalculation = (PatientCalculation) new DemoCalculationProvider().getCalculation("age", null);
		int patientId1 = 2;
		int patientId2 = 7;
		Cohort cohort = new Cohort();
		cohort.addMember(patientId1);
		cohort.addMember(patientId2);
		
		Date date = new SimpleDateFormat(TEST_DATE_FORMAT).parse("2000-01-01");
		PatientCalculationContext ctxt = getService().createCalculationContext();
		ctxt.setNow(date);
		
		ParameterDefinition pd = ageCalculation.getParameterDefinitionSet().getParameterByKey("units");
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(pd.getKey(), "months");
		
		CohortResult cr = getService().evaluate(cohort, ageCalculation, values, ctxt);
		Assert.assertEquals(296, cr.get(patientId1).asType(Integer.class).intValue());
		Assert.assertEquals(280, cr.get(patientId2).asType(Integer.class).intValue());
	}
	
	@Test
	public void shouldGetAnEncounterResultAsADateBasedResult() throws Exception {
		executeDataSet(TEST_DATA_XML);
		int patientId = 7;
		//probably this could be a sql stmt for getting the most recent encounter
		Encounter expectedEncounter = Context.getEncounterService().getEncounter(5);
		EncounterResult result = (EncounterResult) getService().evaluate(patientId,
		    (PatientCalculation) new DemoCalculationProvider().getCalculation("mostRecentEncounter", null));
		
		Assert.assertEquals(expectedEncounter, result.asType(Encounter.class));
		//Since this is a datebased result, check the date
		Assert.assertEquals(expectedEncounter.getEncounterDatetime(), result.getDateOfResult());
	}
	
	@Test
	public void shouldGetAnObsResultAsADateBasedResult() throws Exception {
		executeDataSet(TEST_DATA_XML);
		int patientId = 7;
		Obs expectedObs = Context.getObsService().getObs(103);
		ObsResult result = (ObsResult) getService().evaluate(patientId,
		    (PatientCalculation) new DemoCalculationProvider().getCalculation("mostRecentObs", null));
		
		Assert.assertEquals(expectedObs, result.asType(Obs.class));
		Assert.assertEquals(expectedObs.getObsDatetime(), result.getDateOfResult());
	}
	
	@Test
	public void ShouldGetADateBasedResultBasingOnACachedResultInTheCalculationContext() throws Exception {
		executeDataSet(TEST_DATA_XML);
		//sanity check, since the cache is empty, it should return the most recent obs amongst all obs for the patient
		Obs expectedMostRecentObs = Context.getObsService().getObs(103);
		Integer patientId = 7;
		PatientCalculation mostRecentObsCalculation = (PatientCalculation) new DemoCalculationProvider().getCalculation(
		    "mostRecentObs", null);
		ObsResult testResult = (ObsResult) getService().evaluate(patientId, mostRecentObsCalculation);
		Assert.assertEquals(expectedMostRecentObs, testResult.asType(Obs.class));
		
		EncounterResult encounterResult = (EncounterResult) getService().evaluate(patientId,
		    (PatientCalculation) new DemoCalculationProvider().getCalculation("mostRecentEncounter", null));
		//Should find the most recent obs for the cached encounter
		Obs expectedMostRecentObsForMostRecentEncounter = Context.getObsService().getObs(102);
		ObsResult result = (ObsResult) getService().evaluate(patientId, mostRecentObsCalculation,
		    (PatientCalculationContext) encounterResult.getCalculationContext());
		
		Assert.assertEquals(expectedMostRecentObsForMostRecentEncounter, result.asType(Obs.class));
		Assert.assertEquals(expectedMostRecentObsForMostRecentEncounter.getObsDatetime(), result.getDateOfResult());
	}
}
