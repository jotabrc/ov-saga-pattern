package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.*;
import io.github.jotabrc.ov_saga.model.Item;

public interface EntityMapper {

    Item toEntity(String uuid);
    Item toEntity(ItemDto dto);
    Item toEntity(ItemDtoUpdate dto);
    ItemDtoInfo toDto(Item item);
    ItemDto toDto(String uuid);
    KafkaMessageCarrier<ItemDtoKafka> toCarrier(String uuid, boolean requestReply, boolean senderSuccess, String replyingTopic);
}
