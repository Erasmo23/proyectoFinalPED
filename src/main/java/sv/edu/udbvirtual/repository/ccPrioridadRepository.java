package sv.edu.udbvirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.CcPrioridad;

public interface ccPrioridadRepository extends DataTablesRepository<CcPrioridad, Integer> {

	@Query("SELECT COUNT(0) FROM CcPrioridad x WHERE x.orden = :orden AND x.estado=true")
	Integer countByOrdenActivo(@Param("orden") Integer orden);

}