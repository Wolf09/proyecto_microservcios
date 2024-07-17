package com.example.cuentamovimientosservice.models.services;

import com.example.cuentamovimientosservice.models.dto.ReporteDTO;
import com.example.cuentamovimientosservice.models.entities.Movimiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MovimientoService {

    Movimiento realizarMovimiento(String numeroCuenta, Movimiento.TipoMovimiento tipoMovimiento, Integer valor);

    List<Movimiento> getMovimientosByClienteAndFecha(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Movimiento saveMovimiento(Movimiento movimiento);

    Optional<Movimiento> getMovimientoById(Long id);

    List<Movimiento> getAllMovimientos();


    void deleteMovimiento(Long id);

    List<ReporteDTO> obtenerReporte(Long idCliente, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
