## Features API

### Setup
* Java 8
* Spring Boot 2.4.2
* Maven

### Overview
Features API provides endpoints to get Features along with their images.
The data source is static and represented by a json file `data.json`.
Once the service is started, the data is being consumed and located in the memory.

### Usage
The service provides following endpoints:
* Get list of Features:
  - Request: `GET /api/v1/features`
  - Response:
  ```
    [
        {
            "id": "cf5dbe37-ab95-4af1-97ad-2637aec4ddf0",
            "timestamp": 1556904743783,
            "beginViewingDate": 1556904743783,
            "endViewingDate": 1556904768781,
            "missionName": "Sentinel-1"
        },
        {
            "id": "ca81d759-0b8c-4b3f-a00a-0908a3ddd655",
            "timestamp": 1558155123786,
            "beginViewingDate": 1558155123786,
            "endViewingDate": 1558155148785,
            "missionName": "Sentinel-1"
        }
  ]

* Get Feature by id
  - Request: `GET /api/v1/features/{id}`
  - Response:
  ```
    {
        "id": "ca81d759-0b8c-4b3f-a00a-0908a3ddd655",
        "timestamp": 1558155123786,
        "beginViewingDate": 1558155123786,
        "endViewingDate": 1558155148785,
        "missionName": "Sentinel-1"
    }

* Get Feature quicklook
    - Request: `GET /api/v1/features/{id}/quicklook`
    - Response: `image/png`
    
### Running test locally
`mvn clean test`

### Running docker image locally
`docker build -t features-service .` The project will be build, tested and docker image will be created

`docker run -p 8080:8080 features-service` Run project locally on `8080` port