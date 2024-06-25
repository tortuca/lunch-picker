# Lunch Picker

A lunch picker - to collectively decide on a location to eat for lunch.


## Requirements
1. User can initiate a session and invite others to join.
2. Other users who have joined the session may submit a restaurant of their choice.
3. All users in the session are able to see restaurants that others have submitted.
4. The user who initiated the session is able to end the session.
   a. At the end of the session, a restaurant is randomly picked from all submitted restaurants.
   b. All users in the session are then able to see the picked restaurant.
   c. A user should not be able to join a session that has already ended.


## Design Decisions

API
1. Open lunch plan
2. Add venue
3. View venues
4. Close session

UI
1. Mobile friendly (responsive)

Scope
1. Session codename?
2. Notification to see picked restaurant

Engineering Qualities
1. How would you ensure that one user's submitted location does not cause issues on other users' displays?
2. How would you assure others that your application meets the requirements, and continues to meet the requirements even after changes have been made to it?
3. How would you ensure that your application can be deployed to serve an increasing number of users who may be concurrently using your service?
4. How would you help a fellow team member who may need to debug your application in future?

## API Specifications

### Get Stored Readings

Endpoint

```text
GET /readings/read/<smartMeterId>
```

Parameters

| Parameter      | Description                              |
| -------------- | ---------------------------------------- |
| `smartMeterId` | One of the smart meters' id listed above |

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/readings/read/smart-meter-0"
```

Example output

```json
[
  {
    "time": "2020-11-29T08:00:00Z",
    "reading": 0.0503
  },
  {
    "time": "2020-11-29T08:01:00Z",
    "reading": 0.0621
  },
  {
    "time": "2020-11-29T08:02:00Z",
    "reading": 0.0222
  },
  {
    "time": "2020-11-29T08:03:00Z",
    "reading": 0.0423
  },
  {
    "time": "2020-11-29T08:04:00Z",
    "reading": 0.0191
  }
]
```


## Useful Gradle commands

The project makes use of Gradle and uses the Gradle wrapper to help you out carrying some common tasks such as building
the project or running it.

### List all Gradle tasks

List all the tasks that Gradle can do, such as `build` and `test`.

```console
$ ./gradlew tasks
```

### Build the project

Compiles the project, runs the test and then creates an executable JAR file

```console
$ ./gradlew build
```

Run the application using Java and the executable JAR file produced by the Gradle `build` task. The application will be
listening to port `8080`.

```console
$ java -jar build/libs/lunch-picker-0.0.1-SNAPSHOT.jar
```

### Run the tests

- Run unit tests only

  ```console
  $ ./gradlew test
  ```

### Run the application

Run the application which will be listening on port `8080`.

```console
$ ./gradlew bootRun
```

### Build container
```
docker build -t lunch-picker .
```

### Run container
```
docker run -it -p 8080:8080 lunch-picker
```