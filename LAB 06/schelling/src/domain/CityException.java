package domain;

public class CityException extends RuntimeException {
    public static String FILE_NOT_SAVED = "The file was not saved correctly";
    public static String OPENING_FILE_ERROR = "Error opening file";
    public static String IMPORTING_FILE_ERROR = "Error importing file";
    public static String EXPORTING_FILE_ERROR = "Error exporting file";
    public static String FILE_NOT_FOUND = "File was not found";
    public static String NULL_FILE = "File cannot be null";

    public CityException(String message) {
        super(message);
    }
}
