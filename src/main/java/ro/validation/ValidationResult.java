package ro.validation;

import org.apache.commons.lang3.StringUtils;

/**
 * Stored the result of a validation
 */
public class ValidationResult {

    /**
     * Path in the dto hierarchy to the invalid object
     */
    private String fieldPath;
    /**
     * Error code
     */
    private String errorCode;
    /**
     * Error message
     */
    private String message;

    public static ValidationResult valid() {
        return new ValidationResult();
    }

    private ValidationResult() {

    }

    public static ValidationResult anError(String currentPath, String errorCode, String message) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.fieldPath = currentPath;
        validationResult.errorCode = errorCode;
        validationResult.message = message;
        return validationResult;
    }


    public boolean hasErrors() {
        return StringUtils.isNotEmpty(errorCode);
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
