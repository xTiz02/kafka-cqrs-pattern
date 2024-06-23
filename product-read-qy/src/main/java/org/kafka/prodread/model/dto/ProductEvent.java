package org.kafka.prodread.model.dto;

import org.kafka.prodread.util.EventType;

public record ProductEvent (
        EventType eventType,
        ProductDto productDto,
        String productId,
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