package sv.edu.udbvirtual.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sv.edu.udbvirtual.domain.SecUsuario;
import sv.edu.udbvirtual.service.SecUserRolService;
import sv.edu.udbvirtual.service.SecUsuarioService;

@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private SecUsuarioService secUsuarioService;

	@Autowired
	private SecUserRolService secUserRolService;

	private SecurityUserDetails dbAuthentication(String userName) throws BadCredentialsException {
		try {
			// obtengo al usuario
			SecUsuario usuario = secUsuarioService.getUsuarioByCorreo(userName)
					.orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

			SecurityUserDetails securityUser = new SecurityUserDetails();
			securityUser.setIdUsuario(usuario.getId());
			securityUser.setCorreoUsuario(userName);
			securityUser.setNombreCompleto(usuario.getNombreCompletoDelegate());

			Integer countRolesUsuario = secUserRolService.countRolesByUsuario(usuario);

			if (countRolesUsuario <= 0) {
				throw new BadCredentialsException("Usuario no tiene configurado Roles");
			}

			usuario.getSecUserRoles().forEach(secUserRole -> {
				securityUser.addGrantedAuthority(secUserRole.getSecRol().getCodigo());
				securityUser.addRole(secUserRole.getSecRol().getDescripcion());
			});

			return securityUser;
		} catch (Exception e) {

			throw new BadCredentialsException("Error al obtener información del usuario " + e.getMessage());
		}
	}

	@Override
	public Authentication authenticate(Authentication a) throws AuthenticationException {
		String username = a.getName();
		String password = (String) a.getCredentials();
		if (secUsuarioService.validarConexion(username, password)) {
			SecurityUserDetails user = dbAuthentication(username);
			return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
		} else {
			throw new BadCredentialsException("Direcci&oacute;n de correo electr&oacute;nico o contrase&ntilde;a incorrectos.");
		}
	}

	@Override
	public boolean supports(Class<?> type) {
		return true;
	}
}