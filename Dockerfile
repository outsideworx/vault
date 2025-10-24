FROM openjdk:21
COPY target/*.jar vault.jar

EXPOSE 80 443
CMD ["java", "-jar", "vault.jar"]