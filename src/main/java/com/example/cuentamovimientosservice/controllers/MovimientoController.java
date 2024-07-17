package com.example.cuentamovimientosservice.controllers;

import com.example.cuentamovimientosservice.models.dto.ReporteDTO;
import com.example.cuentamovimientosservice.models.entities.Movimiento;
import com.example.cuentamovimientosservice.models.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/movimientos")
    public List<Movimiento> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<Movimiento> getMovimientoById(@PathVariable Long id) {
        return movimientoService.getMovimientoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/movimientos")
    public ResponseEntity<Movimiento> createMovimiento(@RequestBody Movimiento movimiento) {
        try {
            Movimiento nuevoMovimiento = movimientoService.saveMovimiento(movimiento);
            return ResponseEntity.ok(nuevoMovimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/movimientos/{id}")
    public ResponseEntity<?> updateMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        return movimientoService.getMovimientoById(id)
                .map(existingMovimiento -> {
                    movimiento.setId(id);
                    try {
                        return ResponseEntity.ok(movimientoService.saveMovimiento(movimiento));
                    } catch (RuntimeException e) {
                        return ResponseEntity.badRequest().build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/realizar")
    public ResponseEntity<Movimiento> realizarMovimiento(
            @RequestParam String numeroCuenta,
            @RequestParam Movimiento.TipoMovimiento tipoMovimiento,
            @RequestParam Integer valor) {
        try {
            Movimiento movimiento = movimientoService.realizarMovimiento(numeroCuenta, tipoMovimiento, valor);
            return ResponseEntity.ok(movimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteDTO>> obtenerReporte(Long idCliente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaFin
    ) {
        List<ReporteDTO> reporte = movimientoService.obtenerReporte(idCliente,fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
