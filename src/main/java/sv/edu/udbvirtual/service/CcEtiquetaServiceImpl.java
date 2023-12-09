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
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;
import sv.edu.udbvirtual.domain.CcEtiqueta;
import sv.edu.udbvirtual.repository.CcEtiquetaRepository;

@Service
@Transactional
public class CcEtiquetaServiceImpl implements CcEtiquetaService {
	
	@Autowired
	private CcEtiquetaRepository ccEtiquetaRepository;

	@Transactional(readOnly = true)
	@Override
	public Optional<CcEtiqueta> findById(Integer id) {
		return ccEtiquetaRepository.findById(id);
	}

	@Override
	public ServiceResponse saveValidated(CcEtiqueta ccEtiqueta) {
		ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
		ccEtiqueta.setEstado(Boolean.TRUE);
		ccEtiquetaRepository.save(ccEtiqueta);
		serviceResponse.setMessage(Constants.MSG_GUARDADO_EXITOSO);
		serviceResponse.setSuccess(Boolean.TRUE);
		return serviceResponse;
	}

	@Transactional(readOnly = true)
	@Override
	public DataTablesOutput<CcEtiqueta> findAll(DataTablesInput input) {
		return ccEtiquetaRepository.findAll(input);
	}

	@Override
	public ServiceResponse cambioEstado(Integer id) {
		ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
		CcEtiqueta ccEtiqueta = findById(id)
				.orElseThrow(() -> new CustomRuntimeException("No se encontro registro con ID: " + id ));
		ccEtiqueta.setEstado(!ccEtiqueta.getEstado());
		ccEtiquetaRepository.save(ccEtiqueta);
		serviceResponse
				.setMessage(Boolean.TRUE.equals(ccEtiqueta.getEstado()) ? Constants.MSG_ACTIVADO_EXITOSO
						: Constants.MSG_INACTIVADO_EXITOSO);
		serviceResponse.setSuccess(Boolean.TRUE);
		return serviceResponse;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Slice<CcEtiqueta> getListByDescripcion(String query, Pageable page) {
		return ccEtiquetaRepository.findByDescripcionLike(query, page);
	}

	@Transactional(readOnly = true)
	@Override
	public Slice<CcEtiqueta> getListActivosByDescripcion(String query, Pageable page) {
		return ccEtiquetaRepository.findByDescripcionLikeActivos(query, page);
	}

}