package sv.edu.udbvirtual.repository;

import java.util.Optional;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.SecUsuario;

public interface SecUsuarioRepository extends DataTablesRepository<SecUsuario, Integer> {
	
	Optional<SecUsuario> findByCorreo(String correo);
	
}