package exercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BEGIN
class Validator {
    private static final String NOT_VALIDATED_NULL_MESSAGE = "can not be null";
    private static final String NOT_VALIDATED_MIN_LENGTH_MESSAGE = "length less than ";
    public static List<String> validate(Address address) {
        List<String> notValidatedFields = new ArrayList<>();
        Field[] fields = address.getClass().getDeclaredFields();
        for (var field : fields) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(address) == null) {
                        notValidatedFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return notValidatedFields;
    }

    public static Map<String, List<String>> advancedValidate(Address address) {
        Map<String, List<String>> notValidatedFields = new HashMap<>();
        Field[] fields = address.getClass().getDeclaredFields();
        for (var field : fields) {
            List<String> validationProblemsList = new ArrayList<>();

            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(address) == null) {
                        validationProblemsList.add(NOT_VALIDATED_NULL_MESSAGE);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.isAnnotationPresent(MinLength.class)) {
                field.setAccessible(true);
                try {
                    var fieldValue = field.get(address);
                    int minLength = field.getAnnotation(MinLength.class).minLength();
                    if (fieldValue instanceof String && ((String) fieldValue).length() < minLength) {
                        validationProblemsList.add(NOT_VALIDATED_MIN_LENGTH_MESSAGE + minLength);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (!validationProblemsList.isEmpty()) {
                notValidatedFields.put(field.getName(), validationProblemsList);
            }
        }
        return notValidatedFields;
    }
}
// END
