package sv.edu.udbvirtual.domain;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "CC_ETIQUETAS")
public class CcEtiqueta implements Serializable {

	@Getter
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "ID")
	@EqualsAndHashCode.Include
	@JsonView
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "DESCRIPCION")
	@NotBlank(message = "No puede estar vacio el campo descripcion")
	@Size(max = 100, message = "El campo descripcion excede la longitud permitida")
	private String descripcion;

	@Column(name = "CODIGO")
	@NotBlank(message = "No puede estar vacio el campo codigo")
	@Size(max = 10, message = "El campo codigo excede la longitud permitida")
	private String codigo;

	@Column(name = "ESTADO")
//  @NotNull(message = "No puede estar vacio el campo bnEstado")
	private Boolean estado;
	
	public String getEstadoDelegate() {
        if(Boolean.TRUE.equals(estado)) return "Activo";
        else return "Inactivo";
    }

}