package sv.edu.udbvirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.edu.udbvirtual.domain.SecUsuario;
import sv.edu.udbvirtual.repository.SecUserRolRepository;

@Service
@Transactional(readOnly = true)
public class SecUserRolServiceImpl implements SecUserRolService {
	
	@Autowired
	private SecUserRolRepository secUserRolRepository;

	@Override
	public Integer countRolesByUsuario(SecUsuario secUsuario) {
		return secUserRolRepository.countRolesByUsuario(secUsuario.getId());
	}

}