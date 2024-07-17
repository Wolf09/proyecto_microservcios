package com.example.cuentamovimientosservice.models.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NUEVO_CLIENTE = "nuevoCliente";

    @Bean
    public Queue queueNuevoCliente() {
        return new Queue(QUEUE_NUEVO_CLIENTE, true);
    }
}
