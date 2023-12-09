package sv.edu.udbvirtual.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.criteria.Predicate;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.config.SecurityHelper;
import sv.edu.udbvirtual.domain.CcEstado;
import sv.edu.udbvirtual.domain.Tarea;
import sv.edu.udbvirtual.enums.CcEstadoEnum;
import sv.edu.udbvirtual.repository.AvanceRepository;
import sv.edu.udbvirtual.repository.CcEstadoRepository;
import sv.edu.udbvirtual.repository.SecUsuarioRepository;
import sv.edu.udbvirtual.repository.TareasRepository;

@Service
@Transactional
public class TareasServiceImpl implements TareasService {

	@Autowired
	private TareasRepository tareasRepository;

	@Autowired
	private CcEstadoRepository ccEstadoRepository;
	
	@Autowired
	private AvanceRepository avanceRepository;

	@Autowired
	private SecUsuarioRepository secUsuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public void cargarDatosInicialesUsuario(ModelMap mapa) {

		Integer idUsuario = SecurityHelper.getLoggedInUserDetails().getIdUsuario();

		mapa.put("countTareasCreadas",
				tareasRepository.countTareasByCodigoEstado(CcEstadoEnum.CREADA.getCodigo(), idUsuario));
		mapa.put("countTareasIniciadas",
				tareasRepository.countTareasByCodigoEstado(CcEstadoEnum.INICIADA.getCodigo(), idUsuario));
		mapa.put("countTareasEnProceso",
				tareasRepository.countTareasByCodigoEstado(CcEstadoEnum.PROCESO.getCodigo(), idUsuario));

		mapa.put("tareaProximaIniciar",
				tareasRepository.getTareaByUsuarioAndEstadoReciente(CcEstadoEnum.CREADA.getCodigo(), idUsuario));
		mapa.put("tareaProximaVencer",
				tareasRepository.getTareaByUsuarioAndEstadoReciente(CcEstadoEnum.PROCESO.getCodigo(), idUsuario));

	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Tarea> findById(final Integer id) {
		return tareasRepository.findById(id);
	}

	@Override
	public ServiceResponse saveValidated(final Tarea tarea) {

		CcEstado estado = ccEstadoRepository.findByCodigo(CcEstadoEnum.CREADA.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Estado de Tarea \"Creada\" con codigo CREADA no encontrado"));

		tarea.setCcEstado(estado);
		tarea.setSecUsuario(secUsuarioRepository.getReferenceById(SecurityHelper.getLoggedInUserDetails().getIdUsuario()));
		
		String messageResult = "";
		if (tarea.getId() == null) {
			tarea.setFechaCreacion(LocalDate.now());
			messageResult = "La Tarea fue a&ntilde;adida exitosamente.";
		}else {
			messageResult = "La Tarea fue modificada exitosamente.";
		}
		

		tareasRepository.save(tarea);

		return new ServiceResponse(Boolean.TRUE, messageResult);
	}

	@Override
	public DataTablesOutput<Tarea> findAllByUsuarioSession(DataTablesInput input) {
		return tareasRepository.findAll(input, null, (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.join("secUsuario").get("id"), SecurityHelper.getLoggedInUserDetails().getIdUsuario()));
			query.orderBy(criteriaBuilder.asc(root.get("fechaInicio")));
			return predicate;
		});
	}

	@Override
	public ServiceResponse iniciarTarea(final Integer id) {
		CcEstado estado = ccEstadoRepository.findByCodigo(CcEstadoEnum.INICIADA.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Estado de Tarea \"Iniciada\" con codigo INICIADA no encontrado"));
		
		tareasRepository.updateEstadoIniciada(estado, LocalDate.now(), id);
		return new ServiceResponse(Boolean.TRUE, "La Tarea fue Iniciada exitosamente.");
	}

	@Override
	public ServiceResponse finalizarTarea(final Integer id) {
		
		Integer totalPesoTarea = avanceRepository.sumPorcentajesByTarea(id);
		
		CcEstado ccEstado = null;
		
		
		if (totalPesoTarea < 100) {
			ccEstado = ccEstadoRepository.findByCodigo(CcEstadoEnum.FINALIZADA_IMCOMPLETA.getCodigo())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Estado de Tarea \"Finalizada Incompleta\" con codigo FINA_INCO no encontrado"));
		}else {
			ccEstado = ccEstadoRepository.findByCodigo(CcEstadoEnum.FINALIZADA.getCodigo())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Estado de Tarea \"Finalizada\" con codigo FINA no encontrado"));
		}
		
		tareasRepository.updateEstado(ccEstado, LocalDate.now(), id);
		
		return new ServiceResponse(Boolean.TRUE, "La Tarea fue Finalizada exitosamente.");
	}

}
