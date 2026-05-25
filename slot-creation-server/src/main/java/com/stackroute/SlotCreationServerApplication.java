package com.stackroute;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan("com.stackroute.grpc")
public class SlotCreationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlotCreationServerApplication.class, args);

    }

}
