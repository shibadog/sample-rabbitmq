package com.github.shibadog.sample.publisher;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    Queue queue() {
        return new Queue("test", false);
    }

    private final RabbitTemplate template;
    public Application(RabbitTemplate template) {
        this.template = template;
    }

    @GetMapping(value="/add")
    public String add() {
        template.convertAndSend("test", "testString");
        return "OK";
    }
}
