package org.kafka.prodread.listener;

import org.kafka.prodread.model.dto.ProductEvent;
import org.kafka.prodread.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaProductListener {

    private final Logger logger = LoggerFactory.getLogger(KafkaProductListener.class);

    @Autowired
    private ProductServiceImpl productService;


    @KafkaListener(topics = {"product-w-evt"}, groupId = "product-read" , containerFactory = "listenerContainerFactory")
    public void listener(ProductEvent event){
        logger.info("Received event: {}", event);
        switch (event.eventType().name()){
            case "CREATE":
                productService.saveProduct(event);
                break;
            case "UPDATE":
                productService.updateProduct(event);
                break;
            case "DELETE":
                productService.deleteProduct(event);
                break;
            case "ACTIVE":
                productService.changeActive(event);
                break;
            default:
                throw new RuntimeException("Event type not supported: " + event.eventType());
        }
    }
}
