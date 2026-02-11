package ifc33b.dwesc.mireservafit.repository;

import ifc33b.dwesc.mireservafit.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
