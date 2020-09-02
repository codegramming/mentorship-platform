# Mentorship Platform

Mentorship Platform allows to establish relationships between mentors and    mentees and to manage their processes.

  - ***Dockerized*** Application [***Sprint Boot, React, PostgreSQL, Elasticsearch*** with Docker-Compose]
  - Rol based Authentication and Authorization with ***JSON Web Token***.
  - Google Authentication with ***OAuth2***
  - Free Text Search with ***Elastichsearch***.
  - ***Sending scheduled email*** with Quartz
  - ***CRUD operations*** for mentors and mentees.
  - Application Control ***Panel for Admin***.
  - ***Secured routes*** both by client and server.

## Technologies
Mentorship Platform uses a number of open source projects to work properly:

- [Java](https://www.java.com)
- [Spring Framework](https://spring.io/) (Spring Boot, Spring Security, Spring Data JPA)
- [React](https://reactjs.org/)
- [Docker](https://www.docker.com/)
- [OAuth2](https://oauth.net/2/)
- [PostgreSQL](https://www.postgresql.org/)
- [Elasticsearch](https://www.elastic.co/) -- Free text search
- [Quartz Job Scheduler](http://www.quartz-scheduler.org/) -- Sending scheduled email

## Installation
All parts of the application run on the docker. When you use docker-compose, spring boot, react, postgres and elasticsearch will run.

### Build image
```sh
$ docker-compose build
```

### Run Containers
```sh
$ docker-compose up
```

## API Documentation
Created API Documentation with ***Swagger***

![image](https://i.imgur.com/KgMxIxo.png)

## Screenshots
***Login Page*** for ***registered user*** and ***Google Authentication***
![image](https://i.imgur.com/dhSYiwo.png)

***List of Mentors***
![image](https://i.imgur.com/VWKQv2l.png)

***Application Page for Mentor***
![image](https://i.imgur.com/9PN2GOG.png)

***Admin Panel***
![image](https://i.imgur.com/Wuu3BSW.png)
