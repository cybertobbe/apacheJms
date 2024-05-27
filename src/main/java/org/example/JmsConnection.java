package org.example;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.jms.JmsComponent;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class JmsConnection {

    private static final Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);


    public static void main(String[] args) throws Exception {

        String url = "tcp://localhost:61616";
        String userName = "admin";
        String password = "admin";



        try (CamelContext context = new DefaultCamelContext()) {

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL("tcp://localhost:61616");
            connectionFactory.setUserName("admin");
            connectionFactory.setPassword("admin");


            try {
                connectionFactory = new ActiveMQConnectionFactory();
                Connection connection = (Connection) connectionFactory.createConnection();
                connection.start();
                log.info("Connection successful");
            } catch (JMSException e) {
                log.info("Connection failed");
            }

            context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

            FIleToQueue fileToQueue = new FIleToQueue();
            context.addRoutes(new FIleToQueue());
            context.start();
            fileToQueue.readMessage(context);

            Thread.sleep(6000);
            context.stop();


        }




    }
}
