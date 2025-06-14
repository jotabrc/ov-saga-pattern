package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.*;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public Item toEntity(String name) {
        return Item
                .builder()
                .name(name)
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public Item toEntity(ItemDto dto) {
        return toEntity(dto.getName());
    }

    @Override
    public Item toEntity(ItemDtoUpdate dto) {
        return toEntity(dto.getName());
    }

    @Override
    public ItemDtoInfo toDto(Item item) {
        return new ItemDtoInfo(
                item.getUuid(),
                item.getName(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }

    @Override
    public ItemDto toDto(String uuid) {
        return new ItemDto(uuid);
    }

    @Override
    public KafkaMessageCarrier<ItemDtoKafka> toCarrier(String uuid, boolean requestReply, boolean senderSuccess, String replyingTopic) {
        return new KafkaMessageCarrier<>(requestReply, senderSuccess, new ItemDtoKafka(uuid), replyingTopic);
    }
}
