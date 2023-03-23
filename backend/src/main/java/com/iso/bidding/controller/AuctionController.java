package com.iso.bidding.controller;

import com.iso.bidding.model.Auction;
import com.iso.bidding.model.User;
import com.iso.bidding.repository.IAuctionRepository;
import com.iso.bidding.repository.IUserRepository;
import jakarta.websocket.server.ServerEndpoint;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    IAuctionRepository IAuctionRepository;

    @Autowired
    IUserRepository IUserRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok().body(IAuctionRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable("id") String id) {
        Optional<Auction> auctionOptional = IAuctionRepository.findById(new ObjectId(id));

        return auctionOptional.isPresent() ?
                new ResponseEntity<>(auctionOptional.get(), HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        System.out.println(auction);
        try {
            Auction _auction = IAuctionRepository.save(new Auction(
                    auction.getProductId(),
                    auction.getStartingPrice(),
                    auction.getMinimumRaise(),
                    auction.getPeriod()));
            return new ResponseEntity<>(_auction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable("id") String id, @RequestBody Auction auction) {
        Optional<Auction> auctionOptional = IAuctionRepository.findById(new ObjectId(id));

        if (!auctionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Auction _auction = auctionOptional.get();
        _auction.setId(auction.getId());
        _auction.setProductId(auction.getProductId());
        _auction.setStartingPrice(auction.getStartingPrice());
        _auction.setMinimumRaise(auction.getMinimumRaise());
        _auction.setStartTime(auction.getStartTime());
        _auction.setPeriod(auction.getPeriod());

        return new ResponseEntity<>(IAuctionRepository.save(_auction), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAuction(@PathVariable("id") String id) {
        try {
            IAuctionRepository.deleteById(new ObjectId(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllAuctions() {
        try {
            IAuctionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bid/{auctionId}/{userId}/{offer}")
    public ResponseEntity<String> bid(@PathVariable("auctionId") String auctionId, @PathVariable("userId") String userId, @PathVariable("offer") Double offer) {
        ResponseEntity<Auction> response = getAuctionById(auctionId);
        Auction auction = response.getBody();

        // Auction not found
        if (auction == null) {
            return new ResponseEntity<>("Auction not found!", HttpStatus.NOT_FOUND);
        }

        Optional<User> userOptional = IUserRepository.findById(userId);

        // User not found
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        boolean bidSuccess = auction.bid(userOptional.get(), offer);

        if (!bidSuccess) {
            return new ResponseEntity<>("Insufficient bid!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        updateAuction(auctionId, auction);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
