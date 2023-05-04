package exercise;

public class CustomException extends RuntimeException {
    public String fieldName;

    public CustomException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

}
