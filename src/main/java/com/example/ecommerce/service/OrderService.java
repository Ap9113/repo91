package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductService productService;

    @SuppressWarnings("unchecked")
    public Order checkout(User user, HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        List<OrderItem> items = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Long pid = entry.getKey(); int qty = entry.getValue();
            Product p = productService.get(pid);
            productService.reduceStock(pid, qty);
            items.add(OrderItem.builder().product(p).quantity(qty).build());
        }
        Order order = Order.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .items(items)
                .build();
        Order saved = orderRepo.save(order);
        session.removeAttribute("cart");
        return saved;
    }
}
