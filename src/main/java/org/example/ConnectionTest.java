package org.example;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionTest {

    private static final Logger log = (Logger) LogManager.getLogger(ConnectionTest.class);

    public static void main(String[] args) {
        String url = "tcp://localhost:61616";

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        try {
            Connection connection = connectionFactory.createConnection();
            log.info("Connection successful");
        } catch (Exception e) {
            log.info("Connection failed");
            e.printStackTrace();
    }
    }
}

