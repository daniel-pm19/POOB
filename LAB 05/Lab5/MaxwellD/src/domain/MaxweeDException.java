package domain;

public class MaxweeDException extends Exception {
    public static final String TOOMANYPARTICLES = "Hay demasiadas particulas";
    public MaxweeDException(String message) {
        super(message);
    }
}
