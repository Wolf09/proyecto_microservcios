package com.example.clientepersonaservice.models.services;

import com.example.clientepersonaservice.models.dto.CuentaDTO;
import com.example.clientepersonaservice.models.entities.Cliente;
import com.example.clientepersonaservice.models.feign.CuentaClient;
import com.example.clientepersonaservice.models.repositories.ClienteRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImplent implements ClienteService{

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaClient cuentaClient;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach(this::fetchCuentas);
        return clientes;
    }

    @Override
    public Optional<Cliente> getClienteById(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        clienteOpt.ifPresent(c -> {rabbitMQSender.sendClienteRead(c);
        fetchCuentas(c);});
        return clienteOpt;
    }

    @Bulkhead(name = "cuentaService")
    private void fetchCuentas(Cliente cliente) {
        try {
            List<CuentaDTO> cuentas = cuentaClient.getCuentasByClienteId(cliente.getClienteId());
            cliente.setCuentas(cuentas);
        } catch (Exception e) {
            logger.error("Error al consumir las cuentas del cliente: "+ cliente.getClienteId()+" con el mensaje: " +e.getMessage());
            cliente.setCuentas(List.of());
        }
    }
    @Override
    public Cliente saveCliente(Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        if (cliente.getClienteId()==null){
            rabbitMQSender.sendClienteCreated(nuevoCliente);
        }
        nuevoCliente.setContrasenia(passwordEncoder.encode(cliente.getContrasenia()));
        rabbitMQSender.sendClienteUpdated(nuevoCliente);

        return nuevoCliente;
    }
    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
        rabbitMQSender.sendClienteDeleted(id);
    }
}
