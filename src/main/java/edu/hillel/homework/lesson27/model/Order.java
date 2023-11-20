package edu.hillel.homework.lesson27.model;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {

    private final int id;
    private final String date;
    private final double cost;
    private final List<Product> products;

    public Order(int id, @NotNull List<Product> products) {
        this.id = id;
        this.date = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss")
                .format(new Date());
        this.cost = products.stream()
                .mapToDouble(Product::getCost)
                .sum();
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean isProductExistInOrder(Product product) {
        return products.stream().anyMatch(prod -> prod.equals(product));
    }

    public boolean isProductIdExistInOrder(int id) {
        return products.stream().anyMatch(prod -> prod.getId() == id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(cost, order.cost) == 0 && Objects.equals(date, order.date) && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, cost, products);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", cost=" + cost +
                ", products=" + products +
                '}';
    }
}
