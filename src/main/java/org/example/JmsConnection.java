package org.example;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
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
                //System.out.println("Connection failed");
                log.info("Connection failed");
            }

            context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    from("activemq:queue:my_queue")
                            .to("file:src/data");
                }
            });
            context.start();
            try (ProducerTemplate template = context.createProducerTemplate()) {
                template.sendBody("activemq:queue:my_queue", "Hello World");
            }

            Thread.sleep(6000);
            context.stop();


        }




    }
}
