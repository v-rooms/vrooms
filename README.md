# vrooms
VRooms RESTful web-service

## Prerequisites

- OpenJDK 14 or higher
- Docker 19.03.8 or higher
- Docker Compose 1.25.4 or higher

## Build

### JAR
```shell script
$ ./mvnw package
```

### Docker
If you want to run the app by docker you have to build it the image before.

```shell script
$ ./mvnw verify
```

## Run

### JAR
You need `Open JDK 14` or higher.

```shell script
$ java -jar target/vrooms-0.0.1-SNAPSHOT.jar 
```
### Docker
You need `19.03.8` or higher.

```shell script
$ docker run -ti -p 8443:8443 -e DB_HOST=<host_name> \ 
    -e GOOLE_CLIENT_ID=<id> -e GOOLE_CLIENT_SECRET=<secret> aukhatov/vrooms:0.0.1-SNAPSHOT
```

### Docker Compose
You need `1.25.4` or higher.
The docker-compose.yaml file located in root directory.

> NOTE: You have to put `GOOLE_CLIENT_ID` and `GOOLE_CLIENT_SECRET` variables in docker-compose.yaml

```shell script
$ docker-compose up
```

## API

- `https://localhost:8443/api-docs` - OpenAPIv3 Spec
- `https://localhost:8443/swagger-ui.html` - Swagger UI
