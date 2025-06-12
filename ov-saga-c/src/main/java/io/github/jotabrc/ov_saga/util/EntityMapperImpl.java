package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.model.Item;

public class EntityMapperImpl implements EntityMapper {

    @Override
    public Item toEntity(ItemDto dto) {
        return Item
                .builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .build();
    }
}
