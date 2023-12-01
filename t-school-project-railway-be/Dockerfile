FROM openjdk:21
COPY ./target/t-school-project-railway-be-0.0.1-SNAPSHOT.jar ./app/app.jar
EXPOSE 8080
ENTRYPOINT java --add-opens java.base/java.lang=ALL-UNNAMED -jar ./app/app.jar