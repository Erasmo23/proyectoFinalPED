package sv.edu.udbvirtual.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.criteria.Predicate;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;
import sv.edu.udbvirtual.domain.Avance;
import sv.edu.udbvirtual.domain.CcEstado;
import sv.edu.udbvirtual.domain.Tarea;
import sv.edu.udbvirtual.enums.CcEstadoEnum;
import sv.edu.udbvirtual.repository.AvanceRepository;
import sv.edu.udbvirtual.repository.CcEstadoRepository;
import sv.edu.udbvirtual.repository.TareasRepository;

@Service
@Transactional
public class AvanceServiceImpl implements AvanceService {

	@Autowired
	private AvanceRepository avanceRepository;

	@Autowired
	private TareasRepository tareasRepository;

	@Autowired
	private CcEstadoRepository ccEstadoRepository;

	@Override
	@Transactional(readOnly = true)
	public DataTablesOutput<Avance> findAllByUsuarioSession(final DataTablesInput input, final Integer idTarea) {
		return avanceRepository.findAll(input, null, (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			predicate = criteriaBuilder.and(predicate,
					criteriaBuilder.equal(root.join("tarea").get("id"),idTarea));
			query.orderBy(criteriaBuilder.asc(root.get("id")));
			return predicate;
		});
	}

	@Override
	public void setearParametroPantalla(final Integer idTarea, final Integer idAvance, Model model) {

		if (idTarea != null && idTarea > 0) {
			Tarea tarea = tareasRepository.findById(idTarea)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Tarea con identificador ".concat(idTarea.toString()).concat(" No encontrado")));

			model.addAttribute("PorcentajeAvanceTarea", avanceRepository.sumPorcentajesByTarea(tarea.getId()));
			model.addAttribute("tareaRelacion", tarea);
		}

		Avance avance = new Avance();

		if (idAvance != null && idAvance > 0) {
			Optional<Avance> optAvance = avanceRepository.findById(idAvance);
			if (!optAvance.isPresent()) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Avance no encontrado");
			}
			avance = optAvance.get();

			model.addAttribute("PorcentajeAvanceTarea",
					avanceRepository.sumPorcentajesByTarea(avance.getTarea().getId()));
			model.addAttribute("tareaRelacion", avance.getTarea());

		}

		model.addAttribute("avance", avance);

	}

	@Override
	public ServiceResponse saveValidated(final Avance avance) {
		
		if (avance.getId() != null ) {
			
			Integer sumAvanceTarea = avanceRepository.sumPorcentajesByTareaExcludeAvance(avance.getTarea().getId(),avance.getId());

			if (sumAvanceTarea + avance.getPesoAvance() > 100) {
				throw new CustomRuntimeException("El global del avance de la tarea supera el 100% con la modificacion del mismo favor verificar");
			}
			
		}else {
			Integer sumAvanceTarea = avanceRepository.sumPorcentajesByTarea(avance.getTarea().getId());

			if (sumAvanceTarea + avance.getPesoAvance() > 100) {
				throw new CustomRuntimeException("El global del avance de la tarea supera el 100% favor verificar");
			}

			CcEstado estado = ccEstadoRepository.findByCodigo(CcEstadoEnum.PROCESO.getCodigo())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Estado de Tarea \"En Proceso\" con codigo EN_PROCESO no encontrado"));

			tareasRepository.updateEstado(estado, LocalDate.now(), avance.getTarea().getId());
		}

		avanceRepository.save(avance);
		return new ServiceResponse(Boolean.TRUE, "Se agrego el avance a la tarea existosamente!!!");
	}

}