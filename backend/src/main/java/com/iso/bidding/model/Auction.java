package com.iso.bidding.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Auction")
public class Auction{

    @Id
    private ObjectId id;
    private ObjectId productId;
    private double startingPrice;
    private double currentBid;
    private ObjectId currentBidderId;
    private double minimumRaise;
    private long startTime;     // Unix format of time
    private long period;        // Unix format of time
    private boolean inProgress; // Is auction in progress?
    private boolean isFinished; // Is auction finished? (Can't be finished if it hasn't started)

    public Auction(ObjectId productId, double startingPrice, double minimumRaise, long period) {
        this.id = new ObjectId();
        this.productId = productId;
        this.startingPrice = startingPrice;
        this.currentBid = 0;
        this.currentBidderId = null;
        this.minimumRaise = minimumRaise;
        this.startTime = 0;
        this.period = period;
        this.inProgress = false;
        this.isFinished = false;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", productId=" + productId +
                ", startingPrice=" + startingPrice +
                ", currentBid=" + currentBid +
                ", currentBidderId=" + currentBidderId +
                ", minimumRaise=" + minimumRaise +
                ", startTime=" + startTime +
                ", period=" + period +
                ", inProgress=" + inProgress +
                ", isFinished=" + isFinished +
                '}';
    }

    public boolean bid(User user, double priceOffer) {
        if (priceOffer < currentBid + minimumRaise) {
            System.out.println("Not enough raise!");
            return false;
        }

        if (startTime + period < System.currentTimeMillis()) {
            System.out.println("Auction already ended");
            return false;
        }

        this.currentBid = priceOffer;
        this.currentBidderId = user.getId();
        return true;
    }
}
