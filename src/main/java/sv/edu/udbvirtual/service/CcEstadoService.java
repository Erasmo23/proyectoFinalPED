package sv.edu.udbvirtual.service;

import java.util.Optional;

import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.CcEstado;

public interface CcEstadoService {

	Optional<CcEstado> findById(Integer id);
	
	ServiceResponse saveValidated(CcEstado ccEstado);
	
	DataTablesOutput<CcEstado> findAll(DataTablesInput input);
	
}