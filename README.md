# Lecture Hall Scheduler

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

# Software architecture

**See the ./documents/architecture.pdf for the full images of this document**

Explain whole systems architecture:
Our microservice architecture has two important microservices that define the flow of the
requests, the Eureka server, and the Gateway-authentication server. Our
Gateway-authentication server authenticates the users and forwards the request to the intended
microservice. Eureka server simply allows the microservices to talk to each other. Upon this
architecture, we created a microservice system that can communicate with itself, is accessible
by one port (though the gateway), and whose requests are load-balanced.
Gateway / Authentication:
The main objective of this module is to redirect the incoming request to the intended
microservice. This is achieved by a Zuul proxy server. Zuul is an edge service that proxies
request to multiple backing services and provides a unified front door to our system. The major
benefit of using a gateway is that it encapsulates the internal structure of the whole application.
The getaway-authentication microservice is also responsible for authenticating the users. This is
done by verifying whether the login details provided by the user match a record in the database.
This record consists of the user netId, the encrypted password, and the type of user.
Gateway-authentication microservice benefits from using tokens, allowing some part of the
information to be stored on the client-side. Our service issues JWT tokens to handle
authentication. The user info is stored in the token so that when the user makes a request with
the token, the system knows what type of user it is. This information is used to categorize the
privileges of the user, specified by the rules defined. Different privileges can access different
functionalities. For example, there is some functionality embedded in our system just for
teachers/admins so a student won’t be permitted to use that functionality.
Eureka-server Microservice:
In our microservice architecture, some microservices depend on other microservices to
be functional. For example, we decided to use one microservice to make the request to our
schedule database so that we could control the requests made to that specific database. This
means that when a microservice needs to make a request to the database, it needs to make a
request to the database module first. We needed a way for microservices to communicate with
each other in a way that didn’t involve the gateway. Simply we needed to connect our
microservices internally and for this job, we chose to use a eureka server which is a service
discovery and registration server. All the microservices are registered to the eureka server so if
a microservice needs to make a request to another microservice, it can discover the searched
microservice through the eureka server.
Rules Microservice:
The Rules microservice is responsible for verifying the legality of different actions made
by the schedule-generate and the schedule-edit microservices during the generation and edit of
the schedules. These actions consist of applying a change to a classroom, course, or lecture.
Addition of a student to a lecture, checking for the regulation classroom capacity, and checking
whether each student has a chance to attend a physical lecture in the next two weeks. The
specified rules are retrieved from a JSON file, allowing for quick and simple modification. In
case of a change, the Rules microservice is also responsible for verifying the legality of all the


existing schedules with the new ruleset. The role of this microservice is to deny any changes or
insertions of the schedules that would make it not compliant with the specified ruleset.
Database Microservice:
The Database microservice is responsible for storing all the information required for
generating and modifying a schedule. From the microservices responsible for creating the
schedules, the Database microservice is the only one that has direct access to the database.
Any modification, insertion, or deletion request is forwarded and directly executed by this
microservice. It is also only this microservice that can directly retrieve any kind of data from the
database. Therefore, its other role is efficient querying of any kind of information from the
database. The role of this microservice in the overall infrastructure is the execution of requests
to the database sent by other microservices.
Schedule-edit Microservice:
The Schedule-edit microservice has multiple responsibilities, such as adding new
courses, including how many classes per week are needed, or adding newly available
classrooms to the schedule. Schedule-edit checks the user type for specific operations such as
adding a course. This is due to the fact that such an operation should only be allowed if the user
is a faculty-member. This microservice is also responsible for making the necessary changes in
case that a teacher or a student caught corona-virus. This would include removing such
students from the physical lecture or moving the lecture online in case of an infected teacher.
Schedule-generate Microservice:
The Schedule-generate microservice is responsible for calculating where the new lecture
should be put in the schedule, whenever an addition is made. Similarly, for each deletion
operation, this microservice refactors the schedule. Operations meant by refactoring are picking
a new time for a lecture, adding teachers and students to newly added lectures, etc. The
Schedule-generate microservice also checks with the Rules microservice whether the database
is still consistent with the regulation after the changes. If not, a refactoring in the database will
not be made. If a change was attempted to be made too close to an event time (48 hours prior),
the microservice will not perform any rescheduling, since this would give too little time to
students and teachers.
Viewer Microservice:
The Viewer microservice is responsible for handling requests made by users. These
requests are predominantly requests made by students and teachers asking for their schedules.
Allowing them to see the lectures, as well as the locations, for which the students and teachers
are scheduled.
Why did the development team choose the presented structure?
Using microservice architecture means dividing the whole system into flexible,
maintainable, robust, and scalable pieces. Microservice architecture increases productivity and
speed for distributed teams working simultaneously. The development would be much more
confusing and complex in a monolith system. Another benefit of this architecture is scalability if


