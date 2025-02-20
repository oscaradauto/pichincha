package com.app.pichincha.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return buildErrorResponse("Error de ejecución", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNoEncontradoException(ClienteNoEncontradoException ex) {
        return buildErrorResponse("Cliente no encontrado", ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse("Argumento inválido", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CuentaDuplicadaException.class)
    public ResponseEntity<Map<String, Object>> handleCuentaDuplicadaException(CuentaDuplicadaException ex) {
        return buildErrorResponse("Cuenta duplicada", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDataAccessException(DataAccessException ex) {
        return buildErrorResponse("Error de acceso a datos", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildErrorResponse("Error interno del servidor", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String mensaje, String detalles, HttpStatus status) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("mensaje", mensaje);
        errorBody.put("detalles", detalles);
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", status.value());
        return new ResponseEntity<>(errorBody, status);
    }
}
