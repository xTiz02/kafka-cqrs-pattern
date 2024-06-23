package org.kafka.prodread.model.dto;

public record ProductViewDto(
        String name,
        String description,
        double price,
        int stock,
        boolean active,
        String id
) {
    @Override
    public String toString() {
        return "ProductViewDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", active=" + active +
                ", id='" + id + '\'' +
                '}';
    }
}
