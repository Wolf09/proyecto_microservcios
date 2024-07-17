package com.example.cuentamovimientosservice.models.services;
import com.example.cuentamovimientosservice.models.configurations.RabbitMQConfig;
import com.example.cuentamovimientosservice.models.entities.Cliente;
import com.example.cuentamovimientosservice.models.entities.Cuenta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    private final CuentaService cuentaService;

    @Autowired
    public RabbitMQReceiver(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NUEVO_CLIENTE)
    public void recibirNuevoCliente(Cliente cliente) {
        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setClienteId(cliente.getClienteId());
        nuevaCuenta.setNumeroCuenta(generarNumeroCuenta());
        nuevaCuenta.setTipoCuenta(Cuenta.TipoCuenta.AHORROS);
        nuevaCuenta.setSaldoInicial(0);
        nuevaCuenta.setEstado(true);

        cuentaService.saveCuenta(nuevaCuenta);
    }

    private String generarNumeroCuenta() {
        long numero = System.currentTimeMillis() / 10000000;

        return String.valueOf(numero);
    }
}
