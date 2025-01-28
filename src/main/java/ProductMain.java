import dao.ProductDAO;
import dao.ProductDAOImpl;
import menu.ProductMenu;

import java.util.Scanner;

public class ProductMain {

    public static void main (String [] args){
        ProductDAO productDAO = new ProductDAOImpl();
        productDAO.createTable();
        ProductMenu.menu();

    }
}
