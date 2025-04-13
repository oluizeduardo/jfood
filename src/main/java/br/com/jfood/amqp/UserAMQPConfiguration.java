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
 * <strong>Queue:</strong> {@value #QUEUE_NAME} <br>
 * <strong>Routing Key:</strong> {@value #ROUTING_KEY}</p>
 *
 * @author luiz_costa
 */
@Configuration
public class UserAMQPConfiguration {

    public static final String EXCHANGE_NAME = "user.exchange";
    public static final String QUEUE_NAME = "user.created.queue";
    public static final String ROUTING_KEY = "user.created";

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
    public Queue creteQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * Binds the queue to the exchange using the specified routing key.
     *
     * @param queue    the declared queue
     * @param exchange the declared topic exchange
     * @return the Binding instance
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
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

