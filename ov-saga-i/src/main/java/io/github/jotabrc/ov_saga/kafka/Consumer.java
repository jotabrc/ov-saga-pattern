package io.github.jotabrc.ov_saga.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class Consumer {

    @KafkaListener(topics = {"ITEM_REGISTER_EVENT"},
            containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1500, multiplier = 1.5)
    )
    public void listener(ConsumerRecord<String, String> record) throws JsonProcessingException {

    }
}
