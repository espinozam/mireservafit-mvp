package ifc33b.dwesc.mireservafit.repository;

import ifc33b.dwesc.mireservafit.model.Reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

}