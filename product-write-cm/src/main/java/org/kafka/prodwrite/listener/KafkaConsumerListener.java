package org.kafka.prodwrite.listener;

import org.kafka.prodwrite.model.Product;
import org.kafka.prodwrite.model.dto.ProductEvent;
import org.kafka.prodwrite.model.repository.ProductRepository;
import org.kafka.prodwrite.service.ProductServiceImpl;
import org.kafka.prodwrite.util.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;

@Configuration
public class KafkaConsumerListener {

    @Autowired
    private ProductRepository productService;

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @KafkaListener(topics = "product-revert-evt", groupId = "revert-group", containerFactory = "listenerContainerFactory")
    public void consume(ProductEvent event) {
        logger.info("Received revert event: {}", event);
        try{
            Product product = null;
            switch (event.eventType().name()){
                case "CREATE":
                    productService.deleteByUnitCode(event.productId());
                    break;
                case "UPDATE":
                    product = productService.findByUnitCode(event.productId());
                    ProductMapper.addProductDtoFields(product, event.productDto());
                    productService.save(product);
                    break;
                case "DELETE":
                    product = ProductMapper.toProduct(event.productDto());
                    product.setUnitCode(event.productId());
                    product.setActive(event.active());
                    product.setCreatedAt(LocalDateTime.now());
                    product.setUpdatedAt(LocalDateTime.now());
                    productService.save(product);
                    break;
                case "ACTIVE":
                    productService.changeProductActiveByUnitId(event.productId(), event.active());
                    break;
                default:
                    throw new RuntimeException("Event type not supported: " + event.eventType());
            }
        }catch (Exception e){
            logger.error("Error processing revert event type: {}. Message: {}",event.eventType(), e.getMessage());
            e.printStackTrace();
        }
    }
}
