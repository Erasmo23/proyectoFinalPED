package sv.edu.udbvirtual.service;

import sv.edu.udbvirtual.domain.SecUsuario;

public interface SecUserRolService {

	Integer countRolesByUsuario(SecUsuario secUsuario);
	
}