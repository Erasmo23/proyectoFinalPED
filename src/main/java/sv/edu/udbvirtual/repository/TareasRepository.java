package sv.edu.udbvirtual.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.CcEstado;
import sv.edu.udbvirtual.domain.Tarea;

public interface TareasRepository extends DataTablesRepository<Tarea, Integer> {

	@Query("SELECT COUNT(0) FROM Tarea a WHERE a.ccEstado.codigo = :codigo and a.secUsuario.id = :idUsuario")
	Integer countTareasByCodigoEstado(@Param("codigo") String codigo, @Param("idUsuario") Integer idUsuario);

	@Query(nativeQuery = true, value = "SELECT TOP 1 A.* FROM TAREAS A INNER JOIN CC_ESTADO B ON A.ID_ESTADO = B.ID  WHERE A.ID_USUARIO = :idUsuario AND B.CODIGO = :codigo ORDER BY A.FECHA_FIN ASC")
	Tarea getTareaByUsuarioAndEstadoReciente(@Param("codigo") String codigo, @Param("idUsuario") Integer idUsuario);

	@Modifying
	@Query("UPDATE Tarea t SET t.ccEstado=:estado, t.fechaTareaIniciada=:now, t.fechaActualizacion=:now WHERE t.id=:id")
	void updateEstadoIniciada(CcEstado estado, LocalDate now, Integer id);
	
	@Modifying
	@Query("UPDATE Tarea t SET t.ccEstado=:estado, t.fechaActualizacion=:now WHERE t.id=:id")
	void updateEstado(CcEstado estado, LocalDate now, Integer id);

}