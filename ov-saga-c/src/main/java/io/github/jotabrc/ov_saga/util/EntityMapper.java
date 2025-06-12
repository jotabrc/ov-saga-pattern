package io.github.jotabrc.ov_saga.util;

import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.model.Item;

public interface EntityMapper {

    Item toEntity(ItemDto dto);
}
