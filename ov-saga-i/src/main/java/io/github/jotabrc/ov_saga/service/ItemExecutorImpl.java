package io.github.jotabrc.ov_saga.service;

import io.github.jotabrc.ov_saga.dto.ItemDtoUpdate;
import io.github.jotabrc.ov_saga.handler.ConflictException;
import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemExecutorImpl implements ItemExecutor {

    @Override
    public Item update(Item item, ItemDtoUpdate newData) {
        return item;
    }

    private void selectOperation(Item item, ItemDtoUpdate newData) {
        if (!isPositive(newData.getQuantity())) throw new ConflictException(
                "Operation (%s) requires a positive integer, provided number was (%d)"
                        .formatted(newData.getOperationType(), newData.getQuantity())
        );

        switch (newData.getOperationType()) {
            case ADD -> {
                int sum = item.getQuantity() + newData.getQuantity();
                item.setQuantity(sum);

            }
            case DEDUCT -> {
                if (hasQuantity(item.getQuantity(), newData.getQuantity())) {
                    int deduction = item.getQuantity() - newData.getQuantity();
                    item.setQuantity(deduction);
                } else throw new ConflictException(
                        "Item has insufficient available quantity for operation (%s). Available quantity: (%d), Required quantity: (%d)"
                                .formatted(newData.getOperationType(), item.getQuantity(), newData.getQuantity())
                );
            }
        }
    }

    private boolean hasQuantity(int quantity, int deduction) {
        return quantity >= deduction;
    }

    private boolean isPositive(int value) {
        return value >= 0;
    }
}
