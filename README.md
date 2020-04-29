# vrooms
VRooms RESTful web-service

## Prerequisites

- Mongo DB
- OpenVidu Media Server

## Build

### JAR
```shell script
$ ./mvnw package
```

### Docker
```shell script
$ ./mvnw verify
```

## Run

```shell script
$ java -jar target/vrooms-0.0.1-SNAPSHOT.jar 
```

## API

- `https://localhost:8443/api-docs` - OpenAPIv3 Spec
- `https://localhost:8443/swagger-ui.html` - Swagger UI