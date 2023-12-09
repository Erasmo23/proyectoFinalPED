package sv.edu.udbvirtual.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum CcEstadoEnum {

	CREADA("CREADA", "CREADA"), INICIADA("INICIADA", "INICIADA"), PROCESO("EN_PROCESO", "EN PROCESO"),
	FINALIZADA("FINA", "FINALIZADA"), FINALIZADA_IMCOMPLETA("FINA_INCO", "FINALIZADA INCOMPLETA");

	private String codigo;
	private String descripcion;

	private CcEstadoEnum(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	private static final Map<String, CcEstadoEnum> lookup = new HashMap<>();

	static {
		for (CcEstadoEnum estado : values()) {
			lookup.put(estado.getCodigo(), estado);
		}
	}

	public static CcEstadoEnum getOne(final String codigo) {
		return lookup.get(codigo);
	}

}