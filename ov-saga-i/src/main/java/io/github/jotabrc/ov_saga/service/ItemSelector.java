package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.PageFilter;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemSelector {

    Optional<Item> find(String key, ProcessorType type);
    Page<Item> find(PageFilter filter, Pageable pageable);
    boolean exists(String key, ProcessorType type);
}
