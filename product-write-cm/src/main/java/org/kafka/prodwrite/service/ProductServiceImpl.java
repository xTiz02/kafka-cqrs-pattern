package org.kafka.prodwrite.service;

import org.kafka.prodwrite.model.Product;
import org.kafka.prodwrite.model.dto.ProductDto;
import org.kafka.prodwrite.model.dto.ProductEvent;
import org.kafka.prodwrite.model.repository.ProductRepository;
import org.kafka.prodwrite.util.EventType;
import org.kafka.prodwrite.util.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaSendProductService kafkaSendProductService;

    @Value("${spring-product-write-topic-name}")
    private String prodWriteTopic;

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        Product product = ProductMapper.toProduct(productDto);
        product.setActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product = productRepository.save(product);
        ProductDto savedProduct = ProductMapper.toProductDto(product);
        ProductEvent productEvent = new ProductEvent(
                EventType.CREATE,
                savedProduct,
                product.getId(),
                product.isActive());
        kafkaSendProductService.sendMessage(prodWriteTopic,productEvent);
        return savedProduct;
    }

    @Transactional
    public ProductDto updateProduct(int id,ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        ProductMapper.addProductDtoFields(product, productDto);
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);
        ProductDto updateProduct = ProductMapper.toProductDto(product);
        ProductEvent productEvent = new ProductEvent(
                EventType.UPDATE,
                updateProduct,
                product.getId(),
                product.isActive());
        kafkaSendProductService.sendMessage(prodWriteTopic,productEvent);
        return updateProduct;
    }

    @Transactional
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(product);
        ProductEvent productEvent = new ProductEvent(
                EventType.DELETE,
                null,
                product.getId(),
                product.isActive());
        kafkaSendProductService.sendMessage(prodWriteTopic,productEvent);
    }

    @Transactional
    public void changeActive(int id, boolean active) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.changeProductActive(id,active);
        ProductEvent productEvent = new ProductEvent(
                EventType.ACTIVE,
                null,
                product.getId(),
                product.isActive());
        kafkaSendProductService.sendMessage(prodWriteTopic,productEvent);

    }

}
