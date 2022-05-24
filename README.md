# Texo Movies - API General Purpose
RESTful API created for the purpose of custom listing the winners of the Worst Film category of the Golden Raspberry Awards.

## Main Resources Used
* Spring Boot and Tomcat as an application server;
* Maven for dependency management;
* The available REST call is framed in Level 2 of Leonard Richardson's maturity model;
* Responses are provided in JSON format;
* The application has some DTOs (Data Transfer Object) used for response processing and communication;
* The lombok library was used to increase practicality and reduce the amount of code.
* The H2 database was used to store the information. It is worth mentioning that this database works based on the computer's memory and therefore data is lost when the application ends.
* As requested when starting the application, some information contained in a CSV file is automatically loaded.
* To get more precision and dynamism, the tests were made with a 'clean' database where specific records are inserted for each scenario.

## Important note about the requirement below:
<i>"Get the producer with the longest gap between two consecutive awards, and the one with the fastest two awards."</i>

To solve this requirement, this API takes each of the producers even if the film was produced together, example:

* Producer John was nominated alone in the year 2000 and the following year he is nominated again, but this time the film was produced in conjunction with producer Bruce.

In this way this API will consider that John received <b>two indications</b> and will process the result based on that premise, <b>exactly</b> as requested in the requirement.

## importing project
* Open/import the project as a maven project
* Import all pom.xml dependencies
* Run the project normally
  * If you need to do this manually, just do it in the class: 
```java
  com.texoit.movies.MoviesApplication.java
  ```

## Endpoints
Only one endpoint was created that returns the highest and lowest range of a winning producer (as required). 

HTTP GET:
```http request
http://localhost:8080/movies/award-intervals
```
Returns the producer with the longest gap between two consecutive awards, and the one with the fastest two awards.
Example of response:

```JSON
{
  "min":[
    {
      "producer":"Joel Silver",
      "interval":1,
      "previousWin":1990,
      "followingWin":1991
    }
  ],
  "max":[
    {
      "producer":"Matthew Vaughn",
      "interval":13,
      "previousWin":2002,
      "followingWin":2015
    }
  ]
}
```

## Initial Load
Initial loading is done via the file in:
```bash
src/main/resources/static/movielist.csv
```

If it is necessary to load other data, just replace the contents of this file and they will be automatically loaded when the system is executed.

* It is worth remembering that the previously established structure must be respected according to the line below:

     <b>year;title;studios;producers;winner</b>

## Integration tests
The integration tests were made with the objective that all layers of the application were validated, to do that, was decided to use a clean database (without information) and all data would be created and processed according to each test scenario. 

That is, mockups (fake data) were <b>NOT</b> used.

Each test produces its data, writes it to the database and from there, the endpoint is called, which will go through all the layers of the system (controller, service, repository and DB) and return the result.
And then, finally, based on this result, the validations are carried out.

All tests are available in the file:
```java
src.test.java.com.texoit.movies.controller.MovieControllerTest.java
```

## License
[MIT](https://choosealicense.com/licenses/mit/)