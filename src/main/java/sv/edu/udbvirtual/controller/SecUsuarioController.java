package sv.edu.udbvirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sv.edu.udbvirtual.service.SecUsuarioService;

@RequestMapping("/secUsuario")
@Controller
public class SecUsuarioController {

	
	@Autowired
	private SecUsuarioService secUsuarioService;
	
	@GetMapping("/getFotoPerfil/{idUsuario}")
    public @ResponseBody HttpEntity<byte []> getFotoPerfil(@PathVariable("idUsuario") Integer idUsuario) {
    	return secUsuarioService.getFotoPerfil(idUsuario);
    }
	
}