---------------------------------------------------------------------------------------------------------
-------------------------------------- Build Process ----------------------------------------------------
---------------------------------------------------------------------------------------------------------

Preconditions:
1. Must be installed and running MySQL
2. Must be installed maven

Build process:
1. Configure MySQL connection properties in /application/main/resources/META-INF/spring/persistence_dev.properties
2. Build project using mvn clean install

Run process:
1. Go to /application
2. Start application using mvn jetty:run -Dspring.profiles.active=dev or deploy to tomcat or resin.

---------------------------------------------------------------------------------------------------------
-------------------------------------- Application purpose ----------------------------------------------------
---------------------------------------------------------------------------------------------------------

CF is a test we application based on spring-hibernate-jpa configuration in com.cf.config.JpaConfig and
META-INF/spring/persistence_dev.properties for mysql database, intended to be deployed to Cloud Foundry.

At this stage the application is throwing the following error during deployment:
 …
org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean com.ta.config.JpaConfig.entityManagerFactoryBean()] threw exception; nested exception is java.lang.IllegalStateException: required key [hibernate.format_sql] not found
 …
despite of having the hibernate.format_sql property set in persistence_dev.properties