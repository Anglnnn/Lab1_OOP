package com.example.lab1_servlet.service;

import com.example.lab1_servlet.converter.ProductConverter;
import com.example.lab1_servlet.dto.ProductDTO;
import com.example.lab1_servlet.entity.Product;
import com.example.lab1_servlet.exception.ProductNotFoundException;
import com.example.lab1_servlet.service.data.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductControllerService {
    private final ProductService productService;
    private final ProductConverter productConverter;

    public ProductControllerService(ProductService productService, ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    public List<ProductDTO> findAll() {
        return productConverter.convertToListDTO(productService.findAll());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productConverter.convertToEntity(productDTO);
        Product savedProduct = productService.addProduct(product);
        return productConverter.convertToProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productConverter.convertToEntity(productDTO);
        Product updatedProduct = productService.updateProduct(product);
        return productConverter.convertToProductDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }

    public ProductDTO findProductById(Long id) {
        return productService.findProductById(id)
                .map(productConverter::convertToProductDTO)
                .orElseThrow(ProductNotFoundException::new);
    }
}
