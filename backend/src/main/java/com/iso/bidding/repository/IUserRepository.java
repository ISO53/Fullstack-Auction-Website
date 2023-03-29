package com.iso.bidding.repository;

import com.iso.bidding.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    // Define custom finder methods here
    User findByMail(String mail);
    User findByMailAndPassword(String mail, String password);
    Optional<User> findById(ObjectId objectId);
    void deleteById(ObjectId objectId);
}
