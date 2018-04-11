package org.openmrs.calculation;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class ImplementationConfiguredCalculationProviderTest extends BaseModuleContextSensitiveTest {

	@Autowired
	ImplementationConfiguredCalculationProvider provider;

	@Autowired
	PatientCalculationService service;

	@Before
	public void setUp() throws Exception {
		File groovyDef = new File(getClass().getClassLoader().getResource("implconfig/weight.groovy").getPath());
		provider.setDirectory(groovyDef.getParentFile());
		provider.clearCache();
	}

	@Test
	public void testLoadingCalculation() throws Exception {
		PatientCalculation calculation = (PatientCalculation) provider.getCalculation("weight", null);
		CalculationResult result = service.evaluate(7, calculation);
		assertThat(result, IsInstanceOf.instanceOf(SimpleResult.class));
		assertThat(result.getValue(), Is.<Object>is(61.0));
		assertThat(provider.getCalculations().size(), is(1));
	}

	@Test
	public void testMissingCalculation() throws Exception {
		PatientCalculation calculation = (PatientCalculation) provider.getCalculation("missing", null);
		assertNull(calculation);
		assertThat(provider.getCalculations().size(), is(0));
	}

}