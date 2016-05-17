## A mini-webservice demonstrating Wicket and Hibernate.

##### Persists a user record containing a name, email, and role.  Validation requires unique email address, and unique name.

![Screenshot1](screenshots/screenshot001.jpg)

##### Results are paginated, filterable, and orderable by fields.

![Screenshot1](screenshots/screenshot002.jpg)

### Setup

* mvn clean compile
* make server & # Start hsqldb
* mvn jetty:run
* navigate to localhost:8080
