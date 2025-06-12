package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.model.Item;

import java.util.Optional;

public interface RegistrySelector {

    Optional<Item> select(String key, ProcessorType type);
    boolean selectByExistence(String key, ProcessorType type);
}
