package com.app.pichincha.test;

import static org.junit.jupiter.api.Assertions.*;

import com.app.pichincha.model.Cliente;
import com.app.pichincha.model.Cuenta;
import com.app.pichincha.repository.ClienteRepository;
import com.app.pichincha.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class CuentaRepositoryIntegrationTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testFindByNumeroCuenta() {

        Cliente cliente = new Cliente();
        cliente.setNombre("Martin Giraldo");
        cliente.setDireccion("Medellin Envigado");
        cliente.setTelefono("12345");
        cliente.setContrasena("5678");
        cliente.setEstado(true);
        clienteRepository.save(cliente);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1111111");
        cuenta.setSaldoInicial(5000.0);
        cuenta.setTipo("Ahorro");
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);
        cuentaRepository.save(cuenta);

        Optional<Cuenta> cuentaRecuperada = cuentaRepository.findByNumeroCuenta("1111111");


        assertTrue(cuentaRecuperada.isPresent(),"Cuenta no fue creada");
        assertEquals("1111111", cuentaRecuperada.get().getNumeroCuenta());
        assertEquals("Ahorro", cuentaRecuperada.get().getTipo());
        assertEquals(5000.0, cuentaRecuperada.get().getSaldoInicial());
        assertTrue(cuentaRecuperada.get().getEstado());
    }
}
