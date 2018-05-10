[![Build Status](https://travis-ci.org/openmrs/openmrs-module-calculation.svg?branch=master)](https://travis-ci.org/openmrs/openmrs-module-calculation)

# OpenMRS Calculation Module

The Calculation module provides a framework for OpenMRS developers to author from simple to complex calculations to produce a particular piece of data for a single patient or a cohort of patients. It is a high level abstraction of rules/calculations, simple in that it provides a minimal number of useful concrete implementations of its core components that can be used out of the box but providing a much more flexible tool for authoring patient centered calculations.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the Calculation Module on a live system.

### Prerequisites

Follow instructions on OpenMRS web site to download and install the OpenMRS SDK, found at https://wiki.openmrs.org/display/docs/OpenMRS+SDK+Step+By+Step+Tutorials

### Installing
Download the Calculation Module. On the OpenMRS website, you can find a compiled version of the module at https://addons.openmrs.org/#/show/org.openmrs.module.calculation. Upload this file into your system. See instructions below if you need instructions on how to upload the file into your system.

You may also download the source code from the Calculation Module on github. This is useful for making changes on the module. Follow the following instructions to do this. 

```
git clone https://github.com/openmrs/openmrs-module-calculation.git
cd openmrs-module-calculation
mvn clean install
```

Find the .omod file in openmrs-module-calculation/omod/target. Upload this file into your system.

To upload the file to your system, find your OpenMRS modules folder, which should be labeled

```
/openmrs/modules/
```

Drag your .omod file into this folder, if it does not already contain one.

From here, run your OpenMRS server on and navigate to (please note that this uses port 8080. This is the default port, however yours could be different)

```
http://localhost:8080/openmrs/initialsetup/
```

for a first time setup.

If you have any trouble getting your OpenMRS server running, please go to https://wiki.openmrs.org/display/docs/OpenMRS+SDK#OpenMRSSDK-Settingupservers

## Running the tests

Tests automatically run with the installation of the Calculation Module. If the module is already installed, simply move to the openmrs/openmrs-module-calculation folder and use the following instruction to reinstall the module, running the test cases.

```
mvn clean install
```

## Authors

See the list of [contributors](https://github.com/openmrs/openmrs-module-calculation/graphs/contributors) who participated in this project.

## Project Resources

[Wiki page](https://wiki.openmrs.org/display/docs/Calculation+Module)

## License
This project is licensed under the Mozilla Public License, v. 2.0. Obtain a copy of the License at
http://license.openmrs.org

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

Copyright (C) OpenMRS, LLC.  All Rights Reserved.
