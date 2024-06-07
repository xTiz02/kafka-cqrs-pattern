package org.kafka.prodwrite.service;

import org.kafka.prodwrite.model.dto.ProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSendProductService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void sendMessage(String topic, ProductEvent productEvent) {
        kafkaTemplate.send(topic, productEvent).thenAccept(result -> {
            System.out.println("Sent message=[" + productEvent + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        }).exceptionally(e -> {
            System.out.println("Unable to send message=[" + productEvent + "] due to : " + e.getMessage());
            return null;
        });
    }
}
