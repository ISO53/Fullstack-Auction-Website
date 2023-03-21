package com.iso.bidding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "User")
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String surname;
    private String mail;
    private String password;
}
