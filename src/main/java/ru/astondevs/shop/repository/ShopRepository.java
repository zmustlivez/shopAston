package ru.astondevs.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astondevs.shop.entity.Shop;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("SELECT DISTINCT s FROM Shop s JOIN FETCH s.orderList WHERE s.id = :id")
    Optional<Shop> findByIdWithOrders(@Param("id") Long id);
}
