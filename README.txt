CppUTest Eclipse Plugin
========================
CppUTest is a general purposed unit test framework for C/C++. This plugin is made to make unit testing esier to work with CppUTest & Eclipse.
The goal of this plugin is roughly:
1. Act like JUnit plugin (red/green bars!)
2. Support Test-Driven Developent
3. Make unit testing for legacy code easier


How To Build
------------------
mvn install

This will run the unit test & integration test and then generate the update package/site.
The update site is at update-site/target

How To Work On It
-------------------
1.clone the project
2.import projects from the folder

Each folder is a eclipse project, they are:
org.cpputest.plugin             The general plug-in implementation.
                                This part will do stub/mock code generation.
                                It does not depend on CDT
org.cpputest.runtools(planning) The cpputest run tools.
                                It will depend on CDT
org.cpputest.feature            Feature description of the plug-in
org.cpputest.plugin.unittest    Unit tests
SWTBotTest                      Integration test

In order to edit SWTBotTest project, you might need to install SWTBot eclipse plug-in.
 