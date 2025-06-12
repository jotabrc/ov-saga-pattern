package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.*;
import io.github.jotabrc.ov_saga.handler.ConflictException;
import io.github.jotabrc.ov_saga.handler.NotFoundException;
import io.github.jotabrc.ov_saga.model.Item;
import io.github.jotabrc.ov_saga.repository.ItemRepository;
import io.github.jotabrc.ov_saga.util.EntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final RegistryProcessor registryProcessor;
    private final ItemExecutor itemExecutor;
    private final EntityMapper mapper;
    private final ItemRepository repository;

    @Override
    @Transactional
    public boolean save(ItemDto dto) {
        boolean exists = registryProcessor.exists(dto.getUuid(), ProcessorType.UUID);
        if (exists) throw new ConflictException("Item with UUID (%s) already exists".formatted(dto.getUuid()));
        Item item = mapper.toEntity(dto);
        repository.save(item);
        return true;
    }

    @Override
    @Transactional
    public boolean update(ItemDtoUpdate dto) {
        Item item = getItemOrElseThrow(dto.getUuid(), ProcessorType.UUID);
        itemExecutor.update(item, dto);
        return true;
    }

    @Override
    public GetPage<ItemDtoInfo> get(PageFilter filter, Pageable pageable) {
        Page<Item> page = registryProcessor.find(filter, pageable);
        return mapper.toDto(page);
    }

    @Override
    public ItemDtoInfo get(String uuid) {
        Item item = getItemOrElseThrow(uuid, ProcessorType.UUID);
        return mapper.toDto(item);
    }

    private Item getItemOrElseThrow(String uuid, ProcessorType processorType) {
        return registryProcessor.find(uuid, processorType)
                .orElseThrow(() -> new NotFoundException("Item with UUID (%s) not found"
                        .formatted(uuid)));
    }
}
