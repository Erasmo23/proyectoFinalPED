package sv.edu.udbvirtual.service;

import java.util.Optional;

import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.CcPrioridad;

public interface CcPrioridadService {

	Optional<CcPrioridad> findById(Integer id);
	
	ServiceResponse saveValidated(CcPrioridad ccPrioridad);
	
	DataTablesOutput<CcPrioridad> findAll(DataTablesInput input);
	
	ServiceResponse cambioEstado(Integer id);
}