
# SPRING BOOT REST API WITH JWT

Simple secure rest api starter to ability developers to focus business logic.

## Stack
- spring-boot 2.7.3
- spring-boot-data-mongodb
- spring-boot-starter-security
- spring-boot-starter-oauth2-resource-server
- springdoc-openapi-ui
- mapstruct, lombok

## Installation

```bash
$ mvn clean package
```

## Running the app

*private key under resources/certs/private.pem created for demo purpose. Do not use for production.*

application.yaml
```
auth:
  jwt:
    accessTokenLifetimeSeconds: 3600
    refreshTokenLifetimeSeconds: 1209600
    publicKey: classpath:certs/public.pem
    privateKey: classpath:certs/private.pem
    admin:
      enabled: true
      email: admin@example.com
      password: password

spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/test

springdoc:
  default-produces-media-type: application/json
  
```

```bash
$ spring-boot:run
```
