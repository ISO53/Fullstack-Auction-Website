package com.iso.bidding.controller;

import com.iso.bidding.model.User;
import com.iso.bidding.repository.IUserRepository;
import com.iso.bidding.utils.Encrypter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserRepository IUserRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(IUserRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        Optional<User> userOptional = IUserRepository.findById(new ObjectId(id));

        return userOptional.isPresent() ?
                new ResponseEntity<>(userOptional.get(), HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        // Username and surname is not sent correctly.
        if ((user.getName() + user.getSurname()).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // User mail is not sent correctly.
        if (user.getMail() == null || user.getMail().equals("")) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // There already is a user registered by this mail
        if (IUserRepository.findByMail(user.getMail()) != null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        // User password is not sent correctly.
        if (user.getPassword() == null || user.getPassword().equals("")) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            User _user = IUserRepository.save(new User(
                    user.getName(),
                    user.getSurname(),
                    user.getMail(),
                    Encrypter.encryptPassword(user.getPassword())
            ));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        Optional<User> userOptional = IUserRepository.findById(new ObjectId(id));

        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User _user = userOptional.get();
        _user.setId(user.getId());
        _user.setName(user.getName());
        _user.setSurname(user.getSurname());
        _user.setMail(user.getMail());
        _user.setPassword(user.getPassword());

        return new ResponseEntity<>(IUserRepository.save(_user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
        try {
            IUserRepository.deleteById(new ObjectId(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            IUserRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/login/")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        User user = IUserRepository.findByMailAndPassword(email ,Encrypter.encryptPassword(password));

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

