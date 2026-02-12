package ifc33b.dwesc.mireservafit.repository;

import ifc33b.dwesc.mireservafit.model.Entrenador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {

}