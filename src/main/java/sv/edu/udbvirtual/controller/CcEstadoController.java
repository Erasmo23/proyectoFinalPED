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
import sv.edu.udbvirtual.domain.CcEstado;
import sv.edu.udbvirtual.service.CcEstadoService;

@Controller
@RequestMapping("/ccEstado")
public class CcEstadoController {

	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String REDIRECT_CC_ESTADO = "redirect:/ccEstado/";
	private static final String CC_ESTADO = "ccEstado";
	
	@Autowired
	private CcEstadoService ccEstadoService;
	
	//@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@GetMapping(value = { "/", "" })
	public String indexCcEstado() {
		return "pages/ccEstado/list";
	}
	
	@GetMapping("/list")
	public @ResponseBody DataTablesOutput<CcEstado> listCcEstado(@Valid DataTablesInput input) {
		return ccEstadoService.findAll(input);
	}
	
	//@PreAuthorize(HAS_AUTHORITY_CREATE_EDIT)
	@GetMapping("/form")
	public String formCcTipificador(@RequestParam(required = false) Integer id, Model model) {
		if (!model.containsAttribute(CC_ESTADO)) {
			CcEstado ccEstado = new CcEstado();
			if (id != null) {
				Optional<CcEstado> optCcEstado = ccEstadoService.findById(id);
				if (!optCcEstado.isPresent()) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CcEstado Not Found");
				}
				ccEstado = optCcEstado.get();
			}
			model.addAttribute(CC_ESTADO, ccEstado);
		}
		return "pages/ccEstado/form";
	}
	
	@PostMapping("/save")
	public String saveEntity(@Valid CcEstado ccEstado, BindingResult bdResult, RedirectAttributes atts) {
		String redirectTo = Constants.REDIRECT_FORM;
		String[] parametrosAExcluir = new String[] { "" };
		if (ValidadorHttp.isPeticionCorrectaExcluyendoCampos(bdResult, parametrosAExcluir)) {
			ServiceResponse serviceResponse = ccEstadoService.saveValidated(ccEstado);
			atts.addFlashAttribute(Constants.SERVICE_RESPONSE_NAME, serviceResponse);
			redirectTo = serviceResponse.isSuccess() ? REDIRECT_CC_ESTADO : redirectTo;
		}
		atts.addFlashAttribute(CC_ESTADO, ccEstado);
		atts.addFlashAttribute(BindingResult.class.getCanonicalName() + ".ccEstado", bdResult);
		return redirectTo;
	}
	
}