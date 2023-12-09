package sv.edu.udbvirtual.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.CcEtiqueta;

public interface CcEtiquetaService {

	Optional<CcEtiqueta> findById(Integer id);
	
	ServiceResponse saveValidated(CcEtiqueta ccEtiqueta);
	
	DataTablesOutput<CcEtiqueta> findAll(DataTablesInput input);
	
	ServiceResponse cambioEstado(Integer id);
	
	Slice<CcEtiqueta> getListByDescripcion(String query, Pageable page);
	
	Slice<CcEtiqueta> getListActivosByDescripcion(String query, Pageable page);
	
}