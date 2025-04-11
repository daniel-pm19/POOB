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
    
    public Plan15Exception(String message){
        super(message); 
    }

}
