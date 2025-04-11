package domain; 
/**
 * Write a description of class Plan15Exception here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plan15Exception extends Exception{
    
    public static final String CREDITS_UNKNOWN = "CREDITS_UNKNOWN";
    public static final String CREDITS_ERROR = "CREDITS_ERROR";
    public static final String IN_PERSON_UNKNOWN = "IN_PERSON_UNKNOWN";
    public static final String IN_PERSON_ERROR = "IN_PERSON_ERROR";
    public static final String IMPOSSIBLE = "IMPOSSIBLE";
    public static final String INVALID_NAME = "INVALID_NAME";
    public static final String PORTAGE_ERROR = "PORTAGE_ERROR";
    public static final String INVALID_CODE = "INVALID_CODE";
    public static String INVALID_CREDITS = "Creditos u Horas No Son Enteros";
    public static String INVALID_INPERSON = "Horas Inconsistentes";
    public static String INVALID_NAMES = "Nombres Iguales. Deben Ser Diferentes.";
    
    
    public Plan15Exception(String message){
        super(message); 
    }

}
