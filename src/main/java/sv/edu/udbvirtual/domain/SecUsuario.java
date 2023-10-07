package sv.edu.udbvirtual.domain;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "SEC_USUARIO")
public class SecUsuario {
	
	@Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    
    @Column(name = "NOMBRES" )
    @NotBlank(message = "No puede estar vacio el campo nombres")
    @Size(max = 100, message = "El campo nombres excede la longitud permitida")
    private String nombres;
    
    @Column(name = "APELLIDOS" )
    @NotBlank(message = "No puede estar vacio el campo apellidos")
    @Size(max = 100, message = "El campo apellidos excede la longitud permitida")
    private String apellidos;
    
    @Column(name = "CORREO" )
    @NotBlank(message = "No puede estar vacio el campo correo")
    @Size(max = 100, message = "El campo correo excede la longitud permitida")
    private String correo;
    
    @Column(name = "PASSWORD")
    @Size(max = 100, message = "El campo stPassword excede la longitud permitida")
    private String password;
    
    @Column(name = "FOTO" )
    @Lob
    @JsonIgnore
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto; 
    
    @Column(name = "EXTENSION_FOTO" )
    private String extensionFoto;
    
    public String getNombreCompletoDelegate() {
    	return this.nombres.concat(" ").concat(this.apellidos);
    }
    
    @OneToMany(mappedBy = "secUsuario", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<SecUserRol> SecUserRoles;
    
    /*@Column(name = "FC_CREA_FECHA" )
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    //@CreatedDate
    private LocalDateTime fcCreaFecha; */

}
