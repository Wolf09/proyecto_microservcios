package com.example.cuentamovimientosservice.models.services;

import com.example.cuentamovimientosservice.models.entities.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {
    List<Cuenta> getAllCuentas();

    Optional<Cuenta> getCuentaById(Long id);

    Cuenta saveCuenta(Cuenta cuenta);

    void deleteCuenta(Long id);

    Cuenta findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> getCuentasByClienteId(Long clienteId);
}
