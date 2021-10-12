FROM openjdk:17
ENV ENVIRONMENT=prod
MAINTAINER Benedikt <a@b.de>
ADD target/app.jar app.jar
CMD ["sh", "-c", "java -jar /app.jar"]
