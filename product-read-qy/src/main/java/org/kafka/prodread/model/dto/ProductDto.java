package org.kafka.prodread.model.dto;

public record ProductDto(
        String name,
        String description,
        double price,
        int stock


){
    @Override
    public String toString() {
        return "ProductDto{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}