package ru.astondevs.shop.service;

import ru.astondevs.shop.entity.Shop;

public interface ShopService {

    void update();

    void read();

    void delete();


    Shop create();

    void findAll();
}
