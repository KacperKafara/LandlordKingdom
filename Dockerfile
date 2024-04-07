FROM maven:3.9.6-amazoncorretto-21 as BUILD
WORKDIR /app
COPY . .
RUN mvn clean package

FROM tomcat:10.1.20-jdk21
COPY --from=BUILD /app/target/*.war /usr/local/tomcat/webapps/ssbd02.war