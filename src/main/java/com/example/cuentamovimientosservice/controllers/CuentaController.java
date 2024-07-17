package com.example.cuentamovimientosservice.controllers;

import com.example.cuentamovimientosservice.models.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cuentamovimientosservice.models.entities.Cuenta;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cuenta>> getCuentasByClienteId(@PathVariable Long clienteId) {
        List<Cuenta> cuentas = cuentaService.getCuentasByClienteId(clienteId);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        return cuentaService.getCuentaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.saveCuenta(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.getCuentaById(id)
                .map(existingCuenta -> {
                    cuenta.setId(id);
                    return ResponseEntity.ok(cuentaService.saveCuenta(cuenta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
