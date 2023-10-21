package sv.edu.udbvirtual.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udbvirtual.commons.Constants;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;
import sv.edu.udbvirtual.domain.CcPrioridad;
import sv.edu.udbvirtual.repository.ccPrioridadRepository;

@Service
@Transactional
public class CcPrioridadServiceImpl implements CcPrioridadService {

	@Autowired
	private ccPrioridadRepository ccPrioridadRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Optional<CcPrioridad> findById(Integer id) {
		return ccPrioridadRepository.findById(id);
	}

	@Override
	public ServiceResponse saveValidated(CcPrioridad ccPrioridad) {
		ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
		
		Integer countOrdenActivo = ccPrioridadRepository.countByOrdenActivo(ccPrioridad.getOrden());
		
		if (countOrdenActivo > 0) {
			serviceResponse.setMessage("El Orden de prioridad ya esta ocupado por otro registro");
			return serviceResponse;
		}
		
		ccPrioridad.setEstado(Boolean.TRUE);
		ccPrioridadRepository.save(ccPrioridad);
		serviceResponse.setMessage(Constants.MSG_GUARDADO_EXITOSO);
		serviceResponse.setSuccess(Boolean.TRUE);
		return serviceResponse;
	}

	@Transactional(readOnly = true)
	@Override
	public DataTablesOutput<CcPrioridad> findAll(DataTablesInput input) {
		return ccPrioridadRepository.findAll(input);
	}

	@Override
	public ServiceResponse cambioEstado(Integer id) {
		
		ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
		CcPrioridad ccPrioridad = findById(id)
				.orElseThrow(() -> new CustomRuntimeException("No se encontro registro con ID: " + id ));
		ccPrioridad.setEstado(!ccPrioridad.getEstado());
		ccPrioridadRepository.save(ccPrioridad);
		serviceResponse
				.setMessage(Boolean.TRUE.equals(ccPrioridad.getEstado()) ? Constants.MSG_ACTIVADO_EXITOSO
						: Constants.MSG_INACTIVADO_EXITOSO);
		serviceResponse.setSuccess(Boolean.TRUE);
		return serviceResponse;
	}

}