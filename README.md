# Kafka Java Demo

This demo is based on exercises shown in the
[Apache Kafka Series - Learn Apache Kafka for Beginners](https://www.udemy.com/apache-kafka-series-kafka-from-beginner-to-intermediate) course on Udemy.

The code is set to connect to the Docker container included in the course material and provided by [Landoop](http://www.landoop.com/).

To start the Docker container with the prepared Kafka cluster, run:

    docker run --rm -it \
           -p 2181:2181 -p 3030:3030 -p 8081:8081 \
           -p 8082:8082 -p 8083:8083 -p 9092:9092 \
           -e ADV_HOST=127.0.0.1 \
           landoop/fast-data-dev

The topic **demo_topic** should be created before running the code:

    docker run --rm -it --net=host landoop/fast-data-dev bash
    root@fast-data-dev / $ kafka-topics --zookeeper 127.0.0.1:2181 --create --topic demo_topic --partitions 3 --replication-factor 1

## Implementations

-   Java synchronous Kafka producer: send 10 KV messages to the topic demo_topic
-   Java synchronous Kafka consumer: polls every 100 ms the cluster to check for incoming messages on the topic

You can launch more than a consumer and more than a producer instance. Consumers are grouped in a consumer group, to show how Kafka can scale up easily.
