package com.app.pichincha.repository;

import com.app.pichincha.model.Cliente;
import com.app.pichincha.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    boolean existsByNumeroCuentaAndCliente(String numeroCuenta, Cliente cliente);

    @Query("SELECT c.saldoInicial FROM Cuenta c WHERE c.cuentaId = :cuentaId")
    double findSaldoInicialByCuentaId(@Param("cuentaId") Integer cuentaId);

    List<Cuenta> findByCliente(Cliente cliente);

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

}

