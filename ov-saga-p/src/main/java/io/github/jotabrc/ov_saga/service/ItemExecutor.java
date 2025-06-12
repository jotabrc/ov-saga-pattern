package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.model.Item;

public interface ItemExecutor {

    Item update(Item item, ItemDtoUpdate newData);
}
