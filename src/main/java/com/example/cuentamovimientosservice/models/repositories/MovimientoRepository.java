package com.example.cuentamovimientosservice.models.repositories;

import com.example.cuentamovimientosservice.models.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m.fecha, c.numeroCuenta, c.tipoCuenta, c.saldoInicial, c.estado, " +
            "m.valor, m.saldoDisponible, c.clienteId " +
            "FROM Movimiento m JOIN m.cuenta c " +
            "WHERE m.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY m.fecha")
    List<Object[]> obtenerReporteMovimientos(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
    List<Movimiento> findByCuentaClienteIdAndFechaBetween(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
