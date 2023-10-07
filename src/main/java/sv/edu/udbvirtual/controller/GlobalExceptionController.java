package sv.edu.udbvirtual.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador para el manejo de las excepciones globales que pueden ocurrir para ser capturados y manejados como se detalle en cada metodo
 * @author JMenendez
 * @version 1.1
 * @since 14/12/2022
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

	/**
	 * Metodo que retorna una html de error cuando se excede la capacidad maxima que se tiene en el archivo properties
	 * @param e Excepcion de exceder la capacidad maxima de subida al servidor
	 * @param redirectAttributes Atributos que son redireccionados
	 * @return String que contiene la html a renderizar
	 */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
    	log.error("Error al cargar archivos", e);
    	return "error/MultipartException";
    }
    /**
     * Metodo para el retorno de una Respuesta Generica para los servicios Rest
     * @param e {@link EntidadNoEncontradaRestException} que contiene el detalle del fallo
     * @return {@link ServiceResponse} Wrapper para dar una respuesta generica aunque fallara
     */
    /*
    @ExceptionHandler(EntidadNoEncontradaRestException.class)
    public @ResponseBody ServiceResponse handleRestError(EntidadNoEncontradaRestException e) {
    	return new ServiceResponse(Boolean.FALSE, e.getMessage());
    }*/
    
}