package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.PageFilter;
import io.github.jotabrc.ov_saga.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @AllArgsConstructor
public class RegistryProcessorImpl implements RegistryProcessor {

    private final RegistrySelector registrySelector;

    @Override
    public boolean exists(String key, ProcessorType type) {
        return registrySelector.selectByExistence(key, type);
    }

    @Override
    public Optional<Item> find(String key, ProcessorType type) {
        return registrySelector.select(key, type);
    }

    @Override
    public Page<Item> find(PageFilter filter, Pageable pageable) {
        return registrySelector.select(filter, pageable);
    }
}
