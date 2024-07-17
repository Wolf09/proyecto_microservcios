package com.example.cuentamovimientosservice.models.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.cuentamovimientosservice.models.entities.Cuenta;
import com.example.cuentamovimientosservice.models.repositories.CuentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImplement implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    public List<Cuenta> getCuentasByClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
    @Override
    public Optional<Cuenta> getCuentaById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    @Transactional
    public Cuenta saveCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void deleteCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Cuenta findByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }
}
