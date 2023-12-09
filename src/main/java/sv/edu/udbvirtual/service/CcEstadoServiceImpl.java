package sv.edu.udbvirtual.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udbvirtual.commons.Constants;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.CcEstado;
import sv.edu.udbvirtual.repository.CcEstadoRepository;

@Service
@Transactional
public class CcEstadoServiceImpl implements CcEstadoService {
	
	@Autowired
	private CcEstadoRepository ccEstadoRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Optional<CcEstado> findById(Integer id) {
		return ccEstadoRepository.findById(id);
	}

	@Override
	public ServiceResponse saveValidated(CcEstado ccEstado) {
		
		ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
		
		ccEstadoRepository.save(ccEstado);
		serviceResponse.setMessage(Constants.MSG_GUARDADO_EXITOSO);
		serviceResponse.setSuccess(Boolean.TRUE);

		return serviceResponse;
	}

	@Transactional(readOnly = true)
	@Override
	public DataTablesOutput<CcEstado> findAll(DataTablesInput input) {
		return ccEstadoRepository.findAll(input);
	}

	@Override
	@Transactional(readOnly = true)
	public Slice<CcEstado> getListByDescripcion(String query, Pageable page) {
		return ccEstadoRepository.findByDescripcionLike(query, page);
	}
	

}