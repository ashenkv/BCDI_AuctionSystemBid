package com.auction.ejb;

import com.auction.model.Sale;
import com.auction.model.Offer;
import com.auction.util.JMSUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class OfferProcessorBean {
    private static final Logger logger = LogManager.getLogger(OfferProcessorBean.class);

    @EJB
    private GlobalSaleManagerBean singletonBean;

    @Inject
    private JMSUtil jmsUtil;

    public boolean submitOffer(Long saleId, String bidder, double amount) throws RuntimeException {
        Sale sale = singletonBean.findSale(saleId);
        if (sale == null || !sale.isActive()) {
            logger.warn("Invalid or inactive sale: {}", saleId);
            return false;
        }
        if (amount <= sale.getCurrentPrice()) {
            logger.warn("Offer {} is not higher than current price {}", amount, sale.getCurrentPrice());
            return false;
        }

        sale.setCurrentPrice(amount);
        sale.setCurrentBidder(bidder);
        singletonBean.updateSale(sale);

        Offer offer = new Offer();
        offer.setSaleId(saleId);
        offer.setBidder(bidder);
        offer.setAmount(amount);
        offer.setOfferTime(java.time.LocalDateTime.now());
        singletonBean.recordOffer(saleId, offer);

        try {
            jmsUtil.sendOfferMessage(saleId, bidder, amount);
        } catch (RuntimeException e) {
            logger.error("Failed to send JMS message for offer on saleId={}", saleId, e);
            throw e;
        }

        logger.info("Offer submitted: saleId={}, bidder={}, amount={}", saleId, bidder, amount);
        return true;
    }
}