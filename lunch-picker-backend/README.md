# Lunch Picker

A lunch picker - to collectively decide on a location to eat for lunch.

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
$ java -jar build/libs/lunch-picker-1.0.0.jar
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