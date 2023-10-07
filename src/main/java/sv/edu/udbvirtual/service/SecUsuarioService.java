package sv.edu.udbvirtual.service;

import java.util.Optional;

import org.springframework.http.HttpEntity;

import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.domain.SecUsuario;
import sv.edu.udbvirtual.dto.NuevoUsuarioDTO;

public interface SecUsuarioService {
	
	Optional<SecUsuario> getUsuarioByCorreo(String correo);

	public boolean validarConexion(String correo, String password);
	
	public ServiceResponse crearNuevoUsuario(NuevoUsuarioDTO nuevoUsuarioDTO);

	HttpEntity<byte[]> getFotoPerfil(Integer idUsuario);
	
}