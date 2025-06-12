package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.model.Item;

import java.util.Optional;

public interface RegistryProcessor {

    boolean exists(String key, ProcessorType type);
    Optional<Item> find(String key, ProcessorType type);
}
