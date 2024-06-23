package org.kafka.prodread.service;

import org.kafka.prodread.model.Product;
import org.kafka.prodread.model.dto.ProductDto;
import org.kafka.prodread.model.dto.ProductEvent;
import org.kafka.prodread.model.dto.ProductViewDto;
import org.kafka.prodread.model.repository.ProductRepository;
import org.kafka.prodread.util.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepository productRepository;


    public List<ProductViewDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toProductViewDto)
                .collect(Collectors.toList());
    }

    public ProductViewDto getProductById(String id) {
        return ProductMapper.toProductViewDto(productRepository.findById(id).orElse(null));
    }

    public ProductViewDto getProductByWriteId(String id) {
        return ProductMapper.toProductViewDto(productRepository.findProductByWriteDbId(id));
    }

    @Transactional
    public String saveProduct(ProductEvent event) {
        Product product =  ProductMapper.toProduct(event.productDto());
        product.setActive(event.active());
        product.setWriteDbId(event.productId());
        productRepository.save(product);
        //throw new RuntimeException("Error saving product in read db");
        //si algo falla en la actualizacion de la base de datos, se lanza una excepcion y se hace rollback
        return "Product saved in read db";
    }

    @Transactional
    public String updateProduct(ProductEvent event) {
        Product product =  productRepository.findProductByWriteDbId(event.productId());
        if (product == null) {
            throw new RuntimeException("Product not found in read db");
        }
        ProductMapper.addProductDtoFields(product,event.productDto());
        product.setActive(event.active());
        productRepository.save(product);
        //throw new RuntimeException("Error saving product in read db");
        //si algo falla en la actualizacion de la base de datos, se lanza una excepcion y se hace rollback
        return "Product updated in read db";
    }

    @Transactional
    public String deleteProduct(ProductEvent event) {
        Product product =  productRepository.findProductByWriteDbId(event.productId());
        if (product == null) {
            throw new RuntimeException("Product not found in read db");
        }
        productRepository.delete(product);
        //throw new RuntimeException("Error saving product in read db");
        //si algo falla en la actualizacion de la base de datos, se lanza una excepcion y se hace rollback
        return "Product deleted in read db";
    }

    @Transactional
    public String changeActive(ProductEvent event) {
        Product product =  productRepository.findProductByWriteDbId(event.productId());
        if (product == null) {
            throw new RuntimeException("Product not found in read db");
        }
        productRepository.updateActive(event.productId(),event.active());
        //throw new RuntimeException("Error saving product in read db");
        //si algo falla en la actualizacion de la base de datos, se lanza una excepcion y se hace rollback
        return "Product active status changed in read db";
    }
}
