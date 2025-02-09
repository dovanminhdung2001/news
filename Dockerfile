# B1: build
FROM maven:3.9.9-amazoncorretto-17 AS build

# copy source code and pom.xml to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

#build source code wwith maven
RUN mvn package -DskipTests

#################################################

# B2 : crate image
FROM amazoncorretto:17

# set working folder to app and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# COMMAND TO RUN APPLICATION
ENTRYPOINT ["java", "-jar", "app.jar"]