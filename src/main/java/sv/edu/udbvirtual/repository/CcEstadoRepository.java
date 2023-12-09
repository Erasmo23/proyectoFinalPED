package sv.edu.udbvirtual.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.CcEstado;

public interface CcEstadoRepository extends DataTablesRepository<CcEstado, Integer> {

	Integer countByCodigo(String codigo);
	
	Optional<CcEstado> findByCodigo(String codigo);
	
	@Query(value = "select t from CcEstado t where t.descripcion like '%' || :query || '%' order by t.descripcion")
	Slice<CcEstado> findByDescripcionLike(String query, Pageable page);
	
}