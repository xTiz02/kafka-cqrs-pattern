package org.kafka.prodread.listener;

import org.kafka.prodread.model.Product;
import org.kafka.prodread.model.dto.ProductEvent;
import org.kafka.prodread.model.repository.ProductRepository;
import org.kafka.prodread.service.KafkaRevertService;
import org.kafka.prodread.service.ProductServiceImpl;
import org.kafka.prodread.util.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaProductListener {

    private final Logger logger = LoggerFactory.getLogger(KafkaProductListener.class);

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaRevertService kafkaRevertService;

    @Value("${spring-product-revert-topic-name}")
    private String prodRevertTopic;


    @KafkaListener(topics = {"product-w-evt"}, groupId = "product-read" , containerFactory = "listenerContainerFactory")
    public void listener(ProductEvent event){
        logger.info("Received event: {}", event);
        String id = event.productId();
        System.out.println("id: " + id);
        try{
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
        }catch (Exception e){
            logger.error("Error processing event type : {}. Message: {}",event.eventType(), e.getMessage());
            //hacer un servicio para gestionar evento de revert
            if(event.eventType().name().equals("UPDATE")){
                Product product = productRepository.findProductByWriteDbId(event.productId());
                ProductEvent revertEvent = new ProductEvent(event.eventType(), ProductMapper.toProductDto(product),event.productId(),event.active());
                //kafkaRevertService.sendRevertProduct(prodRevertTopic,revertEvent);
            }else {
                //send revert event
                //kafkaRevertService.sendRevertProduct(prodRevertTopic,event);
            }
            e.printStackTrace();
        }

    }
}
