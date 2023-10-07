package sv.edu.udbvirtual.repository;

import java.util.Optional;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.SecRol;

public interface SecRolRepository  extends DataTablesRepository<SecRol, Integer> {

	Optional<SecRol> findByCodigo(String codigo);
	
}