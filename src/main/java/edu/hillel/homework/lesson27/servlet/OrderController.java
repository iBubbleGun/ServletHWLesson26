package edu.hillel.homework.lesson27.servlet;

import com.google.gson.Gson;
import edu.hillel.homework.lesson27.dao.OrderDAO;
import edu.hillel.homework.lesson27.model.Order;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/order")
public class OrderController extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            final String orderId = req.getParameter("orderId");
            if (orderId == null) {
                writer.println(gson.toJson(OrderDAO.orderRepository.getOrders()));
            } else {
                final Order order = OrderDAO.orderRepository.getOrderById(Integer.parseInt(orderId));
                if (order != null) {
                    writer.println(gson.toJson(order));
                } else {
                    writer.println("Order with id \"" + orderId + "\" not found!");
                }
            }
        }
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            final Order order = gson.fromJson(req.getReader(), Order.class);
            if (order != null && order.getId() != 0 && !order.getProducts().isEmpty()) {
                if (OrderDAO.orderRepository.isOrderExist(order)
                        || OrderDAO.orderRepository.isOrderIdExist(order.getId())) {
                    writer.println("Order already exist!");
                } else {
                    OrderDAO.orderRepository.addOrder(order);
                    writer.println("New order successfully added.");
                    writer.println(order);
                }
            } else {
                writer.println("Empty order can't be added!");
            }
        }
    }

    @Override
    protected void doDelete(@NotNull HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            final String orderId = req.getParameter("orderId");
            if (orderId != null) {
                if (!OrderDAO.orderRepository.isOrderIdExist(Integer.parseInt(orderId))) {
                    writer.println("Order with id \"" + orderId + "\" not found!");
                } else {
                    OrderDAO.orderRepository.deleteOrderById(Integer.parseInt(orderId));
                    writer.println("Order with id \"" + orderId + "\" successfully deleted.");
                }
            }
        }
    }
}
