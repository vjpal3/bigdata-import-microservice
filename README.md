### Bigdata File Import Microservice
  - A Spring Boot service that imports data from a CSV file (about 6 million records) to a PostgreSQL database.
  - The service uses Spring Batch with Spring Data JPA-Hibernate 
  - Scaling and parellel processing is achieved through Spring Batch step partitioning and multi-threading.
  - After importing the data to the database, the job completion event-listener invokes another microservice using Spring Cloud OpenFeign, a declarative REST client, to generate reports.
  - Netflix Hystrix is used to provide fault-tolerance and fallback mechanism when making a call to reports microservice.
  - [The code for reports microservice service: ] (https://github.com/vjpal3/Bigdata-Reports-Service)