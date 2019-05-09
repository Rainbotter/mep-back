from java:openjdk-8-jre

COPY target/*.jar app.jar

EXPOSE 8080

ENV MONGO_URL ""
ENV MONGO_USER ""
ENV MONGO_PASS ""
ENV DATABASE_NAME ""

CMD java -jar app.jar --spring.data.mongodb.uri=mongodb://$MONGO_USER:$MONGO_PASS@$MONGO_URL/$DATABASE_NAME
