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

/**
 * This interface is meant to encapsulate any data that needs to exist during the course of the
 * evaluation of a single Calculation. It's primary purpose is to allow for calculation to store
 * instance data without using instance variables.
 */
public interface EvaluationInstanceData {

}
