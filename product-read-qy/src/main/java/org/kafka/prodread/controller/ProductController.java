package org.kafka.prodread.controller;

import org.kafka.prodread.model.dto.ProductDto;
import org.kafka.prodread.model.dto.ProductViewDto;
import org.kafka.prodread.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/all")
    public List<ProductViewDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductViewDto getProductById(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/write/{id}")
    public ProductViewDto getProductByWriteId(@PathVariable("id") String id) {
        return productService.getProductByWriteId(id);
    }
}
