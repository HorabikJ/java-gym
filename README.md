# javaGym

A simple REST application for gym classes reservation and admin management, written in Java.

## Built With

* Java 1.8 
* Spring Boot 2.1.5
    * Spring Security
    * Spring Web Starter
    * Spring Data JPA
* MySQL 8.0 database
* Maven 
* Swagger 2.9.2

## IDE environment

* IntelliJ Idea Ultimate

## What to do to run the application:

1. Download the project from: https://github.com/HorabikJ/java-gym
2. Open the project in IntelliJ Idea Ultimate and import all Maven dependencies.
3. Create schema "java_gym" in MySQL 8.0 database.
4. Change the values of "spring.datasource.username" and "spring.datasource.password" in 
    application.properties file for your database username and password.
5. Run the application and visit: http://localhost:8080/swagger-ui.html to see REST API documentation for this project generated by Swagger.
    Use below super admin credentials to log in to the application:
    login: super@javagym.pl
    password: 12345
     
## A little more about the application:
    
*  Access to application resources is based on the user role. 
    * Guest - unauthenticated user
    * User - application customer
    * Admin - application admin
    * Super - super admin
    
* Guest can do:
    * see all training classes for next 2 weeks
    * see all instructors and training types
    * see all training classes for given instructor or training type
    * register as a user
    
* User can do:
    * update all his properties (i.e. name, password, email...)
    * make and cancel a reservation for his training classes
    * see his future and past reservations
    
* Admin can do:
    * register new admin
    * delete any regular user or set his account as inactive
    * send newsletter to all users with newsletter consent
    * send new account activation email to any user
    * see all users and admins and search for them by email and name
    
    * add, delete and update any training type
    
    * add, delete and update any instructor
    * send an email to any instructor

    * add, delete and update any training class
    * see all users reservations for any training class
    
* Super Admin can do:
    * delete any regular admin or user account
    * set any regular admin or user account as inactive
    
## Email service in application:

* After registration, user receives an email with unique account activation link, that is valid for next 30 minutes and is clickable only once.
If user will not click the link within given time, he can ask admin to send him a new activation link.

* User can reset his password, to do it he has to provide his valid email that is in application database and then he will receive an email with
link to the reset password website, this link also will be valid for next 30 minutes. If password is successfully reset, the reset password link expires.

* User can change his email address in his account, to do so he has to request the change and confirmation email will be sent to new email given by user.
The change will be done after the confirmation link is clicked by user.

* Classes reservation email service:
    * For every training class reservation, user receives an email with reservation confirmation and training class details.
    * For every cancellation of reservation, user receives an email with confirmation of this action.
    * If training class is fully booked, user can make a reservation but he will be moved to awaiting list.
    If enough reservations from proper reservation list will be freed, user automatically will be moved from
    awaiting list to reservation list and at the moment of this action an email with confirmation will be sent to user.

## Sample actions screenshots:

### Add new training class by admin.

* A POST action to add new training class by admin. With one click admin can add a training class and choose the occurrence for this class (weekly or daily) and set
how many times this class wil repeat in calendar. In below instance we add a training class with start date 2019-07-05 17:00, and this class will repeat every week at the same time for next 10 weeks.
Every training class has its own "id", and as well as this group of ten classes that has been added by this action has its own "classGroupId". Admin can modify one training class using its "id", can modify 
the whole group by its "classGroupId". For example, set the instructor for training classes with the same "classGroupId".

![Alt text](src/main/resources/static/images/add-new-training-class-1.PNG?raw=true "POST action - Add new training class, weekly occurrence, repeat 10 times")

* Partial result of above action:

![Alt text](src/main/resources/static/images/add-new-training-class-2.PNG?raw=true "Result - Add new training class, weekly occurrence, repeat 10 times")

* Set instructor for recently added training classes:

![Alt text](src/main/resources/static/images/set-instructor-by-class-group-id.PNG?raw=true "Set instructor by class group id.")

### Training classes shown to users:

* Users and guests can only view training classes that are in future but no more than two weeks, and that has training type and
instructor assigned. So classes that are ready for reservation.

![Alt text](src/main/resources/static/images/users-show-classes.PNG?raw=true "Show classes available for users.git push")

