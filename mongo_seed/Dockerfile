FROM mongo


COPY init_user.json/ init_user.json
COPY init_item.json/ init_item.json

CMD mongoimport --host mongodb --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin --db $MONGO_INITDB_DATABASE --collection user --type json --drop  --file /init_user.json --jsonArray -v && \
    mongoimport --host mongodb --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin --db $MONGO_INITDB_DATABASE --collection item --type json --drop  --file /init_item.json --jsonArray -v