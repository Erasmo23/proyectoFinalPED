package sv.edu.udbvirtual.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.TransformerMessageValidation;
import sv.edu.udbvirtual.commons.ValidadorHttp;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;
import sv.edu.udbvirtual.domain.Tarea;
import sv.edu.udbvirtual.service.TareasService;

@Controller
@RequestMapping("tareas")
public class TareasController {
	
	private static final String TAREA = "tarea";
	private static final String TAREA_NOT_FOUND = "Tarea No encontrada";
	
	@Autowired
	private TareasService tareasService;
	
	@Autowired
	private TransformerMessageValidation transformerMessageValidation;

	@GetMapping(value = { "/", "" })
	public String indexGsAlerta(Model model) {
		return "pages/tareas/seguimientoTareas";
	}
	
	@GetMapping("/list")
	public @ResponseBody DataTablesOutput<Tarea> listCcEstado(@Valid DataTablesInput input) {
		return tareasService.findAllByUsuarioSession(input);
	}
	
	@GetMapping("/nuevaTarea")
	public String formGsAlerta(@RequestParam(required = false) Integer id, Model model) {
		if (!model.containsAttribute(TAREA)) {
			Tarea tarea = new Tarea();
			if (id != null) {
				Optional<Tarea> optTarea = tareasService.findById(id);
				if (!optTarea.isPresent()) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, TAREA_NOT_FOUND);
				}
				tarea = optTarea.get();
			}
			model.addAttribute(TAREA, tarea);
		}
		return "pages/tareas/nuevaTarea";
	}

	@GetMapping("/editarTarea")
	public String redirectIndexMapping() {
		return "redirect:/index";
	}
	
	@PostMapping("/editarTarea")
	public String editarTarea(@RequestParam(required = false) Integer id, Model model) {
		if (id != null && id > 0) {
			Tarea tarea = tareasService.findById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.FORBIDDEN, TAREA_NOT_FOUND));
			model.addAttribute(TAREA, tarea);
			return "pages/tareas/edit";
		}
		return "redirect:/index";
	}
	
	@PostMapping("/save")
	public @ResponseBody ServiceResponse saveEntity(@Valid Tarea tarea, BindingResult bdResult) {
		
		String[] parametrosAExcluir = new String[] { "" };
		if (ValidadorHttp.isPeticionCorrectaExcluyendoCampos(bdResult, parametrosAExcluir)) {
			try {
				return tareasService.saveValidated(tarea);
			} catch (CustomRuntimeException e) {
				return new ServiceResponse(Boolean.FALSE, e.getMessage());
			}
		}
		return transformerMessageValidation.getServiceResponseError(bdResult);
	}
	
	@PostMapping("/iniciarTarea")
	public @ResponseBody ServiceResponse iniciarTarea(@RequestParam(value = "id", required = false) final Integer id) {
		return tareasService.iniciarTarea(id);
	}
	
	@PostMapping("/finalizarTarea")
	public @ResponseBody ServiceResponse finalizarTarea(@RequestParam(value = "id", required = false) final Integer id) {
		return tareasService.finalizarTarea(id);
	}
	
	@GetMapping("/guia")
	public String guiaTareas(Model model) {
		tareasService.cargarListasTareas(model);
		return "pages/tareas/guia";
	}
}