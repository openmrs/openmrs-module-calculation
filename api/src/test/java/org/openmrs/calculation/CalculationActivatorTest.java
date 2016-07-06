package org.openmrs.calculation;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.calculation.api.CalculationRegistrationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class CalculationActivatorTest extends BaseModuleContextSensitiveTest {

	@Autowired
	CalculationRegistrationService service;

	@Autowired
	ImplementationConfiguredCalculationProvider provider;

	@Before
	public void setUp() throws Exception {
		File groovyDef = new File(getClass().getClassLoader().getResource("implconfig/weight.groovy").getPath());
		provider.setDirectory(groovyDef.getParentFile());
	}

	@Test
	public void testRegisterCalculations() throws Exception {
		assertNull(service.getCalculationRegistrationByToken("weight"));

		new CalculationActivator().registerCalculations(provider, service);

		CalculationRegistration registration = service.getCalculationRegistrationByToken("weight");
		assertThat(registration.getToken(), is("weight"));
		assertThat(registration.getProviderClassName(), is(ImplementationConfiguredCalculationProvider.class.getName()));
		assertThat(registration.getCalculationName(), is("weight"));
	}

	@Test
	public void testOverwritingRegistration() throws Exception {
		service.saveCalculationRegistration(new CalculationRegistration("weight", ClasspathCalculationProvider.class.getName(),
				MostRecentObsCalculation.class.getName(), "5089"));

		new CalculationActivator().registerCalculations(provider, service);

		CalculationRegistration registration = service.getCalculationRegistrationByToken("weight");
		assertThat(registration.getToken(), is("weight"));
		assertThat(registration.getProviderClassName(), is(ImplementationConfiguredCalculationProvider.class.getName()));
		assertThat(registration.getCalculationName(), is("weight"));
	}

	@Test
	public void testDeletingStaleRegistration() throws Exception {
		service.saveCalculationRegistration(new CalculationRegistration("height",
				ImplementationConfiguredCalculationProvider.class.getName(), "nonexistent", null));

		new CalculationActivator().registerCalculations(provider, service);

		assertNull(service.getCalculationRegistrationByToken("height"));
	}
}