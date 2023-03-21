package com.iso.bidding.repository;

import com.iso.bidding.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // Define custom finder methods here
    User findByMail(String mail);
    User findByMailAndPassword(String mail, String password);
}
