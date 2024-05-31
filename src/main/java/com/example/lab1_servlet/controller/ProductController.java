package com.example.lab1_servlet.controller;



import com.example.lab1_servlet.dto.ProductDTO;
import com.example.lab1_servlet.service.ProductControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {

    private final ProductControllerService productControllerService;


    @Autowired
    public ProductController(ProductControllerService productControllerService) {
        this.productControllerService = productControllerService;
    }

    @GetMapping(value = "/product")
    public List<ProductDTO> getAllProducts() {
        return productControllerService.findAll();
    }

    @PostMapping(value = "/product")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return productControllerService.addProduct(productDTO);
    }

    @PutMapping(value = "/product/{productId}")
    public ProductDTO updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        productDTO.setId(productId);
        return productControllerService.updateProduct(productDTO);
    }

    @DeleteMapping(value = "/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productControllerService.deleteProduct(id);
    }
}
