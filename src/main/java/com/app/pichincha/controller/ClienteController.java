package com.app.pichincha.controller;

import com.app.pichincha.DTO.CuentaDTO;
import com.app.pichincha.DTO.UsuarioDTO;
import com.app.pichincha.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/add")
    public ResponseEntity<String> crearCliente(@RequestBody UsuarioDTO clienteDTO) {
        clienteService.crearCliente(clienteDTO);
        return ResponseEntity.ok("Cliente creado exitosamente");
    }

    @GetMapping("/allClientes")
    public ResponseEntity<List<UsuarioDTO>> listarAllClientes() {
        List<UsuarioDTO> listaClientes = clienteService.listarAllClientes();
        return ResponseEntity.ok(listaClientes);
    }


    @GetMapping("/clienteById")
    public ResponseEntity<UsuarioDTO> listarClientePorId(@RequestParam Long idPersona) {
        UsuarioDTO usuarioDTO = clienteService.listarClienteById(idPersona);
        return ResponseEntity.ok(usuarioDTO);
    }



    @PutMapping("/update/{idPersona}")
    public ResponseEntity<UsuarioDTO> actualizarCliente(
            @PathVariable Long idPersona,
            @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = clienteService.updateClienteById(idPersona, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }


    @DeleteMapping("/delete/{idPersona}")
    public ResponseEntity<String> eliminarPersona(@PathVariable Long idPersona) {
        clienteService.deleteClienteById(idPersona);
        return ResponseEntity.ok("Cliente con id " + idPersona + " eliminado exitosamente");
    }



}
