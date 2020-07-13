FROM adoptopenjdk/openjdk8:latest
RUN apt-get update && \
	apt-get install net-tools && \
	apt-get install iputils-ping -y && \
	apt-get install telnet -y  
COPY /jdds-authCenter.jar /
EXPOSE 8087
CMD ["java", "-jar", "jdds-authCenter.jar"]