# OpenMRS Calculation Module

The Calculation module provides a framework for OpenMRS developers to author from simple to complex calculations to produce a particular piece of data for a single patient or a cohort of patients. It is a high level abstraction of rules/calculations, simple in that it provides a minimal number of useful concrete implementations of its core components that can be used out of the box but providing a much more flexible tool for authoring patient centered calculations.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

A step by step series of examples that tell you have to get a development env running

Follow instructions on OpenMRS web site to download and install OpenMRS, found at https://wiki.openmrs.org/display/docs/Installing+OpenMRS+2.x 

### Installing

Clone the module through SSH:

```
git@github.com:yourname/openmrs-module-calculation.git
```

Once this is cloned, change your directory to the folder and input the command

```
mvn clean install
```

Change your directory to /omod/target/ folder. You will see a .omod file. Copy the file.

Find the /openmrs/modules folder, and paste the .omod file if there isn't one there already.

When you run OpenMRS, it will not have the Calcuation Module integrated.

## Running the tests

Change your directory into the module folder, and type

```
mvn clean install
```

This command will install the program, which also runs the tests.

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
