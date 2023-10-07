package sv.edu.udbvirtual.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class TransformerMessageValidation {

	@Autowired
	private MessageSource messageSource;
	
	/***
     * Metodo para obtener los mensajes de error de la validacion en el archivo y colocarlos en el ServiceResponse.data para mostrarlos desde una peticion AJAX
     * @param bindingResult
     * @return
     */
    public ServiceResponse getServiceResponseError(BindingResult bindingResult) {
    	StringBuilder errorString = new StringBuilder("<p>");
		bindingResult.getFieldErrors().forEach(erro -> errorString.append(messageSource.getMessage(erro, LocaleContextHolder.getLocale())).append("<br>") );
		errorString.append("</p>");
		return new ServiceResponse(Boolean.FALSE,errorString.toString());
	}
	
}