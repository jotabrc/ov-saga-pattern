package io.github.jotabrc.ov_saga.dto;

import lombok.Value;

@Value
public class KafkaMessageCarrier<T> {

    boolean requestReply;
    boolean senderOperationSuccess;
    T content;
    String replyingTopic;
}
