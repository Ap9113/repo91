package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;

    @SuppressWarnings("unchecked")
    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, Integer qty, HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new java.util.HashMap<>();
        }
        cart.put(id, cart.getOrDefault(id, 0) + (qty == null ? 1 : qty));
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model m) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) cart = java.util.Collections.emptyMap();
        List<com.example.ecommerce.model.OrderItem> items = cart.entrySet().stream()
                .map(e -> com.example.ecommerce.model.OrderItem.builder()
                        .product(productService.get(e.getKey()))
                        .quantity(e.getValue()).build())
                .collect(Collectors.toList());
        m.addAttribute("cartItems", items);
        return "cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@AuthenticationPrincipal User user, HttpSession session) {
        orderService.checkout(user, session);
        return "checkout";
    }
}
