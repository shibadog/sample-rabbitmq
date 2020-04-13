package com.github.shibadog.sample.consumer;

import java.util.concurrent.TimeUnit;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("user");
        factory.setPassword("password");
        factory.setHost("localhost");
        factory.setPort(5672);

        try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel()) {

            boolean autoAck = false;
            channel.basicConsume("test", autoAck, "", new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(java.lang.String consumerTag,
                        com.rabbitmq.client.Envelope envelope,
                        com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body)
                        throws java.io.IOException {
                    long deliveryTag = envelope.getDeliveryTag();
                    log.info("consume: {}", new String(body));
                    channel.basicAck(deliveryTag, false);
                }
            });

            TimeUnit.MINUTES.sleep(10L);
        }
    }
}
