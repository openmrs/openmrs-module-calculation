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
package org.openmrs.calculation;

import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.test.Verifies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains test methods for {@link CalculationUtilTest}
 */
public class CalculationUtilTest {
	
	private enum Geek {
		NERD
	}
	
	/**
	 * @see {@link CalculationUtil#isPrimitiveWrapperClassName(Class)}
	 */
	@Test
	@Verifies(value = "return true for primitive type wrapper class names", method = "isPrimitiveWrapperClassName(String)")
	public void isPrimitiveWrapperClassName_shouldReturnTrueForPrimitiveTypeWrapperClassNames() throws Exception {
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Boolean"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Character"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Byte"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Short"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Integer"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Float"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Double"));
		assertTrue(CalculationUtil.isPrimitiveWrapperClassName("java.lang.Long"));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a character value to Character", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertACharacterValueToCharacter() throws Exception {
		assertEquals('c', CalculationUtil.cast("c", Character.class).charValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a result with an number value in the valid range to byte", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAResultWithAnNumberValueInTheValidRangeToByte() throws Exception {
		assertEquals(127, CalculationUtil.cast(127, Byte.class).byteValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a single character value to Short", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertASingleCharacterValueToShort() throws Exception {
		assertEquals(2, CalculationUtil.cast('2', Short.class).shortValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid single character value to Integer", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidSingleCharacterValueToInteger() throws Exception {
		assertEquals(2, CalculationUtil.cast('2', Integer.class).intValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid single character value to Long", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidSingleCharacterValueToLong() throws Exception {
		assertEquals(2, CalculationUtil.cast('2', Long.class).longValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Boolean", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToBoolean() throws Exception {
        assertTrue(CalculationUtil.cast("true", Boolean.class).booleanValue());
        assertFalse(CalculationUtil.cast("false", Boolean.class).booleanValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Byte", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToByte() throws Exception {
		assertEquals(2, CalculationUtil.cast("2", Byte.class).byteValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Double", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToDouble() throws Exception {
		assertEquals(1.7976931348623157e+308, CalculationUtil.cast("1.7976931348623157e+308", Double.class)
		        .doubleValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Float", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToFloat() throws Exception {
		assertEquals(3.4028235e+1f, CalculationUtil.cast("3.4028235e+1f", Float.class).floatValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Integer", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToInteger() throws Exception {
		assertEquals(122, CalculationUtil.cast("122", Integer.class).intValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Long", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToLong() throws Exception {
		assertEquals(122, CalculationUtil.cast("122", Long.class).longValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string value to Short", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringValueToShort() throws Exception {
		assertEquals(122, CalculationUtil.cast("122", Short.class).shortValue());
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert the value to the specified type if it is compatible", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertTheValueToTheSpecifiedTypeIfItIsCompatible() throws Exception {
		Person convertedObject = CalculationUtil.cast(new Patient(), Person.class);
		assertNotNull(convertedObject);
		assertTrue(Person.class.isAssignableFrom(convertedObject.getClass()));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should fail if the value to convert is not of a compatible type", method = "cast(Object,Class<T>)")
	public void cast_shouldFailIfTheValueToConvertIsNotOfACompatibleType() throws Exception {
		assertThrows(ConversionException.class, () -> CalculationUtil.cast(new Person(), Patient.class));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should return null if the passed in value is null", method = "cast(Object,Class<T>)")
	public void cast_shouldReturnNullIfThePassedInValueIsNull() throws Exception {
		assertNull(CalculationUtil.cast(null, null));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string to a class object", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringToAClassObject() throws Exception {
		assertEquals(Concept.class, CalculationUtil.cast("org.openmrs.Concept", Class.class));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string to a Locale", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringToALocale() throws Exception {
		assertEquals(Locale.US, CalculationUtil.cast("en_US", Locale.class));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<T>)}
	 */
	@Test
	@Verifies(value = "should convert a valid string to an enum constant", method = "cast(Object,Class<T>)")
	public void cast_shouldConvertAValidStringToAnEnumConstant() throws Exception {
		assertEquals(Geek.NERD, CalculationUtil.cast("NERD", Geek.class));
	}
	
	/**
	 * @see {@link CalculationUtil#cast(Object,Class<QT;>)}
	 */
	@Test
	@Verifies(value = "should format a date object to a string using the default date format", method = "cast(Object,Class<QT;>)")
	public void cast_shouldFormatADateObjectToAStringUsingTheDefaultDateFormat() throws Exception {
		Date date = new Date();
		assertEquals(Context.getDateFormat().format(date), CalculationUtil.cast(date, String.class));
	}
}
