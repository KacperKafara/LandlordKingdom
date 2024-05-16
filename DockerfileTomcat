FROM maven:3.9.6-amazoncorretto-21 as BUILD
WORKDIR /app
COPY pom.xml pom.xml
RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip && rm -rf target/
COPY src src
RUN mvn clean package -Dmaven.test.skip

FROM tomcat:10.1.20-jdk21
COPY --from=BUILD /app/target/*.war /usr/local/tomcat/webapps/ssbd02.war