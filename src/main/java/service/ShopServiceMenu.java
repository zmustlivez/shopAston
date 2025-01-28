package service;

import dao.OrderDAO;
import dao.ShopDAO;
import dao.impl.OrderDAOImpl;
import dao.impl.ShopDAOImpl;

public class ShopServiceMenu {
    static ShopDAO shopDAO = new ShopDAOImpl();
}
