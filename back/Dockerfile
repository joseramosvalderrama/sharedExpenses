# Imagen base para el contenedor de compilación
FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /project
COPY /src /project/src
COPY pom.xml /project/
RUN mvn -B package -DskipTests
# Imagen base para el contenedor de la aplicación
FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app/
COPY --from=builder /project/target/*.jar /usr/src/app/
EXPOSE 8080
CMD [ "java", "-jar", "sharedExpenses-1.0.0.jar" ]