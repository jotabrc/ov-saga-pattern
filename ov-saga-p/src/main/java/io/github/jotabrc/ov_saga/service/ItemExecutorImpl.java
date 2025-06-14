package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.model.Item;
import io.github.jotabrc.ov_saga.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class ItemExecutorImpl implements ItemExecutor {

    private final ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item, ItemDtoUpdate newItemData) {
        return setUpdate(item, newItemData);
    }

    private Item setUpdate(Item item, ItemDtoUpdate newItemData) {
        return item
                .setName(newItemData.getName());
    }
}
