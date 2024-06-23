package org.kafka.prodread.service;

import org.kafka.prodread.model.dto.ProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaRevertService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void sendRevertProduct(String topic,ProductEvent productEvent){
        kafkaTemplate.send(topic, productEvent).thenAccept(result -> {
            System.out.println("Sent revert message=[" + productEvent + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        }).exceptionally(e -> {
            System.out.println("Unable to send revert message=[" + productEvent + "] due to : " + e.getMessage());
            return null;
        });
    }
}
