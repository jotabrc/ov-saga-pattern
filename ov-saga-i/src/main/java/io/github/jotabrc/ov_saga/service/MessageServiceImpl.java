package io.github.jotabrc.ov_saga.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.dto.ItemDtoKafka;
import io.github.jotabrc.ov_saga.dto.ItemDtoKafkaReply;
import io.github.jotabrc.ov_saga.dto.KafkaMessageCarrier;
import io.github.jotabrc.ov_saga.handler.ConflictException;
import io.github.jotabrc.ov_saga.kafka.KafkaEventProducer;
import io.github.jotabrc.ov_saga.kafka.Topic;
import io.github.jotabrc.ov_saga.service.messaging.TopicSelector;
import io.github.jotabrc.ov_saga.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final KafkaEventProducer kafkaEventProducer;
    private final ObjectMapper objectMapper;
    private final EntityMapper entityMapper;
    private final ItemService itemService;
    private final TopicSelector topicSelector;

    @Override
    public <T> void send(KafkaMessageCarrier<T> t, String topic) throws JsonProcessingException {
        kafkaEventProducer.produce(t, topic);
    }

    @Override @Async
    public void consumer(ConsumerRecord<String, String> record) throws JsonProcessingException {
        ItemDto dto = mapObjectToConsume(record);
        try {
            itemService.save(dto);
        } catch (ConflictException e) {
            String topic = topicSelector.select(record.topic());
            KafkaMessageCarrier<ItemDtoKafkaReply> carrier =
                    mapObjectToReply(e.getMessage(), true, false, true, topic);
            kafkaEventProducer.produce(carrier, Topic.ITEM_REGISTER_EVENT_REPLY);
        }
    }

    private ItemDto mapObjectToConsume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        KafkaMessageCarrier<ItemDtoKafka> carrier = objectMapper.readValue(
                record.value(),
                new TypeReference<>() {
                }
        );
        return entityMapper.toDto(carrier.getContent().getUuid());
    }

    private KafkaMessageCarrier<ItemDtoKafkaReply> mapObjectToReply(
            String message,
            boolean conflict,
            boolean requestReply,
            boolean success,
            String replyingTopic
    ) {
        ItemDtoKafkaReply itemDtoKafkaReply = entityMapper.toDto(message, conflict);
        return entityMapper.toDto(itemDtoKafkaReply, requestReply, success, replyingTopic);
    }
}
