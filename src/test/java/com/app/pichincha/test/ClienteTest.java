package com.app.pichincha.test;

import com.app.pichincha.model.Cliente;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    void testClienteCreacion() {
        cliente.setNombre("Juan Perez");
        cliente.setDireccion("Calle 123");
        cliente.setTelefono("0987654321");
        cliente.setContrasena("password123");
        cliente.setEstado(true);

        assertEquals("Juan Perez", cliente.getNombre());
        assertEquals("Calle 123", cliente.getDireccion());
        assertEquals("0987654321", cliente.getTelefono());
        assertEquals("password123", cliente.getContrasena());
        assertTrue(cliente.getEstado());
    }
}
