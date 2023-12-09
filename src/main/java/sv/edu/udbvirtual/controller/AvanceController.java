package sv.edu.udbvirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.TransformerMessageValidation;
import sv.edu.udbvirtual.commons.ValidadorHttp;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesInput;
import sv.edu.udbvirtual.commons.datatables.mapping.DataTablesOutput;
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;
import sv.edu.udbvirtual.domain.Avance;
import sv.edu.udbvirtual.service.AvanceService;

@Controller
@RequestMapping("/avances")
public class AvanceController {
	
	@Autowired
	private AvanceService avanceService;
	
	@Autowired
	private TransformerMessageValidation transformerMessageValidation;
	
	
	@GetMapping("/list")
	public @ResponseBody DataTablesOutput<Avance> listAvancesByUsuario(@Valid DataTablesInput input,@RequestParam(defaultValue = "0") Integer idTarea) {
		return avanceService.findAllByUsuarioSession(input,idTarea);
	}
	
	@PostMapping("/agregar")
	public String agregarAvanceTarea(@RequestParam(required = false) Integer idTarea,@RequestParam(required = false) Integer idAvance,Model model) {
		if ((idTarea != null && idTarea > 0) || (idAvance != null && idAvance > 0)) {
			avanceService.setearParametroPantalla(idTarea,idAvance, model);
			return "pages/avances/index";
		}
		return "redirect:/index";
	}

	@GetMapping("/agregar")
	public String redirectIndexMapping() {
		return "redirect:/index";
	}
	
	
	@PostMapping("/save")
	public @ResponseBody ServiceResponse saveEntity(@Valid Avance avance, BindingResult bdResult) {
		
		String[] parametrosAExcluir = new String[] { "" };
		if (ValidadorHttp.isPeticionCorrectaExcluyendoCampos(bdResult, parametrosAExcluir)) {
			try {
				return avanceService.saveValidated(avance);
			} catch (CustomRuntimeException e) {
				return new ServiceResponse(Boolean.FALSE, e.getMessage());
			}
		}
		return transformerMessageValidation.getServiceResponseError(bdResult);
	}
	

}