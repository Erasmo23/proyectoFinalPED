package sv.edu.udbvirtual.service;

import org.springframework.ui.Model;

import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.Avance;

public interface AvanceService {

	void setearParametroPantalla(final Integer idTarea,final Integer idAvance, Model model);

	ServiceResponse saveValidated(final Avance avance);

	DataTablesOutput<Avance> findAllByUsuarioSession(final DataTablesInput input, final Integer idTarea);

}
