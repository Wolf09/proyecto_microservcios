package com.example.cuentamovimientosservice.models.feign;

import com.example.cuentamovimientosservice.models.entities.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-persona-service")
public interface ClienteFeignClient {

    @GetMapping("/clientes/{id}")
    Cliente obtenerCliente(@PathVariable("id") Long id);
}
