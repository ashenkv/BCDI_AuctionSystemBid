package com.auction.ejb;

import com.auction.model.Sale;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class SaleControllerBean {
    @EJB
    private GlobalSaleManagerBean singletonBean;

    public Sale initiateSale(String itemName, double startPrice, LocalDateTime endTime) {
        Sale sale = singletonBean.initiateSale(itemName, startPrice, endTime);
        System.out.println("Sale created with ID: " + sale.getId());
        return sale;
    }

    public List<Sale> getOngoingSales() {
        return singletonBean.getOngoingSales();
    }

    public Sale findSale(Long id) {
        return singletonBean.findSale(id);
    }
}