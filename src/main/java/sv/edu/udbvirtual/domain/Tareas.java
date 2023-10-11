package sv.edu.udbvirtual.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "SEC_USUARIO")
public class Tareas {

	@Getter
    private static final long serialVersionUID = 1L;

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
    
    @Column(name = "ID_PRIORIDAD")
    private Integer idPrioridad;
    
    @Column(name = "ID_ETIQUETA")
    private Integer idEtiqueta;
    
    @Getter(onMethod = @__( @JsonIgnore))
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SecUsuario ccEstado;
    
    @Column(name = "TITULO" )
    @NotBlank(message = "No puede estar vacio el campo titulo")
    @Size(max = 100, message = "El campo titulo excede la longitud permitida")
    private String titulo; 
	
	@Column(name = "DESCRIPCION" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 200, message = "El campo descripcion excede la longitud permitida")
    private String descripcion;
	
	@Column(name = "FECHA_INICIO" )
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime fechaInicio; 
	
	@Column(name = "FECHA_FIN" )
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime fechaFin; 
	
	@Column(name = "FECHA_CREACION" )
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
	@CreatedDate
    private LocalDateTime fechaCreacion;
	
	@Column(name = "FECHA_ACTUALIZACION" )
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;
	
	@Column(name = "FECHA_TAREA_INICIADA" )
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime fechaTareaIniciada;
	
	
}