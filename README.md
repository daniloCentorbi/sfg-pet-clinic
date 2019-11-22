[![CircleCI](https://circleci.com/gh/daniloCentorbi/sfg-pet-clinic.svg?style=svg)](https://circleci.com/gh/daniloCentorbi/sfg-pet-clinic)


# sfg-pet-clinic
Pet clinic application developed following the spring framework course.

# Running petclinic locally

Petclinic is a Spring Boot application built using Maven. You can build a jar file and run it from the command line and

then access petclinic here: http://localhost:8080/

# Running petclinic as container

You can run the application with the command:

docker run -p 8080:8080 dinokrodino/pet-clinic

This will run the pet clinic application locally.

# Database configuration

In its default configuration, Petclinic uses an in-memory database (HSQLDB) which gets populated at startup with data. A similar setup is provided for MySql in case a persistent database configuration is needed. Note that whenever the database type is changed, the app needs to be run with a different profile: spring.profiles.active=mysql for MySql.

You could start MySql locally with whatever installer works for your OS, or with docker:

docker run -e MYSQL_ROOT_PASSWORD=petclinic -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:5.7.8
