# WebQuizEngine
Web Quiz Engine implemented in Java using Spring Boot. 

This work is an implementation of the following Hyperskill project requirements https://hyperskill.org/projects/91?goal=7.

The goal of this project is to implement a database that stores user data and quiz data. A user makes a request to the database via a given rest endpoint
in order to submit a new quiz, solve an already existing quiz, delete a quiz they've previously created, retrieve any given quiz(zes), etc.

A backend exposes multiple endpoints that connect to an H2 database and employs Http Basic authentication via Spring Security. A user must post new
security credentials to the "/api/register" endpoint to register, and must include those same credentials every time they make a request to a protected resource.

![image](https://user-images.githubusercontent.com/61985975/82439863-49f6c300-9a93-11ea-9e0e-21bab1e30d62.png)




