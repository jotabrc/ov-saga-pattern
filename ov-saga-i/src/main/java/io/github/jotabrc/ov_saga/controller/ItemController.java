package io.github.jotabrc.ov_saga.controller;

import io.github.jotabrc.ov_saga.dto.GetPage;
import io.github.jotabrc.ov_saga.dto.ItemDtoInfo;
import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.dto.PageFilter;
import io.github.jotabrc.ov_saga.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController @RequestMapping(path = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @PutMapping
    public ResponseEntity<String> update(ItemDtoUpdate dto) {
        itemService.update(dto);
        return ResponseEntity
                .ok("Operation (%s) updated item with UUID (%s)".formatted(dto.getOperationType(), dto.getUuid()));
    }

    @GetMapping
    public ResponseEntity<GetPage<ItemDtoInfo>> get(
            @RequestParam(value = "minValue", required = false) int minValue,
            @RequestParam(value = "maxValue", required = false) int maxValue,
            Pageable pageable
    ) {
        PageFilter pageFilter = new PageFilter(minValue, maxValue);
        GetPage<ItemDtoInfo> page = itemService.get(pageFilter, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ItemDtoInfo> get(@PathVariable("uuid") String uuid) {
        ItemDtoInfo dto = itemService.get(uuid);
        return ResponseEntity.ok(dto);
    }
}
