## Description

A music loader, which work as a simple music manager, the project use an implementation of spotify API and invidious API to search music with an orchestrator to manage the fallback of them.

## What i want to do

I will stop the project for a time, but, when I come back, I want to improve the clients manager, add an auth, add a playlist manager, and a recomentation system(using an AI api).

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
