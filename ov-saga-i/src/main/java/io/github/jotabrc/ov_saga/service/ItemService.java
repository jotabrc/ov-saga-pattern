package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.*;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    boolean save(ItemDto dto);
    boolean update(ItemDtoUpdate dto);
    GetPage<ItemDtoInfo> get(PageFilter filter, Pageable pageable);
    ItemDtoInfo get(String uuid);
}
