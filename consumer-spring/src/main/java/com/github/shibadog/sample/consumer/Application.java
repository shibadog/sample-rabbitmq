package com.github.shibadog.sample.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("test-exchange");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public Queue queue() {
        return new Queue("test", false);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("test");
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(new HelloWorldHandler());
    }

    @Slf4j
    public static class HelloWorldHandler {
        public void handleMessage(String text) {
            log.info("Received: {}", text);
        }
    }
    
}
