package com.example.clientepersonaservice.models.services;


import com.example.clientepersonaservice.models.configurations.RabbitMQConfig;
import com.example.clientepersonaservice.models.entities.Cliente;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendClienteCreated(Cliente cliente) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_CLIENTE_CREATED, cliente);
    }

    public void sendClienteRead(Cliente cliente) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_CLIENTE_READ, cliente);
    }

    public void sendClienteUpdated(Cliente cliente) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_CLIENTE_UPDATED, cliente);
    }

    public void sendClienteDeleted(Long clienteId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_CLIENTE_DELETED, clienteId);
    }
}