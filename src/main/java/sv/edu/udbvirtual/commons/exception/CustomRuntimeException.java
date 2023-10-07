package sv.edu.udbvirtual.commons.exception;

import org.springframework.core.NestedRuntimeException;

/***
 * Excepcion Custom para el manejo de excepcion que heredan de Exception para ocuparlo en la capa de servicio para realizar el rollback
 * @author JMenendez
 */
public class CustomRuntimeException extends NestedRuntimeException {

	private static final long serialVersionUID = -4113858238735418604L;

	public CustomRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomRuntimeException(String message) {
		super(message);
	}

}