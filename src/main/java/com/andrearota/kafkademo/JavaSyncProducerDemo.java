package com.andrearota.kafkademo;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class JavaSyncProducerDemo {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final String TOPIC_NAME = "demo_topic";

    public static void main(String[] args) {

        Properties p = new Properties();
        p.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        p.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        p.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<Integer, String> producer = null;

        try {

            producer = new KafkaProducer<Integer, String>(p);

            for (int k = 0; k < 10; k++) {
                ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>(TOPIC_NAME, k, "Value of message with key " + k);
                producer.send(record).get();
                System.out.println("Message " + k + " sent");
            }

        } catch (InterruptedException ie) {
            // Be a good JVM citizen...
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            ee.printStackTrace();
        } finally {

            if (producer != null) {
                // Is producer .flush() a must?
                // https://github.com/confluentinc/confluent-kafka-python/issues/137
                producer.flush();
                producer.close();
            }
        }

    }


}
