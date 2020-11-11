# CSE2115 - Project

### Running 
`gradle bootRun`

### Testing
```
gradle test
```

To generate a coverage report:
```
gradle jacocoTestCoverageVerification
```


And
```
gradle jacocoTestReport
```
The coverage report is generated in: build/reports/jacoco/test/html, which does not get pushed to the repo. Open index.html in your browser to see the report. 

### Static analysis
```
gradle checkStyleMain
gradle checkStyleTest
gradle pmdMain
gradle pmdTest
```

### Notes
- You should have a local .gitignore file to make sure that any OS-specific and IDE-specific files do not get pushed to the repo (e.g. .idea). These files do not belong in the .gitignore on the repo.
- If you change the name of the repo to something other than template, you should also edit the build.gradle file.
- You can add issue and merge request templates in the .gitlab folder on your repo. 

### Basic Functionality
The aim is to create a series of microservices which will be able to create a schedule for lectures which upholds a given set of constraints. The services available will differ depending on what type of user is accesing the api, either a student or a teacher.

Teachers:
 - Will be able to provide details of a lecture, or series of lectures, such as the duration of the lecture, and how manyy occur each week. The api will then create several instances of that lecture accross various timeslots to allow all students enrolled in that course to attend at least one instance of that lecture. (given the requirement to allow a student to attend at least 1 lecture every 2 days im not sure sure if my description is actually accurate, need clarification on how many additional lecture we need to create, if any)
 - Will have access to information about all of the lectures that they are teaching
 - In the event that they have coronavirus, all of their lectures will be canceled indefinitely
 - In the event that they have recovered from coronavirus, their lectures will be resumed.

Students:
 - Will have access to information about all of the lectures they can register for
 - Will be able to register for lectures
 - Will have access to information about all of the lectures they have registered for (I think it would be helpful to create a distinction between the lectures they can register for and the ones they already have registered for)
 - Will be able to Cancel a registration, signalling that they no longer intend to attend that lecture.
 - Must be able to attend at least one lecture every two weeks.

### Group members
Jakub Tokarz | J.M.Tokarz@student.tudelft.nl | 5017475
