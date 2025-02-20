package com.app.pichincha.repository;

import com.app.pichincha.model.Cuenta;
import com.app.pichincha.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {


    Movimiento findTopByCuentaOrderByMovimientosIdDesc(Cuenta cuenta);

    List<Movimiento> findByCuenta_CuentaId(Integer cuentaId);

    @Query("SELECT m FROM Movimiento m WHERE m.createdAt BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByFecha(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    void deleteByCuenta(Cuenta cuenta);

}
