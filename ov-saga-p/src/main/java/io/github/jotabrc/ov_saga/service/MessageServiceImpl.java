package io.github.jotabrc.ov_saga.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_saga.dto.ItemDtoKafka;
import io.github.jotabrc.ov_saga.dto.KafkaMessageCarrier;
import io.github.jotabrc.ov_saga.kafka.KafkaEventProducer;
import io.github.jotabrc.ov_saga.service.messaging.TopicSelector;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final KafkaEventProducer kafkaEventProducer;
    private final TopicSelector topicSelector;
    private final ObjectMapper objectMapper;

    @Override @Async
    public <T> void send(KafkaMessageCarrier<T> t, String topic) throws JsonProcessingException {
        kafkaEventProducer.produce(t, topic);
    }

    @Override @Async
    public void consumer(ConsumerRecord<String, String> record) throws JsonProcessingException {
        KafkaMessageCarrier<ItemDtoKafka> carrier = mapObjectToConsume(record);
        reactToEvent(carrier);
    }

    private KafkaMessageCarrier<ItemDtoKafka> mapObjectToConsume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        return objectMapper.readValue(
                record.value(),
                new TypeReference<>() {
                }
        );
    }

    private void reactToEvent(KafkaMessageCarrier<ItemDtoKafka> carrier) throws JsonProcessingException {
        if (carrier.isRequestReply()) {
            if (!carrier.isSenderOperationSuccess()) {
                String topic = topicSelector.select(carrier.getReplyingTopic());
                send(carrier, topic);
            }
        }
    }
}
