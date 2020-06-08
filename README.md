rest api service

## Setup local environment

Fetch sources from GitHub.
git clone https://github.com/marinap-124/rest.git

Current version of MariaDB used in local development is 10.3.23-MariaDB
Database name: db
Configure database credentials in application.properties

Clean, install and run with spring-boot:
cd rest
mvn clean install
mvn spring-boot:run

Use postman for testing

Loads json from THL, parsers data and inserts to database.
http://localhost:8080/init/data

Load areas from db
http://localhost:8080/area/all

Load area by code
http://localhost:8080/area/{areacode}

Load corona cases by area code
http://localhost:8080/corona/area/{areacode}