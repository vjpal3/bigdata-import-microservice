### Bigdata File Import Microservice
  - A Spring Boot service that imports data from a CSV file (about 6 million records) to a PostgreSQL database.
  - The service uses Spring Batch with Spring Data JPA-Hibernate 
  - Scaling and parellel processing is achieved through Spring Batch MultiResourcePartitioner.
  - Large volume of data is processed using asynchronous job-launcher for non-blocking IO.
  - After importing the data to the database, the job completion event-listener invokes another microservice using Spring Cloud OpenFeign, a declarative REST client, to generate reports.
  - Netflix Hystrix is used to provide fault-tolerance and fallback mechanism when making a call to reports microservice.
  - [Reports microservice - Repo](https://github.com/vjpal3/Bigdata-Reports-Service)
  - Based on this data, a fraud detection model is built using python machine learning libraries. 
  - [Python fraud-detection microservice - Repo](https://github.com/vjpal3/Fraud-Detection-PythonML-Service)
  - [Other microservices in this architecture](https://github.com/vjpal3/Bigdata-Microservices-Spring-Cloud-Repos)

  ### Built with:
  - Java
  - Spring Boot
  - Spring Batch
  - Spring Data JPA-Hibernate
  - PostgreSQL
  - Spring cloud OpenFeign
  - Netflix Hystrix
  - REST API
