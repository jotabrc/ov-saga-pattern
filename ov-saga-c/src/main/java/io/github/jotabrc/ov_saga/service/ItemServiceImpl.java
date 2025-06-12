package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.handler.ConflictException;
import io.github.jotabrc.ov_saga.model.Item;
import io.github.jotabrc.ov_saga.repository.ItemRepository;
import io.github.jotabrc.ov_saga.util.EntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final RegistryProcessor registryProcessor;
    private final EntityMapper mapper;
    private final ItemRepository repository;

    @Override @Transactional
    public boolean save(ItemDto dto) {
        try {
            boolean exists = registryProcessor.exists(dto.getName(), ProcessorType.NAME);
            if (exists) throw new ConflictException("Item with name (%s) already exists".formatted(dto.getName()));
        } catch (Exception e) {
            log.warn("Failed to check item in database ({}), error: {}", dto, e.getMessage());
            throw e;
        }

        Item item = mapper.toEntity(dto);

        try {
            repository.save(item);
            return true;
        } catch (Exception e) {
            log.warn("Failed to persist item ({}), error: {}", item, e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean update(String uuid, ItemDto dto) {
        return false;
    }

    @Override
    public boolean deactivate(String uuid) {
        return false;
    }
}
