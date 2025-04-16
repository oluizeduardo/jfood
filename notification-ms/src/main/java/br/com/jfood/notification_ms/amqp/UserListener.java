package br.com.jfood.notification_ms.amqp;

import br.com.jfood.notification_ms.dto.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    private static final Logger logger = LoggerFactory.getLogger(UserListener.class);

    private final String USER_CREATED_QUEUE_NAME = "user.created.queue";
    private final String USER_DELETED_QUEUE_NAME = "user.deleted.queue";

    @RabbitListener(queues = USER_CREATED_QUEUE_NAME)
    public void listenToUserCreatedEvent(UserEvent event) {
        logger.info("Sending welcome email to [{}].", event.getEmail());
    }

    @RabbitListener(queues = USER_DELETED_QUEUE_NAME)
    public void listenToUserDeletedEvent(UserEvent event) {
        logger.info("Sending account deletion confirmation email to [{}].", event.getEmail());
    }
}
