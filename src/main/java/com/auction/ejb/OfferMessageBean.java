package com.auction.ejb;

import com.auction.util.JMSUtil;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activeMqQueue"),
        @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar-6.1.6")
})
public class OfferMessageBean implements MessageListener {
    private static final Logger logger = LogManager.getLogger(OfferMessageBean.class);

    @Inject
    private JMSUtil jmsUtil;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                logger.info("Received offer message: {}", text);
                jmsUtil.broadcastUpdate(text);
            }
        } catch (JMSRuntimeException e) {
            logger.error("Error processing message", e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}