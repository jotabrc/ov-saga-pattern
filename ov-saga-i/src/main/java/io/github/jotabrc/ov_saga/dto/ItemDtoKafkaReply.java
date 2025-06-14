package io.github.jotabrc.ov_saga.dto;

import lombok.Value;

@Value
public class ItemDtoKafkaReply {

    String message;
    boolean conflict;
}
