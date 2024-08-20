package com.OrderService.service;

import com.OrderService.client.BookFeignClient;
import com.OrderService.model.Book;
import com.OrderService.model.Order;
import com.OrderService.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private BookFeignClient bookFeignClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order createOrder(Order order) {
        // Check book availability using Feign Client
        try {
            Book book = bookFeignClient.getBookById(order.getBook_id());
            if (book == null) {
                throw new RuntimeException("Book not found");
            }
            if (book.getStock() < order.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book ID: " + order.getBook_id());
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Book not found", e);
        }
        if (!"PENDING".equals(order.getStatus()) &&
                !"CONFIRMED".equals(order.getStatus()) &&
                !"CANCELLED".equals(order.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + order.getStatus());
        }
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }


    public Order updateOrder(Long id, Order order) {
            if (!orderRepository.existsById(id)) {
                throw new RuntimeException("Order not found");
            }

            if (!"PENDING".equals(order.getStatus()) &&
                    !"CONFIRMED".equals(order.getStatus()) &&
                    !"CANCELLED".equals(order.getStatus())) {
                throw new IllegalArgumentException("Invalid status: " + order.getStatus());
            }
            order.setId(id);
            return orderRepository.save(order);
        }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
