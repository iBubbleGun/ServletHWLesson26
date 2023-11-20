package edu.hillel.homework.lesson26.services;

import edu.hillel.homework.lesson26.dao.ProductDAO;
import edu.hillel.homework.lesson26.model.Order;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public OrderService() {
        addStartingOrders();
    }

    public Order getOrderById(int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void deleteOrderById(int id) {
        orders.removeIf(order -> order.getId() == id);
    }

    public boolean isOrderExist(@NotNull Order targetOrder) {
        return orders.stream().anyMatch(targetOrder::equals);
    }

    public boolean isOrderIdExist(int targetOrderId) {
        return orders.stream().anyMatch(order -> order.getId() == targetOrderId);
    }

    private void addStartingOrders() {
        orders.add(new Order(1, ProductDAO.productRepository.getProducts().subList(0, 6)));
        orders.add(new Order(2, ProductDAO.productRepository.getProducts().subList(6, 11)));
        orders.add(new Order(3, ProductDAO.productRepository.getProducts().subList(11, 13)));
    }
}
