package org.kafka.prodread.model.dto;

public class ProductMongoDto {
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private boolean active;
    private String writeDbId;

    public ProductMongoDto() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getWriteDbId() {
        return writeDbId;
    }

    public void setWriteDbId(String writeDbId) {
        this.writeDbId = writeDbId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
