package com.auction.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Sale implements Serializable {
    private Long id;
    private String itemName;
    private double currentPrice;
    private String currentBidder;
    private LocalDateTime endTime;
    private boolean active;

    public Sale() {
        this.active = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public String getCurrentBidder() { return currentBidder; }
    public void setCurrentBidder(String currentBidder) { this.currentBidder = currentBidder; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}