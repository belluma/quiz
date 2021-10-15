FROM openjdk:17
ENV ENVIRONMENT=prod
#ENV SPRING_DATASOURCE_URL=    postgres://blstxamhgpimjb:fc047e78c6ff232ceb70b80ff50d633fd3132d87420edabdc18b1ae998bc4cca@ec2-54-195-141-170.eu-west-1.compute.amazonaws.com:5432/d9h8e0c7mb8cod
#ENV SPRING_DATASOURCE_USERNAME=blstxamhgpimjb
#ENV SPRING_DATASOURCE_PASSWORD=fc047e78c6ff232ceb70b80ff50d633fd3132d87420edabdc18b1ae998bc4cca
MAINTAINER Benedikt <a@b.de>
ADD target/app.jar app.jar
CMD ["sh", "-c", "java -Dxerver.port=$PORT -jar /app.jar"]
