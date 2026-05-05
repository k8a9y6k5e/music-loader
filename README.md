## Description

A music loader, which work as a simple music manager, the project use an implementation of spotify API and invidious API to search music with an orchestrator to manage the fallback of them.

## What i want to do

I will stop the project for a time, but, when I come back, I want to improve the clients manager, add an auth, add a playlist manager, and a recomentation system(using an AI api).

## Technologies

- Java
- Spring Boot
- JPA
- MySQL
- Spotify API
- Invidious API
- Junit
- Jakarta
- Lombok

## Routes

### Music (/music)

| Method | Route | Description | Body example | query(s) |
| --- | --- | --- | --- | --- |
| POST | --- | Save the selected music | { name: string, track:(music index) } | --- |
| GET | --- | Search the musics inside the clients | --- | name |
| GET | /saved | List all musics saved | --- | page, size, sort |
| GET | /:id | Search a saved music | --- | --- |
| PUT | /:id | Update all informations from a music | { name: string } | --- |
| PATCH | /:id | Update a few informations from a saved music | { name: string(optional), research: boolean(optional) } | --- |
| DELETE | /:id | Delete one saved music | --- | --- |

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL

### Environment variables

Create an application.properties file in src/main/resources/ and fill in the following:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port=8080
spotify.client.id=your_spotify_client_id
spotify.client.secret=your_spotify_client_secret
```
  
## Running
```bash
git clone https://github.com/k8a9y6k5e/music-loader
cd music-loader
mvn spring-boot:run
```
