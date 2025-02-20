package com.app.pichincha.controller;


import com.app.pichincha.DTO.CuentaDTO;
import com.app.pichincha.DTO.UsuarioDTO;
import com.app.pichincha.service.ClienteService;
import com.app.pichincha.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/add")
    public ResponseEntity<String> crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        cuentaService.crearCuenta(cuentaDTO);
        return ResponseEntity.ok("Cuenta creado exitosamente");
    }

    @GetMapping("/allCuentas")
    public ResponseEntity<List<CuentaDTO>> listarAllCuenta() {
        List<CuentaDTO> listaCuentas = cuentaService.listarAllCuenta();
        return ResponseEntity.ok(listaCuentas);
    }


    @GetMapping("/cuentaByPersonId")
    public ResponseEntity<List<CuentaDTO>> listarAllCuenta(@RequestParam Long idPersona) {
        List<CuentaDTO> listaCuentasByPersonId = cuentaService.listarCuentaByUsuario(idPersona);
        return ResponseEntity.ok(listaCuentasByPersonId);
    }

    @GetMapping("/cuentaByNumeroCuenta/{numeroCuenta}")
    public ResponseEntity<CuentaDTO> listarCuentaPorNumeroCuenta(@PathVariable String numeroCuenta) {
        CuentaDTO cuentaDTO = cuentaService.listarCuentaPorNumeroCuenta(numeroCuenta);
        return ResponseEntity.ok(cuentaDTO);
    }

    @PatchMapping("/update/{idCuenta}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(
            @PathVariable Long idCuenta,
            @RequestBody CuentaDTO cuentaDTO) {

            CuentaDTO cuentaActualizada = cuentaService.updateCuentaById(idCuenta, cuentaDTO);
            return ResponseEntity.ok(cuentaActualizada);

    }

}
