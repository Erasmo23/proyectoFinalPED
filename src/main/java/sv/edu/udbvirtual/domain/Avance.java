package sv.edu.udbvirtual.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
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
import jakarta.validation.constraints.NotNull;
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
@Table(name = "AVANCES")
public class Avance {

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
    @JoinColumn(name = "ID_TAREA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
	private Tarea tarea;
    
    @Column(name = "DESCRIPCION" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 250, message = "El campo descripcion excede la longitud permitida")
    private String descripcion;
    
    @Column(name = "PESO_AVANCE" )
	@NotNull(message = "No puede estar vacio el campo pesoAvance")
	private Integer pesoAvance;
    
    @Column(name = "FECHA_CREACION" )
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    @JsonFormat(pattern = Constants.DATE_FORMAT)
	@CreatedDate
    private LocalDate fechaCreacion;
    
    @Column(name = "FECHA_VENCIMIENTO" )
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    @JsonFormat(pattern = Constants.DATE_FORMAT)
	@CreatedDate
    private LocalDate fechaVencimiento;
    
    
    public String getFechaVencimientoFormateada() {
    	return this.fechaVencimiento != null ? this.fechaVencimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }
    
    public String getTareaEstadoDelegate() {
    	return this.tarea != null ? this.tarea.getCcEstadoCodigoDelegate() : "";
    }
    
}