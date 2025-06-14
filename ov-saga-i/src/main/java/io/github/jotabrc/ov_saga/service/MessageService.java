package io.github.jotabrc.ov_saga.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_saga.dto.KafkaMessageCarrier;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface MessageService {

    <T> void send(KafkaMessageCarrier<T> t, String topic) throws JsonProcessingException;
    void consumer(ConsumerRecord<String, String> record) throws JsonProcessingException;
}
