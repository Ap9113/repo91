package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll();
    }

    public Product get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public void reduceStock(Long productId, int qty) {
        Product p = get(productId);
        if (p.getStock() < qty) throw new IllegalArgumentException("Insufficient stock");
        p.setStock(p.getStock() - qty);
        repo.save(p);
    }
}
