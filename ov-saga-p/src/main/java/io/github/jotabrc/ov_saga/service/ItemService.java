package io.github.jotabrc.ov_saga.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.dto.ItemDtoInfo;
import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;

public interface ItemService {

    String save(ItemDto dto) throws JsonProcessingException;
    boolean update(ItemDtoUpdate dto);
    ItemDtoInfo get(String uuid);
}
