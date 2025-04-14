package br.com.jfood.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up AMQP (RabbitMQ) integration related to user events.
 * <p>
 * This configuration defines a topic exchange, a queue, and a routing key to handle
 * messages for newly created users. It also sets up a JSON message converter,
 * a RabbitTemplate for publishing messages, and initializes the RabbitMQ infrastructure
 * on application startup.
 * </p>
 *
 * <p><strong>Exchange:</strong> {@value #EXCHANGE_NAME} <br>
 * <strong>Queue:</strong> {@value #USER_CREATED_QUEUE_NAME} <br>
 * <strong>Routing Key:</strong> {@value #USER_CREATED_ROUTING_KEY}</p>
 *
 * @author luiz_costa
 */
@Configuration
public class UserAMQPConfiguration {

    public static final String EXCHANGE_NAME = "user.exchange";

    public static final String USER_CREATED_QUEUE_NAME = "user.created.queue";
    public static final String USER_CREATED_ROUTING_KEY = "user.created";

    public static final String USER_DELETED_QUEUE_NAME = "user.deleted.queue";
    public static final String USER_DELETED_ROUTING_KEY = "user.deleted";

    /**
     * Declares a topic exchange to route user-related messages.
     *
     * @return the TopicExchange instance
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * Declares the queue that will receive user creation events.
     *
     * @return the Queue instance
     */
    @Bean
    @Qualifier("userCreatedQueue")
    public Queue userCreatedQueue() {
        return new Queue(USER_CREATED_QUEUE_NAME, false);
    }

    /**
     * Declares the queue that will receive user deletion events.
     *
     * @return the Queue instance
     */
    @Bean
    @Qualifier("userDeletedQueue")
    public Queue userDeletedQueue() {
        return new Queue(USER_DELETED_QUEUE_NAME, false);
    }

    /**
     * Creates a binding between the {@code userCreatedQueue} and the {@code user.exchange} exchange
     * using the {@code user.created} routing key. This allows messages with the specified routing key
     * to be routed to the user creation queue.
     *
     * @param queue    the queue to bind (userCreatedQueue)
     * @param exchange the topic exchange to bind to
     * @return the configured Binding instance
     */
    @Bean
    public Binding bindingUserCreated(@Qualifier("userCreatedQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(USER_CREATED_ROUTING_KEY);
    }

    /**
     * Creates a binding between the {@code userDeletedQueue} and the {@code user.exchange} exchange
     * using the {@code user.deleted} routing key. This ensures that messages with this routing key
     * are delivered to the user deletion queue.
     *
     * @param queue    the queue to bind (userDeletedQueue)
     * @param exchange the topic exchange to bind to
     * @return the configured Binding instance
     */
    @Bean
    public Binding bindingUserDeleted(@Qualifier("userDeletedQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(USER_DELETED_ROUTING_KEY);
    }

    /**
     * Configures the message converter to use JSON for serialization and deserialization.
     *
     * @return the Jackson2JsonMessageConverter instance
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configures the RabbitTemplate used to send messages to RabbitMQ.
     *
     * @param connectionFactory the RabbitMQ connection factory
     * @return the configured RabbitTemplate instance
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    /**
     * Declares a RabbitAdmin bean to manage AMQP resources (queues, exchanges, bindings).
     *
     * @param conn the RabbitMQ connection factory
     * @return the RabbitAdmin instance
     */
    @Bean
    public RabbitAdmin creteRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    /**
     * Initializes RabbitAdmin when the application is ready, ensuring all AMQP
     * resources are declared at startup.
     *
     * @param rabbitAdmin the RabbitAdmin instance
     * @return an ApplicationListener that initializes RabbitAdmin
     */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> startRabbitAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}

