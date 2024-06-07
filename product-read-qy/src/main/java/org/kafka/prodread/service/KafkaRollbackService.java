package org.kafka.prodread.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaRollbackService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void rollbackProduct(int id){
        kafkaTemplate.send("product-w-evt", id);
    }
}
