package ru.zhenik.example.schema.plugin.client1;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import ru.zhenik.example.schema.avro.v1.Event;

public class Application {
  public static void main(String[] args)
      throws InterruptedException, ExecutionException, TimeoutException {
    final Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "client1-id");
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put("schema.registry.url", "http://localhost:8081");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put(ProducerConfig.RETRIES_CONFIG, 0);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

    final KafkaProducer<String, Event> eventKafkaProducer = new KafkaProducer<>(props);

    final String id = UUID.randomUUID().toString();
    final Future<RecordMetadata> futureMetadata = eventKafkaProducer.send(
        new ProducerRecord<>(
            "event-topic",
            id,
            Event.newBuilder().setId(id).setDescription("some description").build())
    );

    final RecordMetadata recordMetadata = futureMetadata.get(5, TimeUnit.SECONDS);
    System.out.println(recordMetadata);
  }
}
