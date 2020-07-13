FROM adoptopenjdk/openjdk8:latest
RUN apt-get update && \
	apt-get install net-tools && \
	apt-get install iputils-ping -y && \
	apt-get install telnet -y  
COPY /jdds-admin-server.jar /
EXPOSE 8097
CMD ["java", "-jar", "jdds-admin-server.jar"]