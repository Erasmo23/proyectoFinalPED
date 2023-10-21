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
import sv.edu.udbvirtual.domain.CcEtiqueta;
import sv.edu.udbvirtual.service.CcEtiquetaService;

@Controller
@RequestMapping("/ccEtiqueta")
public class CcEtiquetaController {

	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String REDIRECT_CC_ETIQUETA = "redirect:/ccEtiqueta/";
	private static final String CC_ETIQUETA = "ccEtiqueta";

	@Autowired
	private CcEtiquetaService ccEtiquetaService;

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@GetMapping(value = { "/", "" })
	public String indexCcEstado() {
		return "pages/ccEtiqueta/list";
	}

	@GetMapping("/list")
	public @ResponseBody DataTablesOutput<CcEtiqueta> listCcEstado(@Valid DataTablesInput input) {
		return ccEtiquetaService.findAll(input);
	}

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@GetMapping("/form")
	public String formCcTipificador(@RequestParam(required = false) Integer id, Model model) {
		if (!model.containsAttribute(CC_ETIQUETA)) {
			CcEtiqueta ccEtiqueta = new CcEtiqueta();
			if (id != null) {
				Optional<CcEtiqueta> optCcEtiqueta = ccEtiquetaService.findById(id);
				if (!optCcEtiqueta.isPresent()) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CcEtiqueta Not Found");
				}
				ccEtiqueta = optCcEtiqueta.get();
			}
			model.addAttribute(CC_ETIQUETA, ccEtiqueta);
		}
		return "pages/ccEtiqueta/form";
	}

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@PostMapping("/save")
	public String saveEntity(@Valid CcEtiqueta ccEtiqueta, BindingResult bdResult, RedirectAttributes atts) {
		String redirectTo = Constants.REDIRECT_FORM;
		String[] parametrosAExcluir = new String[] { "" };
		if (ValidadorHttp.isPeticionCorrectaExcluyendoCampos(bdResult, parametrosAExcluir)) {
			ServiceResponse serviceResponse = ccEtiquetaService.saveValidated(ccEtiqueta);
			atts.addFlashAttribute(Constants.SERVICE_RESPONSE_NAME, serviceResponse);
			redirectTo = serviceResponse.isSuccess() ? REDIRECT_CC_ETIQUETA : redirectTo;
		}
		atts.addFlashAttribute(CC_ETIQUETA, ccEtiqueta);
		atts.addFlashAttribute(BindingResult.class.getCanonicalName() + ".ccEtiqueta", bdResult);
		return redirectTo;
	}

	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	@PostMapping(value = { "/cambioEstado" })
	public @ResponseBody ServiceResponse cambioEstado(@RequestParam(value = "id", required = false) Integer id) {
		return ccEtiquetaService.cambioEstado(id);
	}

}