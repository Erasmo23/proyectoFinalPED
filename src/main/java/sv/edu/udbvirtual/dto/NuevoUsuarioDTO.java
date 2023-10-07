package sv.edu.udbvirtual.dto;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevoUsuarioDTO implements Serializable {

	@Getter
	private static final long serialVersionUID = -5731698055505770659L;
	
	@NotNull(message = "No puede quedar vacio el campo de nombres")
	private String nombres;
	
	@NotNull(message = "No puede quedar vacio el campo de apellidos")
	private String apellidos;
	
	@NotNull(message = "No puede quedar vacio el campo de correo")
	private String correo;
	
	@NotNull(message = "No puede quedar vacio el campo de municipio")
	private String password;
	
	private String passwordConfirm;
	
    private MultipartFile foto;

}