from java:openjdk-8-jre

ARG MONGO_URL
ARG MONGO_USER
ARG MONGO_PASS

COPY target/*.jar app.jar

EXPOSE 8080

ENV MONGO_URL $MONGO_URL
ENV MONGO_USER $MONGO_USER
ENV MONGO_PASS $MONGO_PASS

CMD java -jar app.jar --spring.data.mongodb.uri=mongodb://$MONGO_USER:$MONGO_PASS@$MONGO_URL/mep