package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.PageFilter;
import io.github.jotabrc.ov_saga.handler.ProcessorTypeException;
import io.github.jotabrc.ov_saga.model.Item;
import io.github.jotabrc.ov_saga.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrySelectorImpl implements RegistrySelector {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> select(final String key, final ProcessorType type) {
        return switch (type) {
            case NAME -> itemRepository.findByName(key);
            case UUID -> itemRepository.findByUuid(key);
            case null, default -> throw new ProcessorTypeException("Unsupported processor type (%s)".formatted(type));
        };
    }

    @Override
    public boolean selectByExistence(final String key, final ProcessorType type) {
        return switch (type) {
            case NAME -> itemRepository.existsByName(key);
            case UUID -> itemRepository.existsByUuid(key);
            case null, default -> throw new ProcessorTypeException("Unsupported processor type (%s)".formatted(type));
        };
    }

    @Override
    public Page<Item> select(PageFilter filter, Pageable pageable) {
        if (filter.getMinQuantity() < filter.getMaxQuantity())
            return itemRepository.findByQuantityBetween(filter.getMinQuantity(), filter.getMaxQuantity(), pageable);
        else if (filter.getMaxQuantity() >= filter.getMinQuantity())
            return itemRepository.findByQuantityLessThanEqual(filter.getMaxQuantity(), pageable);
        else
            return itemRepository.findByQuantityMoreThanEqual(filter.getMinQuantity(), pageable);
    }
}
