package sv.edu.udbvirtual.domain;

import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sv.edu.udbvirtual.commons.Constants;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "SEC_USER_ROL")
public class SecUserRol {
	
	@Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SecUsuario secUsuario;
	
	@Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SecRol secRol;

	@Column(name = "FECHA_CREACION" )
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @CreatedDate
    private LocalDate fechaCreacion; 

}