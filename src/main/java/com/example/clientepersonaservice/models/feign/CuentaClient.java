package com.example.clientepersonaservice.models.feign;


import com.example.clientepersonaservice.models.dto.CuentaDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cuenta-movimiento-service")
public interface CuentaClient {

    @GetMapping("/cuentas/cliente/{clienteId}")
    @CircuitBreaker(name = "cuentaService", fallbackMethod = "getCuentasFallback")
    @Retry(name = "cuentaService")
    List<CuentaDTO> getCuentasByClienteId(@PathVariable("clienteId") Long clienteId);

    default List<CuentaDTO> getCuentasFallback(Long clienteId, Throwable throwable) {
        return List.of(); // Retorna una lista vacía
    }
}
