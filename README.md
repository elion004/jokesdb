# JokesDB

A minimal application to play with JPA and spring data topics.

## 🐳 Postgres with Docker

A simple solution expects a https://www.baeldung.com/linux/docker-run-without-sudo[running docker without sudo].
To get a Database connection (and associated JPA-autocomplete), run `./gradlew bootRun` (it will hang).

Alternatively launch a postgres docker container similar to the `dockerPostgres`-Task in `build.gradle` by hand.

## 🪣 IntelliJ Database View

View | Tool Windows | Database | + | Data Source from URL
```
jdbc:postgresql://localhost:5432/localdb
User: localuser, Password: localpass
```

# Selbsteinschätzung

Dieses Projekt ist nicht gerade perfekt, es geht bestimmt besser, jedoch konnte ich von
allem ein wenig einbauen. Von Webflux-Client bis zur lokalen Datenspeicherung.

# Features
Es wurde probiert alle mindest Anforderungen Features zu implementieren. Aber nicht 
nur diese sondern auch einige erweiterte Features.

# Fokus
Der Fokus auf dem Projekt lag aufs ausprobieren, ich wollte nicht alles Perfekt haben,
nur alles schon einmal probiert und gesehen haben.