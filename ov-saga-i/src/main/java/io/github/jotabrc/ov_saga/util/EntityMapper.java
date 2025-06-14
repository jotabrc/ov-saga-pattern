package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.*;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;

public interface EntityMapper {

    Item toEntity(String uuid, int quantity);
    Item toEntity(ItemDto dto);
    Item toEntity(ItemDtoUpdate dto);
    ItemDtoInfo toDto(Item item);
    ItemDto toDto(String uuid);
    ItemDtoKafkaReply toDto(String message, boolean conflict);
    <T> KafkaMessageCarrier<T> toDto(T dto, boolean requestReply, boolean senderOperationSuccess, String replyingTopic);
    GetPage<ItemDtoInfo> toDto(Page<Item> page);
}
