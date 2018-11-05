## Run

Local:

```
// run postgres db
docker run --name psql -p 5432:5432 -e POSTGRES_DB=test -e POSTGRES_PASSWORD=secret -d postgres:alpine

// run app
mvn spring-boot:run
```

Prod:
```
// build images
docker-compose build

// run (please wait, it takes a certain amount of time to download every java third-party libraries)
docker-compose up -d
```

## Utils:
```
// stop and remove all containers
docker rm -f $(docker ps -aq)
```