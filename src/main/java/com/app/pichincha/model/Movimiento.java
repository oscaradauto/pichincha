package com.app.pichincha.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
public class Movimiento extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movimientosId;

    @Column(name = "tipo", length = 20)
    private String tipo;

    @Column(name = "valor_operacion")
    private Double valorOperacion;

    @Column(name = "operacion")
    private String operacion;

    @Column(name = "saldo")
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;


    public Movimiento() {}

    public Movimiento(Integer movimientosId, String tipo, Double valorOperacion, String operacion, Double saldo, Cuenta cuenta) {
        this.movimientosId = movimientosId;
        this.tipo = tipo;
        this.valorOperacion = valorOperacion;
        this.operacion = operacion;
        this.saldo = saldo;
        this.cuenta = cuenta;
    }


    public Integer getMovimientosId() {
        return movimientosId;
    }

    public void setMovimientosId(Integer movimientosId) {
        this.movimientosId = movimientosId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValorOperacion() {
        return valorOperacion;
    }

    public void setValorOperacion(Double valorOperacion) {
        this.valorOperacion = valorOperacion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
