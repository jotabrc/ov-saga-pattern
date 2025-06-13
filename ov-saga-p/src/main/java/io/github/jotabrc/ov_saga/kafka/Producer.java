package io.github.jotabrc.ov_saga.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Async
    public <T> void produce(T t, String topic) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(t);
        kafkaTemplate.send(topic, json);
    }
}
