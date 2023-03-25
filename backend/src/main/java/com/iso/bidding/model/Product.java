package com.iso.bidding.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Product")
public class Product {

    @Id
    private ObjectId id;
    private String name;
    private String description;
    private boolean published;
    private String imageURL;

    public Product(String name, String description, boolean published, String imageURL) {
        this.id = new ObjectId();
        this.name = name;
        this.description = description;
        this.published = published;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", published=" + published +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
