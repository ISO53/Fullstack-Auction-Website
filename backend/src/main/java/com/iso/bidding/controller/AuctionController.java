package com.iso.bidding.controller;

import com.iso.bidding.model.Auction;
import com.iso.bidding.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    AuctionRepository auctionRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<Auction>> getAllAuctions(@RequestParam(required = false) String title) {
        return ResponseEntity.ok().body(auctionRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable("id") String id) {
        Optional<Auction> auctionOptional = auctionRepository.findById(id);

        return auctionOptional.isPresent() ?
                new ResponseEntity<>(auctionOptional.get(), HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        try {
            Auction _auction = auctionRepository.save(new Auction(
                    auction.getId(),
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
        Optional<Auction> auctionOptional = auctionRepository.findById(id);

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

        return new ResponseEntity<>(auctionRepository.save(_auction), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAuction(@PathVariable("id") String id) {
        try {
            auctionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllAuctions() {
        try {
            auctionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
