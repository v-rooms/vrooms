# vrooms
VRooms RESTful web-service

## Prerequisites

- OpenJDK v14 or higher
- Docker v19.03.8 or higher
- Docker Compose v1.25.4 or higher

## Build

### JAR
```shell script
$ ./gradlew build
```

### Docker
If you want to run the app by the docker you have to build it the image before.

```shell script
$ ./gradlew buildImage
```

## Configure CORS

### JAR
Pass the arg `--cors.allowed.origins=http://origin.example.com`

> Please, replace by your origin host and protocol.

### Docker Compose
Put the environment var `CORS_ALLOWED_ORIGINS:http://origin.example.com` in `vrooms` seervice.

> Please, replace by your origin host and protocol.

## Run

### JAR
You need `Open JDK v14` or higher.

```shell script
$ java -jar target/vrooms-0.0.1-SNAPSHOT.jar 
```
### Docker
You need `v19.03.8` or higher.

```shell script
$ docker run -ti -p 8443:8443 -e DB_HOST=<host_name> \ 
    -e GOOLE_CLIENT_ID=<id> -e GOOLE_CLIENT_SECRET=<secret> aukhatov/vrooms:0.0.1-SNAPSHOT
```

### Docker Compose
You need `v1.25.4` or higher.
The docker-compose.yaml file located in root directory.

> NOTE: You have to put `GOOLE_CLIENT_ID` and `GOOLE_CLIENT_SECRET` variables in docker-compose.yaml

```shell script
$ docker-compose up
```

## API

- `https://localhost:8443/api-docs` - OpenAPIv3 Spec
- `https://localhost:8443/swagger-ui.html` - Swagger UI
