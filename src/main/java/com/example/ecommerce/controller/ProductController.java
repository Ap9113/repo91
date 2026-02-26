package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping({"/", "/products"})
    public String list(Model m) {
        m.addAttribute("products", productService.listAll());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model m) {
        Product p = productService.get(id);
        m.addAttribute("product", p);
        return "product_detail";
    }
}
