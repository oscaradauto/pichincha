package com.app.pichincha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cuenta")
public class Cuenta extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cuentaId;  // Clave primaria de la entidad

    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "saldo_inicial")
    private Double saldoInicial;

    @Column(name = "estado")
    private Boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)  // Apunta a la clave primaria heredada de Cliente (idPersona)
    private Cliente cliente;

    public Cuenta() {}


    public Cuenta(Integer cuentaId, String numeroCuenta, String tipo, Double saldoInicial, Boolean estado, Cliente cliente) {
        this.cuentaId = cuentaId;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.cliente = cliente;
    }

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
