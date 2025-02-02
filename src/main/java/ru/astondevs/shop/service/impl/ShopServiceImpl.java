package ru.astondevs.shop.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astondevs.shop.entity.Shop;
import ru.astondevs.shop.repository.ShopRepository;
import ru.astondevs.shop.service.ShopService;

import java.util.Scanner;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final Scanner scanner = new Scanner(System.in);

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop create() {
        String name = setName();

        Shop shop = new Shop();
        shop.setName(name);

        Shop save = shopRepository.save(shop);
        log.info("Successfully created shop with id: {}", save.getId());
        return save;
    }

    private String setName() {
        String name;
        while (true) {
            System.out.println("Enter name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Enter valid name: ");
            }else break;
        }
        return name;
    }

    @Override
    public void read() {
        System.out.println("Enter shop id: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop != null) {
            System.out.println("Shop found: " + shop);
        }else {
            log.error("Shop not found");
        }
    }

    @Override
    public void update() {
        System.out.println("Enter shop id: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop == null) {
            log.error("Shop not found");
            return;
        }

        System.out.println("Current shop name: " + shop.getName());
        String name = setName();

        shop.setName(name);

        shopRepository.save(shop);
        log.info("Successfully updated shop with id: {}", shop.getId());
    }
    @Override
    public void delete() {
        System.out.println("Enter id: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        if (shopRepository.existsById(id)){
            log.info("Deleted shop with id: {}", id);
            shopRepository.deleteById(id);
        }else {
            log.error("Shop with id: {} does not exist", id);
        }

    }

    @Override
    public void findAll() {
        System.out.println("List of shops: ");
        shopRepository.findAll().forEach(System.out::println);
    }
}
