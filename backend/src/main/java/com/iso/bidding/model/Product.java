package com.iso.bidding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Product")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private boolean published;
    private String imageURL;
}
