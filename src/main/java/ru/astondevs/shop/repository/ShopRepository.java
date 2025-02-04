package ru.astondevs.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.shop.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

}
