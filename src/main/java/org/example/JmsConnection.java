package org.example;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JmsConnection {

    private static final Logger log = LogManager.getLogger(JmsConnection.class);


    public static void main(String[] args) throws Exception {


        try (CamelContext context = new DefaultCamelContext()) {

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL("tcp://localhost:61616");
            connectionFactory.setUserName("admin");
            connectionFactory.setPassword("admin");


            try {
                connectionFactory = new ActiveMQConnectionFactory();
                Connection connection = connectionFactory.createConnection();
                connection.start();
                log.info("Connection successful");
            } catch (JMSException e) {
                log.info("Connection failed");
            }

            context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

            FIleToQueue fileToQueue = new FIleToQueue();
            context.addRoutes(fileToQueue);
            context.start();


            Thread.sleep(10000);
            context.stop();


        }




    }
}
