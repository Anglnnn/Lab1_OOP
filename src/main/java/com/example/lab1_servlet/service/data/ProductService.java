package com.example.lab1_servlet.service.data;

import com.example.lab1_servlet.entity.Product;
import com.example.lab1_servlet.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        productRepository.addProduct(product);
        return product;
    }

    public Product updateProduct(Product product) {
        productRepository.updateProduct(product);
        return product;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
}
