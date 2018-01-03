package com.andrearota.kafkademo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class JavaSyncConsumerDemo {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final String TOPIC_NAME = "demo_topic";
    private static final String CONSUMER_GROUP = "demo_group_1";

    public static void main(String[] args) {

        Properties p = new Properties();
        p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        p.setProperty(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        p.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        p.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer<Integer, String> consumer = null;

        try {
            consumer = new KafkaConsumer<Integer, String>(p);
            consumer.subscribe(Arrays.asList(TOPIC_NAME));

            while (true) {

                ConsumerRecords<Integer, String> records = consumer.poll(100);

                for (ConsumerRecord<Integer, String> r : records) {
                    System.out.println("topic: " + r.topic() +
                            ", partition: " + r.partition() +
                            ", key: " + r.key() +
                            ", value: " + r.value());
                }
            }
        } finally {
            if (consumer != null) consumer.close();
        }

    }


}
