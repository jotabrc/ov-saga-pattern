package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDto;

public interface ItemService {

    boolean save(ItemDto dto);
    boolean update(String uuid, ItemDto dto);
    boolean deactivate(String uuid);
}
