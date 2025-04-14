package br.com.jfood.service;

import br.com.jfood.amqp.UserAMQPConfiguration;
import br.com.jfood.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserEventPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(UserEventPublisherService.class);

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void convertAndSend(final String exchange, final String routingKey, final UserEvent event) {
        Objects.requireNonNull(exchange, "Exchange must not be null.");
        Objects.requireNonNull(routingKey, "Routing key must not be null.");
        Objects.requireNonNull(event, "Event payload must not be null.");

        logger.info("Publishing event to exchange='{}' with routingKey='{}'.", exchange, routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }

    public void publishUserCreatedEvent(final UserEvent event) {
        convertAndSend(
                UserAMQPConfiguration.EXCHANGE_NAME,
                UserAMQPConfiguration.USER_CREATED_ROUTING_KEY,
                event
        );
    }

    public void publishUserDeletedEvent(final UserEvent event) {
        convertAndSend(
                UserAMQPConfiguration.EXCHANGE_NAME,
                UserAMQPConfiguration.USER_DELETED_ROUTING_KEY,
                event
        );
    }
}

