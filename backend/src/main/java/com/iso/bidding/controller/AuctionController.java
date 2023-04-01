package com.iso.bidding.controller;

import com.iso.bidding.model.Auction;
import com.iso.bidding.model.User;
import com.iso.bidding.repository.IAuctionRepository;
import com.iso.bidding.repository.IUserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private IAuctionRepository iAuctionRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/getAll")
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok().body(iAuctionRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable("id") String id) {
        Optional<Auction> auctionOptional = iAuctionRepository.findById(new ObjectId(id));

        return auctionOptional.isPresent() ?
                new ResponseEntity<>(auctionOptional.get(), HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        System.out.println(auction);
        try {
            Auction _auction = iAuctionRepository.save(new Auction(
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
        Optional<Auction> auctionOptional = iAuctionRepository.findById(new ObjectId(id));

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
        _auction.setCurrentBid(auction.getCurrentBid());
        _auction.setCurrentBidderId(auction.getCurrentBidderId());
        _auction.setInProgress(auction.isInProgress());
        _auction.setFinished(auction.isFinished());

        return new ResponseEntity<>(iAuctionRepository.save(_auction), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAuction(@PathVariable("id") String id) {
        try {
            iAuctionRepository.deleteById(new ObjectId(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllAuctions() {
        try {
            iAuctionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bid/{auctionId}/{userId}/{offer}")
    public ResponseEntity<HttpStatus> bid(@PathVariable("auctionId") String auctionId, @PathVariable("userId") String userId, @PathVariable("offer") Double offer) {
        ResponseEntity<Auction> response = getAuctionById(auctionId);
        Auction auction = response.getBody();

        // Auction not found
        if (auction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<User> optionalUser = iUserRepository.findById(userId);


        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean bidSuccess = auction.bid(optionalUser.get(), offer);

        if (!bidSuccess) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<Auction> responseEntity = updateAuction(auctionId, auction);
        if (responseEntity.getStatusCode().isError()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Bid success mean data changed in the auctions. Frontend must be signaled
        messagingTemplate.convertAndSend("/bid/update", responseEntity.getBody());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
