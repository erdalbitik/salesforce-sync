# Salesforce data sync demo:

## Tech Stack

- Java
- Quarkus Framework
- Spring Data
- Quartz

## Requirements

To compile and run this demo you will need:

- JDK 1.8+
- Postgres


### Configuring JDK 1.8+

Make sure that `JAVA_HOME` environment variables have
been set, and that a JDK 1.8+ `java` command is on the path.

## Building the demo

Launch the Maven build on the checked out sources of this demo:

> ./mvnw install

## Running the demo

### Prepare a PostgreSQL instance

Make sure you have a PostgreSQL instance running. To set up a PostgreSQL database with Docker:

> docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name sf_sync_postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -p 5432:5432 postgres:11.5


### Run App in developer mode

> ./mvnw compile quarkus:dev

This command will leave App running in the foreground listening on port 8080.


### Run App in JVM mode

When you're done iterating in developer mode, you can run the application as a
conventional jar file.

First compile it:

> ./mvnw install

Then run it:

> java -jar ./target/salesforce-sync-1.0-SNAPSHOT-runner.jar

## Prepare Salesforce Account

Create a free Salesforce Developer Account at https://developer.salesforce.com/signup

To populate test data in your Salesforce, it is recommended to use https://appexchange.salesforce.com/listingDetail?listingId=a0N3A00000EO5smUAD

Create a security token: Settings --> My Personal Information --> Reset My Security Token. 


## Test Salesforce Data Sync App

First, you need to register your salesforce account to sync application.

> curl -d '{ "label": "Account label", "username": "your_login_username", "password": "your_login_pass_and_token", "syncPeriod": 30 }' -H "Content-Type: application/json" -X POST http://localhost:8080/account

- password is a combination of your login password and security token. lets say your login password: pass123 and your security token: blahh, then you need to use pass123blahh as api password.
- syncPeriod is in seconds

Data sync process will run immediately and every 30 seconds.

Get synced data:

> curl http://localhost:8080/salesforce/{objectId}




