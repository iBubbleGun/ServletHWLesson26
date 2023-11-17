package edu.hillel.homework.lesson26.repository;

import edu.hillel.homework.lesson26.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        addStartingProducts();
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

    public void add(Product product) {
        products.add(product);
    }

    public void deleteById(int id) {
        products.removeIf(product -> product.getId() == id);
    }

    public boolean isProductExist(Product product) {
        return products.stream().anyMatch(prod -> prod.equals(product));
    }

    public boolean isProductIdExist(int id) {
        return products.stream().anyMatch(prod -> prod.getId() == id);
    }

    private void addStartingProducts() {
        products.add(new Product(1, "Book", 125.3));
        products.add(new Product(2, "Book", 250.0));
        products.add(new Product(3, "Pencil", 25.1));
        products.add(new Product(4, "Calculator", 570.35));
        products.add(new Product(5, "Bag", 750.25));
        products.add(new Product(6, "Computer", 25500.0));
        products.add(new Product(7, "Mouse", 500.0));
        products.add(new Product(8, "USB cable", 250.0));
        products.add(new Product(9, "Cell phone", 20500.0));
        products.add(new Product(10, "TV", 75000.5));
        products.add(new Product(11, "Flashlight", 950.25));
        products.add(new Product(12, "Sneakers", 2500.0));
        products.add(new Product(13, "T-shirt", 700.0));
    }
}
