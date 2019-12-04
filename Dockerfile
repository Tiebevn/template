#
# Base image for building
#
FROM maven:3.6.0-jdk-8-slim as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Move package to production image
#
FROM tomcat:9-jdk8-slim
RUN ["rm", "-rf", "/usr/local/tomcat/webapps/ROOT"]
COPY --from=build /home/app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]