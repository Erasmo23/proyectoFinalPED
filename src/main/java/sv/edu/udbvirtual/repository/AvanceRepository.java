package sv.edu.udbvirtual.repository;

import org.springframework.data.jpa.repository.Query;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.Avance;

public interface AvanceRepository extends DataTablesRepository<Avance, Integer> {

	@Query("SELECT COALESCE(SUM(a.pesoAvance),0) FROM Avance a WHERE a.tarea.id=:idTarea")
	Integer sumPorcentajesByTarea(Integer idTarea);
	
	@Query("SELECT COALESCE(SUM(a.pesoAvance),0) FROM Avance a WHERE a.tarea.id=:idTarea and a.id !=:idAvance")
	Integer sumPorcentajesByTareaExcludeAvance(Integer idTarea, Integer idAvance);

}