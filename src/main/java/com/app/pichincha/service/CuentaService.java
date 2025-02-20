package com.app.pichincha.service;

import com.app.pichincha.DTO.CuentaDTO;
import com.app.pichincha.DTO.UsuarioDTO;
import com.app.pichincha.exception.ClienteNoEncontradoException;
import com.app.pichincha.exception.CuentaDuplicadaException;
import com.app.pichincha.exception.CuentasNoEncontradasException;
import com.app.pichincha.model.Cliente;
import com.app.pichincha.model.Cuenta;
import com.app.pichincha.repository.ClienteRepository;
import com.app.pichincha.repository.CuentaRepository;
import com.app.pichincha.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public void crearCuenta(CuentaDTO cuentaDTO) {
        Cliente cliente = clienteRepository.findById(cuentaDTO.getIdPersona())
                .orElseThrow(() -> new ClienteNoEncontradoException(Constantes.CLIENTE_NO_ENCONTRADO));

        validarCuentaExistente(cuentaDTO.getNumeroCuenta(), cliente);

        Cuenta cuenta = mapearCuentaDesdeDTO(cuentaDTO, cliente);
        cuentaRepository.save(cuenta);
    }


    public List<CuentaDTO> listarAllCuenta() {
        List<Cuenta> listaCuenta = cuentaRepository.findAll();
        if (listaCuenta.isEmpty()) {
            throw new CuentasNoEncontradasException(Constantes.CUENTAS_NO_ENCONTRADAS);
        }

        return listaCuenta.stream()
                .map(this::mapearCuentaADTO)
                .collect(Collectors.toList());
    }


    public List<CuentaDTO> listarCuentaByUsuario(Long idPersona) {
        Cliente cliente = clienteRepository.findById(idPersona)
                .orElseThrow(() -> new ClienteNoEncontradoException(Constantes.CLIENTE_NO_ENCONTRADO));

        List<Cuenta> listaCuenta = cuentaRepository.findByCliente(cliente);
        if (listaCuenta.isEmpty()) {
            throw new CuentasNoEncontradasException(Constantes.CUENTAS_NO_ENCONTRADAS);
        }

        return listaCuenta.stream()
                .map(this::mapearCuentaADTO)
                .collect(Collectors.toList());
    }


    public CuentaDTO listarCuentaPorNumeroCuenta(String numeroCuenta) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentasNoEncontradasException(Constantes.CUENTA_NO_ENCONTRADA));

        return mapearCuentaADTO(cuenta);
    }



    private void validarCuentaExistente(String numeroCuenta, Cliente cliente) {
        if (cuentaRepository.existsByNumeroCuentaAndCliente(numeroCuenta, cliente)) {
            throw new CuentaDuplicadaException(Constantes.CUENTAS_REGISTRADA);
        }
    }


    private Cuenta mapearCuentaDesdeDTO(CuentaDTO cuentaDTO, Cliente cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipo(cuentaDTO.getTipo());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());
        cuenta.setCliente(cliente);
        return cuenta;
    }


    private CuentaDTO mapearCuentaADTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setCuentaId(cuenta.getCuentaId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipo(cuenta.getTipo());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.getEstado());
        cuentaDTO.setIdPersona(Long.valueOf(cuenta.getCliente().getIdPersona()));
        return cuentaDTO;
    }


    public CuentaDTO updateCuentaById(Long idCuenta, CuentaDTO cuentaDTO) {

        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentasNoEncontradasException(Constantes.CUENTA_NO_ENCONTRADA));


        actualizarCamposDeCuenta(cuenta, cuentaDTO);
        cuentaRepository.save(cuenta);

        return mapearCuentaADTO(cuenta);
    }

    private void actualizarCamposDeCuenta(Cuenta cuenta, CuentaDTO cuentaDTO) {
        if (cuentaDTO.getNumeroCuenta() != null) {
            cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        }
        if (cuentaDTO.getTipo() != null) {
            cuenta.setTipo(cuentaDTO.getTipo());
        }
        if (cuentaDTO.getSaldoInicial() != null) {
            cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        }
        if (cuentaDTO.getEstado() != null) {
            cuenta.setEstado(cuentaDTO.getEstado());
        }
    }

}
