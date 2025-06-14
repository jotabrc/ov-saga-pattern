package io.github.jotabrc.ov_saga.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_saga.dto.*;
import io.github.jotabrc.ov_saga.handler.ConflictException;
import io.github.jotabrc.ov_saga.handler.NotFoundException;
import io.github.jotabrc.ov_saga.kafka.Topic;
import io.github.jotabrc.ov_saga.model.Item;
import io.github.jotabrc.ov_saga.util.EntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemSelector itemSelector;
    private final ItemExecutor itemExecutor;
    private final EntityMapper mapper;
    private final MessageService messageService;

    @Override
    @Transactional
    public String save(ItemDto dto) throws JsonProcessingException {
        boolean exists = itemSelector.exists(dto.getName(), ProcessorType.NAME);
        if (exists) throw new ConflictException("Item with NAME (%s) already exists".formatted(dto.getName()));
        Item item = mapper.toEntity(dto);
        itemExecutor.save(item);
        callKafkaProducer(item.getUuid());
        return item.getUuid();
    }

    @Override
    @Transactional
    public boolean update(ItemDtoUpdate dto) {
        Item item = getItemOrElseThrow(dto.getUuid(), ProcessorType.UUID);
        itemExecutor.update(item, dto);
        itemExecutor.save(item);
        return true;
    }

    @Override
    public ItemDtoInfo get(String uuid) {
        Item item = getItemOrElseThrow(uuid, ProcessorType.UUID);
        return mapper.toDto(item);
    }

    private Item getItemOrElseThrow(String uuid, ProcessorType processorType) {
        return itemSelector.find(uuid, processorType)
                .orElseThrow(() -> new NotFoundException("Item with UUID (%s) not found"
                        .formatted(uuid)));
    }

    @Async
    private void callKafkaProducer(String uuid) throws JsonProcessingException {
        KafkaMessageCarrier<ItemDtoKafka> dto = mapper.toCarrier(uuid, true, true, Topic.ITEM_REGISTER_EVENT);
        messageService.send(dto, Topic.ITEM_REGISTER_EVENT);
    }
}
