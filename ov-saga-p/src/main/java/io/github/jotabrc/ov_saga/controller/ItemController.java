package io.github.jotabrc.ov_saga.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_saga.dto.ItemDto;
import io.github.jotabrc.ov_saga.dto.ItemDtoInfo;
import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController @RequestMapping(path = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> save(ItemDto dto) throws JsonProcessingException {
        String uuid = itemService.save(dto);
        URI location = ServletUriComponentsBuilder
                .fromPath("/item/{uuid}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<String> update(ItemDtoUpdate dto) {
        itemService.update(dto);
        return ResponseEntity
                .ok("Updated item with UUID (%s)".formatted(dto.getUuid()));
    }

    @GetMapping
    public ResponseEntity<ItemDtoInfo> get(@RequestParam("uuid") String uuid) {
        ItemDtoInfo item = itemService.get(uuid);
        return ResponseEntity.ok().body(item);
    }
}
