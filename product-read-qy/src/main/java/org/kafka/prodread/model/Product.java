package org.kafka.prodread.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Product {
    @MongoId(value = FieldType.STRING)
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private boolean active;
    private int writeDbId;

    public Product() {
    }

    public Product(String id, String name, String description, double price, int stock, boolean active, int writeDbId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.writeDbId = writeDbId;
    }

    public int getWriteDbId() {
        return writeDbId;
    }

    public void setWriteDbId(int writeDbId) {
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

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "active=" + active +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", writeDbId=" + writeDbId +
                '}';
    }
}
