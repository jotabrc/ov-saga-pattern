package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemExecutorImpl implements ItemExecutor {

    @Override
    public Item update(Item item, ItemDtoUpdate newData) {
        return setUpdate(item, newData);
    }

    private Item setUpdate(Item item, ItemDtoUpdate newData) {
        return item
                .setName(newData.getName());
    }
}
