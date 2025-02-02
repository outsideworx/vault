FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

RUN apt update
RUN apt install maven -y

COPY . .
CMD ["mvn", "spring-boot:run"]