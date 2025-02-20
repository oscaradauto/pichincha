package com.app.pichincha.controller;


import com.app.pichincha.DTO.CuentaDTO;
import com.app.pichincha.DTO.MovientoDTO;
import com.app.pichincha.exception.MovimientosNoEncontradosException;
import com.app.pichincha.service.CuentaService;
import com.app.pichincha.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/movimiento")
public class MovimientoController {


    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/add")
    public ResponseEntity<String> crearMovimiento(@RequestBody MovientoDTO movientoDTO) {
        movimientoService.crearMovimientos(movientoDTO);
        return ResponseEntity.ok("Movimiento creado exitosamente");
    }

    @GetMapping("/listMovimientoByPersona")
    public ResponseEntity<List<MovientoDTO>> listMovimientoByPersona(@RequestParam Long idPersona) {

        List<MovientoDTO> movimientos = movimientoService.listarMovimientosByIdPersona(idPersona);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/listMovimientoByFecha")
    public ResponseEntity<List<MovientoDTO>> listMovimientoByFecha(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFin) {

        List<MovientoDTO> movimientos = movimientoService.listarMovimientosPorRangoDeFechas(fechaInicio, fechaFin);

        if (movimientos.isEmpty()) {
            throw new MovimientosNoEncontradosException("No se encontraron movimientos para el rango de fechas proporcionado");
        }

        return ResponseEntity.ok(movimientos);
    }




}
