package sv.edu.udbvirtual.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import sv.edu.udbvirtual.commons.Constants;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.ValidadorHttp;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.domain.CcPrioridad;
import sv.edu.udbvirtual.service.CcPrioridadService;

@Controller
@RequestMapping("/ccPrioridad")
public class CcPrioridadController {

	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String REDIRECT_CC_PRIORIDAD = "redirect:/ccPrioridad/";
	private static final String CC_PRIORIDAD = "ccPrioridad";

	@Autowired
	private CcPrioridadService ccPrioridadService;

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@GetMapping(value = { "/", "" })
	public String indexCcEstado() {
		return "pages/ccPrioridad/list";
	}

	@GetMapping("/list")
	public @ResponseBody DataTablesOutput<CcPrioridad> listCcEstado(@Valid DataTablesInput input) {
		return ccPrioridadService.findAll(input);
	}

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@GetMapping("/form")
	public String formCcTipificador(@RequestParam(required = false) Integer id, Model model) {
		if (!model.containsAttribute(CC_PRIORIDAD)) {
			CcPrioridad ccPrioridad = new CcPrioridad();
			if (id != null) {
				Optional<CcPrioridad> optCcEstado = ccPrioridadService.findById(id);
				if (!optCcEstado.isPresent()) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CcPrioridad Not Found");
				}
				ccPrioridad = optCcEstado.get();
			}
			model.addAttribute(CC_PRIORIDAD, ccPrioridad);
		}
		return "pages/ccPrioridad/form";
	}

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@PostMapping("/save")
	public String saveEntity(@Valid CcPrioridad ccPrioridad, BindingResult bdResult, RedirectAttributes atts) {
		String redirectTo = Constants.REDIRECT_FORM;
		String[] parametrosAExcluir = new String[] { "" };
		if (ValidadorHttp.isPeticionCorrectaExcluyendoCampos(bdResult, parametrosAExcluir)) {
			ServiceResponse serviceResponse = ccPrioridadService.saveValidated(ccPrioridad);
			atts.addFlashAttribute(Constants.SERVICE_RESPONSE_NAME, serviceResponse);
			redirectTo = serviceResponse.isSuccess() ? REDIRECT_CC_PRIORIDAD : redirectTo;
		}
		atts.addFlashAttribute(CC_PRIORIDAD, ccPrioridad);
		atts.addFlashAttribute(BindingResult.class.getCanonicalName() + ".ccPrioridad", bdResult);
		return redirectTo;
	}

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@PostMapping(value = { "/cambioEstado" })
	public @ResponseBody ServiceResponse cambioEstado(@RequestParam(value = "id", required = false) Integer id) {
		return ccPrioridadService.cambioEstado(id);
	}

}