package org.openmrs.calculation;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.util.OpenmrsClassLoader;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import groovy.lang.GroovyClassLoader;

/**
 * Automatically loads calculation classes from APPDATA/configuration/calculation.
 * A single instance of the class will be instantiated and cached, and any @Autowired properties will be injected.
 * Supports:
 * <ul>
 *     <li>.groovy ... parsed by the groovy classloader (file should define a single class, that implements Calculation)</li>
 * </ul>
 */
@Component
public class ImplementationConfiguredCalculationProvider implements CalculationProvider {

	protected final Log log = LogFactory.getLog(this.getClass());

	private File directory = new File(OpenmrsUtil.getApplicationDataDirectory(), "configuration/calculation");

	private Map<String, Calculation> calculations = new HashMap<String, Calculation>();

	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader(OpenmrsClassLoader.getInstance());

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	@Override
	public Calculation getCalculation(String calculationName, String configuration) throws InvalidCalculationException {
		if (!calculations.containsKey(calculationName)) {
			File file = new File(directory, calculationName + ".groovy");
			loadCalculation(file);
		}
		return calculations.get(calculationName);
	}

	/**
	 * Will cache the calculation, if it is successfully loaded
	 * @param file
	 * @return
	 */
	private Calculation loadCalculation(File file) {
		if (file.exists()) {
			try {
				Class clazz = groovyClassLoader.parseClass(file);
				Object instance = clazz.newInstance();
				Calculation calculation = (Calculation) instance;
				autowireCapableBeanFactory.autowireBean(calculation);
				calculations.put(calculationNameFor(file), calculation);
				return calculation;
			}
			catch (Exception e) {
				log.error("Error loading implementation-configured groovy calculation from " + file.getName(), e);
			}
		} else {
			log.warn("Trying to load calculation from " + file.getPath() + " but the file doesn't exist");
		}
		return null;
	}

	private String calculationNameFor(File file) {
		String name = file.getName();
		int index = name.lastIndexOf(".");
		return name.substring(0, index);
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public void clearCache() {
		calculations = new HashMap<String, Calculation>();
	}

	/**
	 * @return the cache of all calculations that have been loaded. May contain null entries to indicate that
	 */
	Map<String, Calculation> getCalculations() {
		return Collections.unmodifiableMap(calculations);
	}

	public void loadCalculationsFromDirectory() {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				loadCalculation(file);
			}
		}
	}

}
