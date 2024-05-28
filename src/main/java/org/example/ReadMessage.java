package org.example;


import org.apache.camel.builder.RouteBuilder;

public class ReadMessage extends RouteBuilder {

    @Override
    public void configure() {
        from("activemq:queue:my_queue")

                .process(exchange -> {
                    String message = exchange.getIn().getBody(String.class);
                    System.out.println("Message received: " + message);

                });

    }
}