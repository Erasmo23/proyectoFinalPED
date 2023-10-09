package sv.edu.udbvirtual.validator;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.edu.udbvirtual.dto.NuevoUsuarioDTO;

@Component
public class NuevoUsuarioValidator implements Validator {

	private static final List<String> EXTENSIONES_PERMITIDAS = Arrays.asList("JPEG","JPG","PNG");
	private static final String FIELD_MULTIPART = "foto";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NuevoUsuarioDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		NuevoUsuarioDTO nuevoUsuarioDTO = (NuevoUsuarioDTO) target;
		
		
		if (nuevoUsuarioDTO.getPassword() != null) {
		
			if (!nuevoUsuarioDTO.getPassword().equals(nuevoUsuarioDTO.getPasswordConfirm())) {
				errors.rejectValue("passwordConfirm", "secUsuario.password.error.noequals");
			}
			
		}
		
		if (nuevoUsuarioDTO.getFoto() != null && !nuevoUsuarioDTO.getFoto().isEmpty()) {
			String extension = FilenameUtils.getExtension(nuevoUsuarioDTO.getFoto().getOriginalFilename());
			
			if (!EXTENSIONES_PERMITIDAS.contains(extension.toUpperCase())){
				errors.rejectValue(FIELD_MULTIPART, "secUsuario.files.extension");
	        }
		}

	}

}
