# Extend vert.x image
FROM mongo


COPY /mongo_seed/init_user.json/ init_user.json
COPY /mongo_seed/init_item.json/ init_item.json

CMD mongoimport --host mongodb --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin --db $MONGO_INITDB_DATABASE --collection user --type json --drop  --file /init_user.json --jsonArray -v && \
    mongoimport --host mongodb --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin --db $MONGO_INITDB_DATABASE --collection item --type json --drop  --file /init_item.json --jsonArray -v


# FROM openjdk

# EXPOSE 8080

# VOLUME /app
# ADD  keystore.jceks keystore.jceks
# ADD  /target/mikroserwis-1.0.0-SNAPSHOT-fat.jar app.jar

# CMD ["java", "-jar","/app.jar"]


