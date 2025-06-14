package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.handler.ProcessorTypeException;
import io.github.jotabrc.ov_saga.model.Item;
import io.github.jotabrc.ov_saga.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemSelectorImpl implements ItemSelector {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> find(final String key, final ProcessorType type) {
        return switch (type) {
            case NAME -> itemRepository.findByName(key);
            case UUID -> itemRepository.findByUuid(key);
            case null, default -> throw new ProcessorTypeException("Unsupported processor type (%s)".formatted(type));
        };
    }

    @Override
    public boolean exists(final String key, final ProcessorType type) {
        return switch (type) {
            case NAME -> itemRepository.existsByName(key);
            case UUID -> itemRepository.existsByUuid(key);
            case null, default -> throw new ProcessorTypeException("Unsupported processor type (%s)".formatted(type));
        };
    }
}
