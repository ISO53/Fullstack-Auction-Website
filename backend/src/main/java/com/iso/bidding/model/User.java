package com.iso.bidding.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "User")
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String surname;
    private String mail;
    private String password;

    public User(String name, String surname, String mail, String password) {
        this.id = new ObjectId();
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
