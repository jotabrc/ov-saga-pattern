package io.github.jotabrc.ov_saga.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ItemDtoInfo {

    String uuid;
    String name;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
