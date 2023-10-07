package sv.edu.udbvirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.SecUserRol;

public interface SecUserRolRepository  extends DataTablesRepository<SecUserRol, Integer> {

	@Query("SELECT COUNT(0) FROM SecUserRol x WHERE x.secUsuario.id=:idUsuario")
	public Integer countRolesByUsuario(@Param("idUsuario") Integer idUsuario);
	
}