package org.kafka.prodread.controller;

import org.kafka.prodread.model.Product;
import org.kafka.prodread.model.dto.ProductDto;
import org.kafka.prodread.model.dto.ProductEvent;
import org.kafka.prodread.model.dto.ProductMongoDto;
import org.kafka.prodread.model.repository.ProductRepository;
import org.kafka.prodread.service.ProductServiceImpl;
import org.kafka.prodread.util.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductControllerPrueba {



    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/create")
    public String createProduct(@RequestBody ProductMongoDto productMongoDto) {

        Product product = new Product();
        product.setName(productMongoDto.getName());
        product.setDescription(productMongoDto.getDescription());
        product.setPrice(productMongoDto.getPrice());
        product.setStock(productMongoDto.getStock());
        product.setActive(productMongoDto.isActive());
        product.setWriteDbId(productMongoDto.getWriteDbId());
        productService.saveProduct(new ProductEvent(EventType.CREATE,
                null,
               "1234",
                true));
        return "Product created";
    }
}
