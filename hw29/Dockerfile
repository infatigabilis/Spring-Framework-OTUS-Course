FROM maven:3.5.4-jdk-8-alpine

COPY . /home/app/

WORKDIR /home/app

CMD ["mvn", "spring-boot:run",  "-Dspring-boot.run.profiles=prod"]

EXPOSE 8080
