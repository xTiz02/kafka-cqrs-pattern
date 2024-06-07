package org.kafka.prodwrite.model.dto;

import org.kafka.prodwrite.util.EventType;

public record ProductEvent (
     EventType eventType,
     ProductDto productDto,
     int productId,
     boolean active
){
    @Override
    public String toString() {
        return "ProductEvent{" +
                "eventType=" + eventType +
                ", productDto=" + productDto +
                ", productId=" + productId +
                ", active=" + active +
                '}';
    }
}
