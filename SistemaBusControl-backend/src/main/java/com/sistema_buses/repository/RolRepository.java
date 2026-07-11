package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_buses.enums.Roles;
import com.sistema_buses.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	Optional<Rol> findByNombre(Roles nombre);
}
