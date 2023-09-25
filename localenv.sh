export PGPASSWORD="secret"

docker stop zookeeper kafka niffler-all

docker rm zookeeper kafka niffler-all

docker volume rm niffler-st3

docker volume create niffler-st3

docker run --name niffler-all -p 5432:5432 -e POSTGRES_PASSWORD=secret -v niffler-st3:/var/lib/postgresql/data -d postgres:15.1

docker run --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 -d confluentinc/cp-zookeeper:7.5.0

docker run --restart always -d --name=kafka -e KAFKA_BROKER_ID=1 \
-e KAFKA_ZOOKEEPER_CONNECT=$(docker inspect zookeeper --format='{{ .NetworkSettings.IPAddress }}'):2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
-e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
-e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 \
-e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 \
-p 9092:9092 -d confluentinc/cp-kafka:7.5.0

sleep 10

psql -h localhost -p 5432 -U postgres -d postgres -c 'CREATE DATABASE "niffler-userdata" WITH OWNER postgres;'
psql -h localhost -p 5432 -U postgres -d postgres -c 'CREATE DATABASE "niffler-spend" WITH OWNER postgres;'
psql -h localhost -p 5432 -U postgres -d postgres -c 'CREATE DATABASE "niffler-currency" WITH OWNER postgres;'
psql -h localhost -p 5432 -U postgres -d postgres -c 'CREATE DATABASE "niffler-auth" WITH OWNER postgres;'
psql -h localhost -p 5432 -U postgres -d postgres -c 'CREATE DATABASE "geoservice" WITH OWNER postgres;'


unset PGPASSWORD

cd /home/user/IdeaProjects/niffler-st3/niffler-frontend/
npm run build:dev