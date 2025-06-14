package io.github.jotabrc.ov_saga.service.messaging;

import io.github.jotabrc.ov_saga.kafka.Topic;

public class TopicSelectorImpl implements TopicSelector {

    @Override
    public String select(String topic) {
        if (topic.equals(Topic.ITEM_REGISTER_EVENT_REPLY)) return Topic.ITEM_REGISTER_EVENT;
        else if (topic.equals(Topic.ITEM_REGISTER_EVENT)) return Topic.ITEM_REGISTER_EVENT;
        throw new IllegalArgumentException(
                "Provided topic (%s) not supported to a reaction chain"
                .formatted(topic));
    }
}
