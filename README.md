# Agile Engine Challenge

## Technologies
- Kotlin
- VertX
- Guice
- JUnit5
- RxJava

## Project Structure
The project architecture is based on 'Clean Architecture' and is divided in four main layers:
- Core: This layer contains the business logic. 
- Infrastructure: In this layer you will find the configurations and utility classes.
- Entrypoint: This layer acts as a facade through which clients will interact with the use cases.
- Repository: This layer has the implementations that allow use cases to interact with external resources.

## Environment
To run the application locally you can use the next command:
`./gradlew run`
