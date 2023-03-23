package com.iso.bidding;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.iso.bidding.utils.WebSocket;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BiddingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiddingApplication.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer()
    {
        return builder -> builder.serializerByType(ObjectId.class,new ToStringSerializer());
    }
}
