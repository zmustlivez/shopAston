package ru.astondevs.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astondevs.shop.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT DISTINCT orders FROM Order orders JOIN FETCH orders.products WHERE orders.buyer.id = :id")
    List<Order> findOrdersByBuyer(@Param("buyerId") long id);

    Order readOrderById(long id);
}
