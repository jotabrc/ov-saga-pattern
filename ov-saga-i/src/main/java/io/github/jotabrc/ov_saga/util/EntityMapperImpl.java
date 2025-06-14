package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.*;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public Item toEntity(String uuid, int quantity) {
        return Item
                .builder()
                .uuid(uuid)
                .quantity(quantity)
                .build();
    }

    @Override
    public Item toEntity(ItemDto dto) {
        return toEntity(dto.getUuid(), dto.getQuantity());
    }

    @Override
    public Item toEntity(ItemDtoUpdate dto) {
        return toEntity(dto.getUuid(), dto.getQuantity());
    }

    @Override
    public ItemDtoInfo toDto(Item item) {
        return new ItemDtoInfo(
                item.getUuid(),
                item.getQuantity(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }

    @Override
    public ItemDto toDto(String uuid) {
        return new ItemDto(uuid, 0);
    }

    @Override
    public ItemDtoKafkaReply toDto(String message, boolean conflict) {
        return new ItemDtoKafkaReply(message, conflict);
    }

    @Override
    public <T> KafkaMessageCarrier<T> toDto(T dto, boolean requestReply, boolean senderOperationSuccess, String replyingTopic) {
        return new KafkaMessageCarrier<T>(false, true, dto, replyingTopic);
    }

    @Override
    public GetPage<ItemDtoInfo> toDto(Page<Item> page) {
        return GetPage
                .<ItemDtoInfo>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(this::toDto)
                                .toList()
                )
                .last(page.isLast())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .size(page.getSize())
                .number(page.getNumber())
                .numberOfElements(page.getNumberOfElements())
                .empty(page.isEmpty())
                .build();
    }
}
