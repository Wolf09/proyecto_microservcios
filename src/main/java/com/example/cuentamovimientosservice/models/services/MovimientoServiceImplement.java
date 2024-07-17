package com.example.cuentamovimientosservice.models.services;


import com.example.cuentamovimientosservice.models.dto.ReporteDTO;
import com.example.cuentamovimientosservice.models.entities.Cliente;
import com.example.cuentamovimientosservice.models.entities.Cuenta;
import com.example.cuentamovimientosservice.models.entities.Movimiento;
import com.example.cuentamovimientosservice.models.excepcions.SaldoInsuficienteException;
import com.example.cuentamovimientosservice.models.feign.ClienteFeignClient;
import com.example.cuentamovimientosservice.models.repositories.CuentaRepository;
import com.example.cuentamovimientosservice.models.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImplement implements MovimientoService{
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ClienteFeignClient clienteFeignClient;

    @Override
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Optional<Movimiento> getMovimientoById(Long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    @Transactional
    public Movimiento saveMovimiento(Movimiento movimiento) {
        Cuenta cuenta = movimiento.getCuenta();
        if (cuenta == null) {
            throw new RuntimeException("Cuenta no especificada para el movimiento");
        }

        Integer nuevoSaldo;
        if (movimiento.getTipoMovimiento() == Movimiento.TipoMovimiento.DEPOSITO) {
            nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
        } else {
            if (cuenta.getSaldoInicial() < movimiento.getValor()) {
                throw new SaldoInsuficienteException();
            }
            nuevoSaldo = cuenta.getSaldoInicial() - movimiento.getValor();
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaService.saveCuenta(cuenta);

        movimiento.setSaldoDisponible(nuevoSaldo);
        movimiento.setFecha(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    @Override
    @Transactional
    public void deleteMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
    @Override
    @Transactional
    public Movimiento realizarMovimiento(String numeroCuenta, Movimiento.TipoMovimiento tipoMovimiento, Integer valor) {
        Cuenta cuenta = cuentaService.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        Integer nuevoSaldo;
        if (tipoMovimiento == Movimiento.TipoMovimiento.DEPOSITO) {
            nuevoSaldo = cuenta.getSaldoInicial() + valor;
        } else {
            if (cuenta.getSaldoInicial() < valor) {
                throw new RuntimeException("Saldo no disponible");
            }
            nuevoSaldo = cuenta.getSaldoInicial() - valor;
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaService.saveCuenta(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setValor(valor);
        movimiento.setSaldoDisponible(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<ReporteDTO> obtenerReporte(Long idCliente,LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Object[]> resultados = movimientoRepository.obtenerReporteMovimientos(
                fechaInicio,
                fechaFin);

        return resultados.stream()
                .map(res -> this.mapToReporteDTO(idCliente,res))
                .collect(Collectors.toList());
    }

    private ReporteDTO mapToReporteDTO(Long id, Object[] resultado) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setFecha((LocalDateTime) resultado[0]);
        reporteDTO.setNumeroCuenta((String) resultado[1]);
        reporteDTO.setTipoCuenta((String) resultado[2]);
        reporteDTO.setSaldoInicial((Integer) resultado[3]);
        reporteDTO.setEstado((Boolean) resultado[4]);
        reporteDTO.setValorMovimiento((Integer) resultado[5]);
        reporteDTO.setSaldoDisponible((Integer) resultado[6]);

        // Obtener el nombre del cliente usando Feign Client
        Cliente cliente = clienteFeignClient.obtenerCliente(id);
        reporteDTO.setNombreCliente(cliente.getNombre());

        return reporteDTO;
    }

    @Override
    public List<Movimiento> getMovimientosByClienteAndFecha(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByCuentaClienteIdAndFechaBetween(clienteId, fechaInicio, fechaFin);
    }
}
