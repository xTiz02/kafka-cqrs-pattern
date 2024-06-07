package org.kafka.prodwrite.controller;

import org.kafka.prodwrite.model.Product;
import org.kafka.prodwrite.model.dto.ProductDto;
import org.kafka.prodwrite.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;


    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto pDto = productService.saveProduct(productDto);
        return ResponseEntity.ok(pDto);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(value = "id") int id, @RequestBody ProductDto productDto) {
        ProductDto pDto = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(pDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateProduct(@PathVariable(value = "id") int id) {
        productService.changeActive(id,true);
        return ResponseEntity.ok("Product activated");
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateProduct(@PathVariable(value = "id") int id) {
        productService.changeActive(id,false);
        return ResponseEntity.ok("Product deactivated");
    }



}
