package com.auction.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Offer implements Serializable {
    private Long id;
    private Long saleId;
    private String bidder;
    private double amount;
    private LocalDateTime offerTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSaleId() { return saleId; }
    public void setSaleId(Long saleId) { this.saleId = saleId; }
    public String getBidder() { return bidder; }
    public void setBidder(String bidder) { this.bidder = bidder; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getOfferTime() { return offerTime; }
    public void setOfferTime(LocalDateTime offerTime) { this.offerTime = offerTime; }
}