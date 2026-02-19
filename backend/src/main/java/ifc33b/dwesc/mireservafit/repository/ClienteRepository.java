package ifc33b.dwesc.mireservafit.repository;

import ifc33b.dwesc.mireservafit.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findById(Integer id);
}