package com.app.pichincha.service;

import com.app.pichincha.DTO.MovientoDTO;
import com.app.pichincha.exception.*;
import com.app.pichincha.model.Cliente;
import com.app.pichincha.model.Cuenta;
import com.app.pichincha.model.Movimiento;
import com.app.pichincha.repository.ClienteRepository;
import com.app.pichincha.repository.CuentaRepository;
import com.app.pichincha.repository.MovimientoRepository;
import com.app.pichincha.util.Constantes;
import com.app.pichincha.util.Utilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public void crearMovimientos(MovientoDTO movientoDTO) {
        Cuenta cuenta = cuentaRepository.findById(movientoDTO.getIdCuenta())
                .orElseThrow(() -> new CuentasNoEncontradasException(Constantes.CUENTAS_NO_ENCONTRADAS));

        Double saldoActual = obtenerSaldoActual(cuenta);
        double saldoFinal = calcularSaldo(saldoActual, movientoDTO.getOperacion(), movientoDTO.getValorOperacion());

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipo(movientoDTO.getTipo());
        movimiento.setOperacion(movientoDTO.getOperacion());
        movimiento.setValorOperacion(movientoDTO.getValorOperacion());
        movimiento.setSaldo(saldoFinal);

        movimientoRepository.save(movimiento);
    }


    public List<MovientoDTO> listarMovimientosPorRangoDeFechas(Date fechaInicio, Date fechaFin) {
        validarRangoDeFechas(fechaInicio, fechaFin);

        List<Movimiento> movimientos = movimientoRepository.findByFecha(
                Utilitario.convertirADateTime(fechaInicio),
                Utilitario.convertirADateTimeFinDelDia(fechaFin)
        );

        if (movimientos.isEmpty()) {
            throw new MovimientosNoEncontradosException(Constantes.MOVIMIENTOS_NO_ENCONTRADOS);
        }

        return convertirAMovimientoDTOs(movimientos);
    }


    public List<MovientoDTO> listarMovimientosByIdPersona(Long idPersona) {
        Cliente cliente = clienteRepository.findById(idPersona)
                .orElseThrow(() -> new ClienteNoEncontradoException(Constantes.CLIENTE_NO_ENCONTRADO));

        List<Cuenta> listaCuentas = cuentaRepository.findByCliente(cliente);
        if (listaCuentas.isEmpty()) {
            throw new CuentasNoEncontradasException(Constantes.CUENTAS_NO_ENCONTRADAS);
        }

        List<Movimiento> listaMovimientos = new ArrayList<>();
        for (Cuenta cuenta : listaCuentas) {
            listaMovimientos.addAll(movimientoRepository.findByCuenta_CuentaId(cuenta.getCuentaId()));
        }

        if (listaMovimientos.isEmpty()) {
            throw new MovimientosNoEncontradosException(Constantes.MOVIMIENTOS_NO_ENCONTRADOS);
        }

        return convertirAMovimientoDTOs(listaMovimientos);
    }


    private Double obtenerSaldoActual(Cuenta cuenta) {
        try {
            Movimiento ultimoMovimiento = movimientoRepository.findTopByCuentaOrderByMovimientosIdDesc(cuenta);
            return (ultimoMovimiento != null) ? ultimoMovimiento.getSaldo() : cuenta.getSaldoInicial();
        } catch (DataAccessException e) {
            throw new SaldoActualException("Error al obtener el saldo actual para la cuenta ID: " + cuenta.getCuentaId(), e);
        }
    }


    private double calcularSaldo(double saldoActual, String operacion, double valorOperacion) {
        if (operacion.equalsIgnoreCase(Constantes.DEPOSITO)) {
            return saldoActual + valorOperacion;
        } else if (operacion.equalsIgnoreCase(Constantes.RETIRO)) {
            if (saldoActual < valorOperacion) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro");
            }
            return saldoActual - valorOperacion;
        } else {
            throw new IllegalArgumentException("Operación no válida: " + operacion);
        }
    }


    private List<MovientoDTO> convertirAMovimientoDTOs(List<Movimiento> movimientos) {
        Map<Integer, Double> saldoAnteriorPorCuenta = new HashMap<>();

        return movimientos.stream().map(movimiento -> {
            MovientoDTO movientoDTO = new MovientoDTO();
            Cliente cliente = movimiento.getCuenta().getCliente();
            Cuenta cuenta = movimiento.getCuenta();

            Double saldoAnterior = saldoAnteriorPorCuenta.getOrDefault(cuenta.getCuentaId(), cuenta.getSaldoInicial());

            movientoDTO.setNombreCliente(cliente.getNombre());
            movientoDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
            movientoDTO.setTipo(movimiento.getTipo());
            movientoDTO.setOperacion(movimiento.getOperacion());
            movientoDTO.setValorOperacion(movimiento.getValorOperacion());
            movientoDTO.setSaldoActual(movimiento.getSaldo());
            movientoDTO.setEstado(cuenta.getEstado());
            movientoDTO.setIdCuenta(Long.valueOf(cuenta.getCuentaId()));
            movientoDTO.setSaldoInicial(saldoAnterior);

            saldoAnteriorPorCuenta.put(cuenta.getCuentaId(), movimiento.getSaldo());

            if (movimiento.getCreatedAt() != null) {
                movientoDTO.setFecha(Utilitario.formatearFecha(movimiento.getCreatedAt()));
            }

            return movientoDTO;
        }).collect(Collectors.toList());
    }



    private void validarRangoDeFechas(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new FechaInvalidaException(Constantes.FECHAS_NULAS_O_INVALIDAS);
        }
        if (fechaInicio.after(fechaFin)) {
            throw new FechaInvalidaException(Constantes.FECHA_INICIO_POSTERIOR);
        }
    }
}
