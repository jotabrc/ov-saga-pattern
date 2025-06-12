package io.github.jotabrc.ov_saga.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ItemDtoInfo {

    String uuid;
    int quantity;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
