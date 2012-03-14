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
package org.openmrs.calculation.util;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.test.Verifies;


/**
 * Contains test methods for {@link CalculationUtilTest}
 */
public class CalculationUtilTest {

	/**
	 * @see {@link CalculationUtil#isPrimitiveWrapperType(Class)}
	 */
	@Test
	@Verifies(value = "should return true for primitive type wrappers classes", method = "isPrimitiveWrapperType(Class)")
	public void isPrimitiveWrapperType_shouldReturnTrueForPrimitiveTypeWrapperClasses() throws Exception {
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Boolean.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Character.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Byte.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Short.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Integer.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Float.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Double.class));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperType(Long.class));
	}
	
	/**
	 * @see {@link CalculationUtil#isPrimitiveWrapperClassName(Class)}
	 */
	@Test
	@Verifies(value = "return true for primitive type wrapper class names", method = "isPrimitiveWrapperClassName(String)")
	public void isPrimitiveWrapperClassName_shouldReturnTrueForPrimitiveTypeWrapperClassNames() throws Exception {
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Boolean"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Character"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Byte"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Short"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Integer"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Float"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Double"));
		Assert.assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Long"));
	}
}
