package com.auction.ejb;

import com.auction.model.Sale;
import com.auction.model.Offer;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
@Startup
public class GlobalSaleManagerBean {
    private Map<Long, Sale> sales = new HashMap<>();
    private Map<Long, List<Offer>> offers = new HashMap<>();
    private AtomicLong saleIdGenerator = new AtomicLong(1);
    private AtomicLong offerIdGenerator = new AtomicLong(1);

    public Sale initiateSale(String itemName, double startPrice, LocalDateTime endTime) {
        Sale sale = new Sale();
        sale.setId(saleIdGenerator.getAndIncrement());
        sale.setItemName(itemName);
        sale.setCurrentPrice(startPrice);
        sale.setEndTime(endTime);
        sale.setActive(true);
        sales.put(sale.getId(), sale);
        offers.put(sale.getId(), new ArrayList<>());
        return sale;
    }

    public List<Sale> getOngoingSales() {
        List<Sale> ongoingSales = sales.values().stream()
                .filter(Sale::isActive)
                .toList();
        System.out.println("Ongoing sales count: " + ongoingSales.size());
        return ongoingSales;
    }

    public Sale findSale(Long id) {
        return sales.get(id);
    }

    public void updateSale(Sale sale) {
        sales.put(sale.getId(), sale);
    }

    public void recordOffer(Long saleId, Offer offer) {
        offer.setId(offerIdGenerator.getAndIncrement());
        offers.computeIfAbsent(saleId, k -> new ArrayList<>()).add(offer);
    }

    public List<Offer> getOffersForSale(Long saleId) {
        return offers.getOrDefault(saleId, new ArrayList<>());
    }

    public void checkSaleTimeouts() {
        LocalDateTime now = LocalDateTime.now();
        sales.values().stream()
                .filter(sale -> sale.isActive() && now.isAfter(sale.getEndTime()))
                .forEach(sale -> sale.setActive(false));
    }
}