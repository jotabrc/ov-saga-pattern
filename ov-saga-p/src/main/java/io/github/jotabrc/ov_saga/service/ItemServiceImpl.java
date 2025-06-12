package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.dto.ItemDtoInfo;
import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.handler.ConflictException;
import io.github.jotabrc.ov_saga.handler.NotFoundException;
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
    private final ItemExecutor itemExecutor;
    private final EntityMapper mapper;
    private final ItemRepository repository;

    @Override
    @Transactional
    public String save(ItemDto dto) {
        boolean exists = registryProcessor.exists(dto.getName(), ProcessorType.NAME);
        if (exists) throw new ConflictException("Item with NAME (%s) already exists".formatted(dto.getName()));
        Item item = mapper.toEntity(dto);
        repository.save(item);
        return item.getUuid();
    }

    @Override
    @Transactional
    public boolean update(ItemDtoUpdate dto) {
        Item item = getItemOrElseThrow(dto.getUuid(), ProcessorType.UUID);
        itemExecutor.update(item, dto);
        return true;
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
