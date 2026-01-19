package ci553.happyshop.catalogue;

import ci553.happyshop.client.login.CustomerAccounts;
import ci553.happyshop.orderManagement.OrderState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void getOrderId() {
        ArrayList<Product> trolley = new ArrayList<>();
        Product p1 = new Product("A001", "Milk", "milk.jpg", 1.50, 100);
        trolley.add(p1);
        Order order = new Order(10, OrderState.Ordered, "2026-01-18 14:13:25", trolley);
        assertEquals(10, order.getOrderId());
    }

    @Test
    void getState() {
        ArrayList<Product> trolley = new ArrayList<>();
        Product p1 = new Product("A001", "Milk", "milk.jpg", 1.50, 100);
        trolley.add(p1);
        Order order = new Order(10, OrderState.Ordered, "2026-01-18 14:13:25", trolley);
        assertEquals(OrderState.Ordered, order.getState());
    }

    @Test
    void getOrderedDateTime() {
        ArrayList<Product> trolley = new ArrayList<>();
        Product p1 = new Product("A001", "Milk", "milk.jpg", 1.50, 100);
        trolley.add(p1);
        Order order = new Order(10, OrderState.Ordered, "2026-01-18 14:13:25", trolley);
        assertEquals("2026-01-18 14:13:25", order.getOrderedDateTime());
    }

    @Test
    void getProductList() {
        ArrayList<Product> trolley = new ArrayList<>();
        Product p1 = new Product("A001", "Milk", "milk.jpg", 1.50, 100);
        trolley.add(p1);
        Order order = new Order(10, OrderState.Ordered, "2026-01-18 14:13:25", trolley);
        assertEquals("A001", order.getProductList().getFirst().getProductId());
    }

}