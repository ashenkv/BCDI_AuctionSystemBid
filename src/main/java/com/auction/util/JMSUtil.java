package com.auction.util;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.*;
import jakarta.jms.IllegalStateException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@ApplicationScoped
public class JMSUtil {
    private static final Logger logger = LogManager.getLogger(JMSUtil.class);

    private final ConnectionFactory connectionFactory;

    @Resource(lookup = "activeMqQueue")
    private Queue queue;

    public JMSUtil() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        activeMQConnectionFactory.setUserName("admin");
        activeMQConnectionFactory.setPassword("admin");
        this.connectionFactory = (ConnectionFactory) activeMQConnectionFactory;
        logger.info("Initialized ActiveMQConnectionFactory with broker URL: tcp://localhost:61616");
    }

    public void sendOfferMessage(Long saleId, String bidder, double amount) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            logger.info("ConnectionFactory: {}", connectionFactory);
            logger.info("Queue: {}", queue);

            if (connectionFactory == null || queue == null) {
                logger.error("ConnectionFactory or Queue not available");
                throw new IllegalStateException("JMS resources not available");
            }

            logger.debug("Attempting to create JMS connection...");
            connection = connectionFactory.createConnection();
            logger.debug("JMS connection created successfully");
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            producer = session.createProducer(queue);

            String message = "Offer on sale " + saleId + " by " + bidder + " for " + amount;
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);

            logger.info("Sent JMS message: {}", message);
        } catch (JMSException e) {
            logger.error("JMS exception while sending message", e);
            throw new RuntimeException("Failed to send JMS message", e);
        } finally {

            try {
                if (producer != null) producer.close();
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException e) {
                logger.error("Error closing JMS resources", e);
            }
        }
    }

    public void broadcastUpdate(String message) {
        logger.info("Broadcasting update: {}", message);
    }
}