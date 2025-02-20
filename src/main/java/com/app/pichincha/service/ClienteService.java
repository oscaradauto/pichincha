package com.app.pichincha.service;

import com.app.pichincha.DTO.UsuarioDTO;
import com.app.pichincha.exception.ClienteNoEncontradoException;
import com.app.pichincha.model.Cliente;
import com.app.pichincha.model.Cuenta;
import com.app.pichincha.repository.ClienteRepository;
import com.app.pichincha.repository.CuentaRepository;
import com.app.pichincha.repository.MovimientoRepository;
import com.app.pichincha.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;


    public void crearCliente(UsuarioDTO usuarioDTO) {
        Cliente cliente = mapearClienteDesdeDTO(usuarioDTO);
        clienteRepository.save(cliente);
    }


    public List<UsuarioDTO> listarAllClientes() {
        List<Cliente> listaCliente = clienteRepository.findAll();
        if (listaCliente.isEmpty()) {
            throw new ClienteNoEncontradoException(Constantes.CLIENTE_NO_ENCONTRADO);
        }
        return listaCliente.stream()
                .map(this::mapearClienteADTO)
                .collect(Collectors.toList());
    }


    public UsuarioDTO listarClienteById(Long idPersona) {
        Cliente cliente = clienteRepository.findById(idPersona)
                .orElseThrow(() -> new ClienteNoEncontradoException(Constantes.CLIENTE_NO_ENCONTRADO));
        return mapearClienteADTO(cliente);
    }


    public UsuarioDTO updateClienteById(Long idPersona, UsuarioDTO usuarioDTO) {
        Cliente cliente = clienteRepository.findById(idPersona)
                .orElseThrow(() -> new ClienteNoEncontradoException(Constantes.CLIENTE_NO_ENCONTRADO));
        actualizarDatosCliente(cliente, usuarioDTO);
        clienteRepository.save(cliente);
        return mapearClienteADTO(cliente);
    }


    @Transactional
    public void deleteClienteById(Long idPersona) {
        Cliente cliente = clienteRepository.findById(idPersona)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente con id " + idPersona + " no encontrado"));

        List<Cuenta> cuentas = cuentaRepository.findByCliente(cliente);
        for (Cuenta cuenta : cuentas) {
            movimientoRepository.deleteByCuenta(cuenta);
        }
        cuentaRepository.deleteAll(cuentas);
        clienteRepository.delete(cliente);
    }


    private UsuarioDTO mapearClienteADTO(Cliente cliente) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setPersonaId(cliente.getIdPersona());
        usuarioDTO.setNombres(cliente.getNombre());
        usuarioDTO.setDireccion(cliente.getDireccion());
        usuarioDTO.setTelefono(cliente.getTelefono());
        usuarioDTO.setContrasena(cliente.getContrasena());
        usuarioDTO.setEstado(cliente.getEstado());
        return usuarioDTO;
    }


    private Cliente mapearClienteDesdeDTO(UsuarioDTO usuarioDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(usuarioDTO.getNombres());
        cliente.setDireccion(usuarioDTO.getDireccion());
        cliente.setTelefono(usuarioDTO.getTelefono());
        cliente.setContrasena(usuarioDTO.getContrasena());
        cliente.setEstado(usuarioDTO.getEstado());
        return cliente;
    }


    private void actualizarDatosCliente(Cliente cliente, UsuarioDTO usuarioDTO) {
        cliente.setNombre(usuarioDTO.getNombres());
        cliente.setDireccion(usuarioDTO.getDireccion());
        cliente.setTelefono(usuarioDTO.getTelefono());
        cliente.setContrasena(usuarioDTO.getContrasena());
        cliente.setEstado(usuarioDTO.getEstado());
    }
}
