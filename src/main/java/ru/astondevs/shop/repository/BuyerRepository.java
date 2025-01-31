package ru.astondevs.shop.repository;

import ru.astondevs.shop.entity.Buyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс {@link BuyerRepository} предоставляет доступ к данным покупателей в базе данных.
 * Наследует методы из {@link JpaRepository}, такие как сохранение, удаление, поиск по ID и другие.
 * Аннотация {@link Repository} указывает, что этот интерфейс является Spring-репозиторием.
 *
 * @see JpaRepository
 * @see Repository
 * @see Buyer
 */
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
