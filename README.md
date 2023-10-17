# GigaCal

Amazing project.

# Setup a database
```
docker pull postgres
docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
docker exec -it postgres bash
psql -U postgres
CREATE DATABASE test;
\l
```
