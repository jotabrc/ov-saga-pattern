package io.github.jotabrc.ov_saga.repository;

import io.github.jotabrc.ov_saga.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByUuid(String uuid);
    Optional<Item> findByName(String name);
    Page<Item> findByQuantityBetween(int minValue, int maxValue, Pageable pageable);
    Page<Item> findByQuantityLessThanEqual(int maxValue, Pageable pageable);
    Page<Item> findByQuantityMoreThanEqual(int minValue, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByUuid(String name);
}
