package com.example.clientepersonaservice.models.services;

import com.example.clientepersonaservice.models.entities.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> getAllClientes();

    Optional<Cliente> getClienteById(Long id);

    Cliente saveCliente(Cliente cliente);

    void deleteCliente(Long id);
}
