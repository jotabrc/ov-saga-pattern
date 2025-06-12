package io.github.jotabrc.ov_saga.repository;

import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByUuid(String uuid);
    Page<Item> findByQuantityBetween(int minValue, int maxValue, Pageable pageable);
    Page<Item> findByQuantityLessThanEqual(int maxValue, Pageable pageable);
    Page<Item> findByQuantityGreaterThanEqual(int minValue, Pageable pageable);
    boolean existsByUuid(String uuid);
}
