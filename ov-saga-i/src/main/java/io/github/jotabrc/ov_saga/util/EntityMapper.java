package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.GetPage;
import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.dto.ItemDtoInfo;
import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;

public interface EntityMapper {

    Item toEntity(String uuid, int quantity);
    Item toEntity(ItemDto dto);
    Item toEntity(ItemDtoUpdate dto);
    ItemDtoInfo toDto(Item item);
    GetPage<ItemDtoInfo> toDto(Page<Item> page);
}
