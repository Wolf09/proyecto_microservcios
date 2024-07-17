package com.example.clientepersonaservice.models.configurations;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_CLIENTE_CREATED = "clienteCreated";
    public static final String QUEUE_CLIENTE_READ = "clienteRead";
    public static final String QUEUE_CLIENTE_UPDATED = "clienteUpdated";
    public static final String QUEUE_CLIENTE_DELETED = "clienteDeleted";

    @Bean
    public Queue queueClienteCreated() {
        return new Queue(QUEUE_CLIENTE_CREATED, true);
    }

    @Bean
    public Queue queueClienteRead() {
        return new Queue(QUEUE_CLIENTE_READ, true);
    }

    @Bean
    public Queue queueClienteUpdated() {
        return new Queue(QUEUE_CLIENTE_UPDATED, true);
    }

    @Bean
    public Queue queueClienteDeleted() {
        return new Queue(QUEUE_CLIENTE_DELETED, true);
    }

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}