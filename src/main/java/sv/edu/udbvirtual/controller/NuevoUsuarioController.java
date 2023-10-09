package sv.edu.udbvirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.validation.Valid;
import sv.edu.udbvirtual.commons.TransformerMessageValidation;
import sv.edu.udbvirtual.dto.NuevoUsuarioDTO;
import sv.edu.udbvirtual.service.SecUsuarioService;
import sv.edu.udbvirtual.validator.NuevoUsuarioValidator;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.commons.ValidadorHttp;
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;

@Controller
@RequestMapping("/nuevousuario")
public class NuevoUsuarioController {

	@Autowired
	private SecUsuarioService secUsuarioService;
	
	@Autowired
	private NuevoUsuarioValidator nuevoUsuarioValidator;
	
	@Autowired
	private TransformerMessageValidation transformerMessageValidation;
	
	@GetMapping(value = {"/", "/form"})
    public String index(Model model) {
		model.addAttribute("nuevoUsuarioDTO", new NuevoUsuarioDTO());
		return "pages/nuevoUsuario/form";
    }
	
	
	@PostMapping("/save")
	public @ResponseBody ServiceResponse save(@Valid NuevoUsuarioDTO nuevoUsuarioDTO, BindingResult bdResult) {
    	
		nuevoUsuarioValidator.validate(nuevoUsuarioDTO, bdResult);
		
    	String[] parametrosAExcluir = new String[]{""};
        if (ValidadorHttp.isPeticionCorrectaExcluyendoCampos(bdResult, parametrosAExcluir)) {
        	
        	try {
        		return secUsuarioService.crearNuevoUsuario(nuevoUsuarioDTO);
    		} catch (CustomRuntimeException e) {
				return new ServiceResponse(Boolean.FALSE, e.getMessage());
    		}
        }
    	
        return transformerMessageValidation.getServiceResponseError(bdResult);
    }
	
	
	
	//save
	
}