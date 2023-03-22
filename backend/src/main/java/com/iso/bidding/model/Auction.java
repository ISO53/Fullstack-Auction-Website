package com.iso.bidding.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Auction")
public class Auction implements Runnable {

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
    private Thread thread;

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
        this.thread = new Thread(this);
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
                ", thread=" + thread +
                '}';
    }

    @Override
    public void run() {
        while ((startTime + period > System.currentTimeMillis()) && inProgress) {
            // Wait. Auction is on progress...
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Auction has ended!");
        // Auction has ended by time or manually
        // close the "inProgress" just in case
        inProgress = false;

        // this.currentBidder won the auction with this.currentBid amount of money
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.inProgress = true;
        this.thread.start();
    }

    public void end() {
        inProgress = false;
    }

    public boolean bid(User user, double priceOffer) {
        if (priceOffer < currentBid + minimumRaise) {
            System.out.println("Not enough raise!");
            return false;
        }

        this.currentBid = priceOffer;
        this.currentBidderId = user.getId();
        return true;
    }
}
