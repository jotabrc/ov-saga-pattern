package io.github.jotabrc.ov_saga.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_saga.service.MessageService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class KafkaEventConsumer {

    private final MessageService messageService;

    @KafkaListener(topics = {Topic.ITEM_REGISTER_EVENT_REPLY},
            containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1500, multiplier = 1.5)
    )
    public void listener(ConsumerRecord<String, String> record) throws JsonProcessingException {
        messageService.consumer(record);
    }
}