you expect the product to grow in the near future. Our architecture uses two databases, the
Login database, and the Scheduling database. Login database is directly accessed by the
authentication microservice and contains login information. While the Scheduling database
contains the scheduling information, its division would be difficult and potentially cause more
issues than benefits. We decided not to split the data across more databases since we believe
that further division of data would be unbeneficial for our system, providing little to no speed up
and causing unnecessary consistency liability. Our architecture benefits from a Eureka server
(also known as Discovery server). Which is a server used in microservice architecture that
contains the information about other microservices. Every microservice is connected to Eureka,
in turn, the Eureka server memorizes the services running on each port. Usage of the Eureka
server allows the system to have a much simpler architecture with easier maintenance
compared to connecting everything directly to each-other. It is only the viewer and schedule-edit
microservices that are directly connected to the authentication/ gateway microservice, as these
are the only services that should be viewed (and edited) directly by the user. The other
microservices are encapsulated in a way where they are connected internally while remaining
invisible to the user.

# Design patterns

## 2.1 Observer Pattern

One of the additional features we had was that users should receive a notification
whenever a change in their schedule ensued. Since we had not added this feature yet, we
decided to implement it using the observer design pattern. ​ _“This is a behavioral design pattern
that lets you define a subscription mechanism to notify multiple objects about any events that
happen to the object they’re observing.”(Refactoring.Guru, 2020)_ Using this design pattern we
can notify users of any schedule changes.
In our codebase, we identify a subscriber and a subject. The subject is the lecture
schema in the database. Subscribers are students and teachers that are notified when a change
to their schedule happens. Following the observer pattern, we have implemented a mechanism
such that objects can subscribe to a stream of events from a publisher. We do not need to
create a class that interacts directly with the database, because spring already handles this, so
we have created the LecturePublisher class. This LecturePublisher class will be autowired into
any component that wants to add/remove subscribers and in any object that interacts with the
lecture schema. When a component wants to register a new subscriber it will call the add
subscriber method with the corresponding UserNotifier. When there is an update to the
database, the class responsible for it will now call ​ _notify_subscribers_ ​, which causes the
LecturePublisher to go over all the subscribed LectureSubscribers and call the update method.
The subscriber will then determine what to do with the update, depending on its type. We have
decided to create an abstract class (UserNotifier) that implements the LectureSubscriber
interface and automatically decides the reason the update method was called (using our
enumerated “actions” and “actors”) and it will call the corresponding abstract method which is


defined in TeacherNotifier and StudentNotifier. A good example is that when a teacher
(actor=teacher) reports that he’s sick, the lecture is going to be moved to be online
(action=lecture moved online), and therefore only students will be updated. The decision to set
UserNotifier to be abstract is in order to force implementations to be user-type-specific, in order
to make it easier in the long-term to define custom behaviors. Finally, these notifiers shall use
the appropriate API call to notify the students.

## 2.2 Builder Pattern

We decided to implement a builder pattern in the gateway authentication module to
streamline the process of parsing arguments and forwarding API calls to the other modules.
Before this pattern was implemented, the SysInteractController class, which is responsible for
taking incoming API calls and redirecting them to the appropriate microservice, receives
arguments stored in a list of undefined objects, and it was assumed that the order in which
these objects had to arrive was known by both the sender (the service making use of our API)
and the receiver (the gateway authentication module). This limited design space since
whenever a new feature is added, the specific order of arguments would need to be
communicated to all of our clients, greatly slowing down both the design and production process
of future features.
_Figure 1: The UML diagram of the observer pattern._


The builder pattern solves this issue by creating an arguments class, which can store all
of the relevant arguments for a request in specific attributes, and an argument builder
(ArgsBuilder) class, which can create an argument object and, using the arguments given by the
sender, fill this object with the information necessary for the SysInteractController to then
forward onto the other modules. The input arguments given by the sender are stored in a
Dictionary, argsDict, as you can see in fig. 2. This makes accessing the user input much more
reliable, as the system no longer relies on the client’s knowledge of how their input needs to be
parsed. As shown in fig. 2, the ArgumentsBuilder class is an implementation of the Builder
interface, which allows for similar builders to be created if the range of possible API calls, and
their accompanying arguments, necessitate it.
Several methods of the argument builder retrieve multiple arguments from the input at
once, which greatly reduces the amount of code used in the SysInteractController class
involving more complicated data types such as adding a lecture or course. This is ideal as the
primary function of the SysInteractController is to relay incoming messages to the modules who
need to receive them, and so by reducing the amount of code in its methods, each method of
the class has a much more well-defined purpose and is easier for collaborators to read and
understand the purpose of.


_Figure 2: The UML diagram of the builder pattern_

## 2.3 Proxy Pattern

Before receiving this assignment, we implemented a proxy pattern through the use of a
dedicated database microservice, to which all other microservices sent database queries,
instead of the database itself. By having all interactions with the database be handled in one
place, we can easily create and enforce rules about how the database is accessed and what
operations are allowed to be performed; by upholding these rules in the database module, it is
upheld in the entire system as well.
Using a proxy to control access to the database also makes it simpler to communicate
with the database, as developers working on other microservices do not need to understand the
schema of the database in its entirety, they just need to know what call to make to the “Proxy
server”.


# Sources:

Refactoring.Guru. (2020, January 1). Observer. https://refactoring.guru/design-patterns/observer

