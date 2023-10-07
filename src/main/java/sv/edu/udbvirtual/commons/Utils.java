package sv.edu.udbvirtual.commons;

import java.beans.FeatureDescriptor;
import java.security.SecureRandom;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.MediaType;

public class Utils {
    
    private static final char[] CARACTERES_DISPONIBLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
    private static final Pattern PATTERN_MAYUSCULA = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_NUMERO = Pattern.compile("\\d");
    private static final Pattern PATTERN_SOLO_NUMERO_Y_LETRAS = Pattern.compile("^[\\d\\w]+$");
    
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
    }

    /**
     * Metodo para obtener la extension de un archivo de su nombre
     *
     * @param originalFilename
     * @return
     */
    public static String getExtensionByNombreArchivo(String originalFilename) {
        if (originalFilename != null) {
            String fileType = null;
            StringTokenizer tokenizer = new StringTokenizer(originalFilename, ".");
            while (tokenizer.hasMoreTokens()) {
                fileType = tokenizer.nextToken();
            }
            return fileType;
        } else {
            return "";
        }
    }

    /**
     * *
     * Dependiendo del tipo de extension asi se devuelve un MediaType
     *
     * @param extension
     * @return
     */
    public static MediaType getMediaTypeByExtensionArchivo(String extension) {
        if (extension == null) {
            return null;
        }
        switch (extension) {
            case "PNG":
                return MediaType.IMAGE_PNG;
            case "JPG","JPEG":
                return MediaType.IMAGE_JPEG;
            case "GIF":
                return MediaType.IMAGE_GIF;
            case "PDF":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public static boolean isInheritanceThreadException(Exception e) {
        return e instanceof InterruptedException || e instanceof ExecutionException;
    }

    public static boolean isNotExtensionVideo(String extension) {
        return !"mp4".equals(extension) && !"webm".equals(extension);
    }

    public static String generarStringAleatorio(int longitud) {
        StringBuilder builder = new StringBuilder(longitud);
        SecureRandom random = new SecureRandom();

        // creo los numeros aleatorios, los convierto a los caracteres y los guardo en una lista
        random.ints(longitud, 0, (CARACTERES_DISPONIBLES.length - 1))
                .mapToObj(valor -> CARACTERES_DISPONIBLES[valor])
                .forEach(builder::append);

        return builder.toString();
    }

    /**
     * Valida que el password se adhiera a las reglas definidas para passwords
     * para este software, es decir:
     *
     * - Debe haber al menos una letra mayuscula. - Debe haber al menos un
     * numero. - Debe haber al menos un caracter especial. - Debe tener entre 8
     * y 12 caracteres (incluyendo ambos valores).
     *
     * @param password el password a probar
     * @return true si cumple con las reglas antes mencionadas, false si no
     */
    public static boolean isPasswordValido(String password) {
        // valido que haya al menos un caracter en mayuscula
        boolean hayCaracterMayusculas = PATTERN_MAYUSCULA.matcher(password).find();
        boolean hayNumero = PATTERN_NUMERO.matcher(password).find();
        boolean hayCaracteresEspeciales = !PATTERN_SOLO_NUMERO_Y_LETRAS.matcher(password).matches();

        boolean isInLongitudValida = password.length() >= 8 && password.length() <= 12;

        return hayCaracterMayusculas && hayNumero && hayCaracteresEspeciales && isInLongitudValida;
    }

}