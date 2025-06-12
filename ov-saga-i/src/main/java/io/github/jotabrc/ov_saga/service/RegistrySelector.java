package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.PageFilter;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegistrySelector {

    Optional<Item> select(String key, ProcessorType type);
    boolean selectByExistence(String key, ProcessorType type);
    Page<Item> select(PageFilter filter, Pageable pageable);
}
