package org.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;



public class FIleToQueue extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:src/data")
                .convertBodyTo(String.class)
                .to("activemq:queue:my_queue")
                .onCompletion()
                .log("Message sent to queue")
                .end();
    }


}
