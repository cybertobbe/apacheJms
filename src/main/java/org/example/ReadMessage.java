package org.example;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class ReadMessage extends RouteBuilder {

    @Override
    public void configure() {
        from("activemq:queue:my_queue")
                .doTry()
                    .process(exchange -> {
                        String message = exchange.getIn().getBody(String.class);
                        System.out.println("Message received: " + message);
                        //throw new Exception("Test exception");
                    })
                .doCatch(Exception.class)
                    .process(exchange -> {
                        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println("Error processing message: " + e.getMessage());
                });
    }
}
