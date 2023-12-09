package sv.edu.udbvirtual.service;

import java.util.Optional;

import org.springframework.ui.ModelMap;

import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.Tarea;

public interface TareasService {

	void cargarDatosInicialesUsuario(ModelMap mapa);
	
	Optional<Tarea> findById(final Integer id);

	ServiceResponse saveValidated(final Tarea tarea);

	DataTablesOutput<Tarea> findAllByUsuarioSession(final DataTablesInput input);

	ServiceResponse iniciarTarea(final Integer id);

	ServiceResponse finalizarTarea(final Integer id);
	
}