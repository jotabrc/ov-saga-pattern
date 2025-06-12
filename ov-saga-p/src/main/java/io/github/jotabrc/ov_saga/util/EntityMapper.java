package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.dto.ItemDtoInfo;
import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.model.Item;

public interface EntityMapper {

    Item toEntity(String uuid);
    Item toEntity(ItemDto dto);
    Item toEntity(ItemDtoUpdate dto);
    ItemDtoInfo toDto(Item item);
}
