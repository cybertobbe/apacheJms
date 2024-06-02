package org.example;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.jms.JmsComponent;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JmsConnection {

    private static final Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);


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


            ReadMessage readMessage = new ReadMessage();
            context.addRoutes(readMessage);
            context.start();

            Thread.sleep(10000);
            context.stop();


        }




    }
}
