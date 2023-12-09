package sv.edu.udbvirtual.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.CcPrioridad;

public interface ccPrioridadRepository extends DataTablesRepository<CcPrioridad, Integer> {

	@Query("SELECT COUNT(0) FROM CcPrioridad x WHERE x.orden = :orden AND x.estado=true")
	Integer countByOrdenActivo(@Param("orden") Integer orden);

	@Query(value = "select t from CcPrioridad t where t.descripcion like '%' || :query || '%' order by t.descripcion")
	Slice<CcPrioridad> findByDescripcionLike(String query, Pageable page);
	
	@Query(value = "select t from CcPrioridad t where t.estado=true and t.descripcion like '%' || :query || '%' order by t.descripcion")
	Slice<CcPrioridad> findByDescripcionLikeActivos(String query, Pageable page);

}