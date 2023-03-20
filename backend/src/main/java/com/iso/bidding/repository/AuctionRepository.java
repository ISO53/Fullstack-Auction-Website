package com.iso.bidding.repository;

import com.iso.bidding.model.Auction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuctionRepository extends MongoRepository<Auction, String> {
    // Define custom finder methods here
}
