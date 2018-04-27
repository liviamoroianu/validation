package ro.validation;

import java.util.List;

/**
 * Custom implementation of Validation for null check
 */
public class NotNullValidation extends Validation {

    public NotNullValidation(Object object, String currentPath, Validator validatorCall) {
        super(object, currentPath, (Validator<Object>) obj -> {
            if (nullOrEmpty(obj)) {
                return ValidationResult.anError(currentPath, "MISSING", "Missing field");
            }
            return ValidationResult.valid();
        });
    }

    public static Validation notNull(Object object, String currentPath) {
        return new Validation(object, currentPath, (Validator<Object>) obj -> {
            if (nullOrEmpty(obj)) {
                return ValidationResult.anError(currentPath, "MISSING", "Missing field");
            }
            return ValidationResult.valid();
        });
    }

    private static boolean nullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String && ((String) object).
                isEmpty()) {
            return true;
        }
        if (object instanceof List && ((List) object).
                isEmpty()) {
            return true;
        }

        return false;
    }
}
