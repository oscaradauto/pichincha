package com.app.pichincha.repository;

import com.app.pichincha.model.Cliente;
import com.app.pichincha.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
