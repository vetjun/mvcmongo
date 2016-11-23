# User Management Panel

This Project contains a collection of mongoDb repository and Spring Mvc sample that
demonstrate the User Management Panel.

# Controllers

* getIssuerByTicker :Getting one user with parameter id
* getAllIssuers :Getting all users
* updateIssuerByTicker :Updating one user with parameter id and User class
* deleteIssuerByTicker :Deletes User with parameter id
* addIssuer :Adding new User with User class

#Functions in Controller

* readAll :Getting one Reader object and builds a string from it
* readJsonFromUrl :Getting Json result from specified url

#Repositories

* A mongoDb repository (UserRepository) autowires User Class 


#User Management Panel

Topics covered:

* Display all users in Grid table
* Update user information on Grid table
* Refresh Grid Table users
* Add new user
* Captcha validation
* Return required input validation
* Delete user that selected in table
* Waiting div element while processing ajax calling
* input masking on telephone input field


#Dependencies

Project uses maven to dependency management.It defines dependencies on pom.xml and dependencies has version property is defined in pom.xml too.
required dependencies for spring mvc and mongodb access are spring mvc,spring context,mongodb driver,javax etc..

#Target Runtime

It is not required but Project's runtime server is Pivotal Tc server.

#Conclusion

I used some tutorials for completing this project.I started with a basic spring mvc project.Then added mongoDb dependencies.
I created User class and UserRepository for controlling CRUD actions , in web.xml file defined there is servlet mapping tag.
I configured dispatcher to map userController.Then I create Controller actions for ajax callings From update,delete,Add,Get All actions.
Finally I create jsp file uses extJs library for Grid Table .I configured  and initialized it to control users.I added captcha and input masking finally.
That's all
