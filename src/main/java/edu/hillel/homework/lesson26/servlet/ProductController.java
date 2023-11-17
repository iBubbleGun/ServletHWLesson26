package edu.hillel.homework.lesson26.servlet;

import com.google.gson.Gson;
import edu.hillel.homework.lesson26.dao.OrderDAO;
import edu.hillel.homework.lesson26.dao.ProductDAO;
import edu.hillel.homework.lesson26.model.Product;
import edu.hillel.homework.lesson26.repository.OrderRepository;
import edu.hillel.homework.lesson26.repository.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/product")
public class ProductController extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            final String orderId = req.getParameter("orderId");
            final String productId = req.getParameter("productId");

            final OrderRepository orderRepository = OrderDAO.orderRepository;
            final ProductRepository productRepository = ProductDAO.productRepository;

            if (orderId != null) {
                if (orderRepository.isOrderIdExist(Integer.parseInt(orderId))) {
                    if (productId != null) {
                        if (orderRepository.getOrderById(Integer.parseInt(orderId)).isProductIdExistInOrder(Integer.parseInt(productId))) {
                            final Product product = orderRepository.getOrderById(Integer.parseInt(orderId)).getProductById(Integer.parseInt(productId));
                            writer.println(gson.toJson(product));
                        } else {
                            writer.println("Product with productId \"" + productId + "\" not found in the order with orderId \"" + orderId + "\"!");
                        }
                    } else {
                        final List<Product> products = orderRepository.getOrderById(Integer.parseInt(orderId)).getProducts();
                        writer.println(gson.toJson(products));
                    }
                } else {
                    writer.println("Order with orderId \"" + orderId + "\" not found!");
                }
            } else if (productId != null) {
                if (productRepository.isProductIdExist(Integer.parseInt(productId))) {
                    final Product product = productRepository.getProductById(Integer.parseInt(productId));
                    writer.println(gson.toJson(product));
                } else {
                    writer.println("Product with productId \"" + productId + "\" not found!");
                }
            } else {
                writer.println(gson.toJson(productRepository.getProducts()));
            }
        }
    }
}
