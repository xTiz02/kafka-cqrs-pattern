package org.kafka.prodread.util;


import org.kafka.prodread.model.Product;
import org.kafka.prodread.model.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    public static Product toProduct(ProductDto productDto) {
        Product product = new Product();
        return addProductDtoFields(product, productDto);
    }



    public static Product addProductDtoFields(Product product, ProductDto productDto) {
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setStock(productDto.stock());
        return product;
    }
}
