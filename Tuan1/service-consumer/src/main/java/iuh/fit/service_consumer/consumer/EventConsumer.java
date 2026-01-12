package iuh.fit.consumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import iuh.fit.consumer.config.RabbitMQConfig;

@Component
public class EventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveEvent(String message) {
        System.out.println("Received event: " + message);
        // xử lý nghiệp vụ ở đây
    }
}
