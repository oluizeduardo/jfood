package br.com.jfood.service;

import br.com.jfood.amqp.UserAMQPConfiguration;
import br.com.jfood.model.UserCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(UserCreatedEvent event) {
        String exchange = UserAMQPConfiguration.EXCHANGE_NAME;
        String routingKey = UserAMQPConfiguration.ROUTING_KEY;
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}

