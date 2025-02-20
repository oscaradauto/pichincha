package com.app.pichincha.exception;

public class SaldoActualException extends RuntimeException {
    public SaldoActualException(String message) {
        super(message);
    }

    public SaldoActualException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}