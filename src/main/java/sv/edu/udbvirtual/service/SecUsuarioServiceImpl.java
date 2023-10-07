package sv.edu.udbvirtual.service;

import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import sv.edu.udbvirtual.commons.Constants;
import sv.edu.udbvirtual.commons.ServiceResponse;
import sv.edu.udbvirtual.domain.SecUserRol;
import sv.edu.udbvirtual.domain.SecUsuario;
import sv.edu.udbvirtual.dto.NuevoUsuarioDTO;
import sv.edu.udbvirtual.repository.SecRolRepository;
import sv.edu.udbvirtual.repository.SecUserRolRepository;
import sv.edu.udbvirtual.repository.SecUsuarioRepository;
import sv.edu.udbvirtual.commons.Utils;
import sv.edu.udbvirtual.commons.exception.CustomRuntimeException;

@Slf4j
@Service
@Transactional
public class SecUsuarioServiceImpl implements SecUsuarioService {

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecUsuarioRepository secUsuarioRepository;
	
	@Autowired
	private SecRolRepository secRolRepository;
	
	@Autowired
	private SecUserRolRepository secUserRolRepository;
	
	
	@Override
	public boolean validarConexion(String correo, String password) {
		
		SecUsuario secUsuario = secUsuarioRepository.findByCorreo(correo).orElseThrow(
				() ->  new BadCredentialsException("Correo no registrado...")
				);
		
		return passwordEncoder.matches(password, secUsuario.getPassword());
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<SecUsuario> getUsuarioByCorreo(String correo) {
		return secUsuarioRepository.findByCorreo(correo);
	}

	@Override
	public ServiceResponse crearNuevoUsuario(NuevoUsuarioDTO nuevoUsuarioDTO) {
		
		secUsuarioRepository.findByCorreo(nuevoUsuarioDTO.getCorreo()).ifPresent(usuarioPersistido->{
			throw new CustomRuntimeException("Correo ya fue registrado por otra cuenta, verificar datos");
		});
		
		try {
			
			SecUsuario secUsuario = new SecUsuario();
			secUsuario.setNombres(nuevoUsuarioDTO.getNombres());
			secUsuario.setApellidos(nuevoUsuarioDTO.getApellidos());
			secUsuario.setCorreo(nuevoUsuarioDTO.getCorreo());
			secUsuario.setPassword(passwordEncoder.encode(nuevoUsuarioDTO.getPassword()));
			
			if (nuevoUsuarioDTO.getFoto() != null) {
				secUsuario.setExtensionFoto(FilenameUtils.getExtension(nuevoUsuarioDTO.getFoto().getOriginalFilename()));
				secUsuario.setFoto(nuevoUsuarioDTO.getFoto().getBytes());
			}
			
			secUsuarioRepository.save(secUsuario);
			
			secRolRepository.findByCodigo("USER_TASK").ifPresent(secRol -> {
				
				SecUserRol secUserRol = new SecUserRol();
				secUserRol.setSecRol(secRol);
				secUserRol.setSecUsuario(secUsuario);
				
				secUserRolRepository.save(secUserRol);
				
			});
			
			return new ServiceResponse(Boolean.TRUE, Constants.MSG_GUARDADO_EXITOSO);
			
		} catch (Exception e) {
			
			log.error("ERROR::Procesamiento de archivos con compresion",e);
			
			throw new CustomRuntimeException("Error en el proceso de guardado y compresion de archivos",e);
		}
		
	}

	@Override
	public HttpEntity<byte[]> getFotoPerfil(Integer idUsuario) {
		
		Optional<SecUsuario> optionalSecUsuario = secUsuarioRepository.findById(idUsuario);
		
		if (optionalSecUsuario.isPresent()) {
			
			SecUsuario usuario = optionalSecUsuario.get();
			
			ContentDisposition disposicionContenido = ContentDisposition.builder("inline")
	                .filename("FotoPerfil")
	                .build();
			
			HttpHeaders header = new HttpHeaders();
	        if (usuario.getFoto() != null) {
	            header.setContentDisposition(disposicionContenido);
	            header.setContentLength(usuario.getFoto().length);
	            header.setContentType(Utils.getMediaTypeByExtensionArchivo(usuario.getExtensionFoto()));
	        }
	        if (usuario.getFoto() == null || usuario.getFoto().length == 0) {
	        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new HttpEntity<>(usuario.getFoto(), header);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}