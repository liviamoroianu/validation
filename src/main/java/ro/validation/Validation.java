package ro.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * A class used to store a validation check to be performed on a dto in the form of an explicit validator
 * or a callable statement
 *
 * @param <T>
 */
public class Validation<T> {

    /**
     * Validate through a custom validator call
     */
    private DtoValidator<T> validator;
    /**
     * Object to be validated
     */
    protected T object;
    /**
     * Current path in the validation result tree
     */
    protected String currentPath;

    /**
     * An errorPredicate which if not evaluated to true will return a validation result with the error code and message provided
     */
    protected Predicate<T> errorPredicate;

    /**
     * The error code returned in the validation result if the errorPredicate evaluates to true
     */
    private String errorCode;

    /**
     * The error message returned in the validation result if the errorPredicate evaluates to true
     */
    private String errorMessage;

    public Validation(T object, String currentPath) {
        this.object = object;
        this.currentPath = currentPath;
    }

    public Validation(T object, String currentPath, DtoValidator<T> validator) {
        this.object = object;
        this.currentPath = currentPath;
        this.validator = validator;
    }

    private String getPath(String currentPath, String fieldName) {
        if (fieldName == null) {
            return currentPath;
        } else {
            return StringUtils.join(Arrays.asList(StringUtils.stripToEmpty(currentPath), StringUtils.stripToEmpty(fieldName)), ".");
        }
    }

    protected ValidationResult validate() {
        if (validator != null) {
            return callValidator();
        }
        if (errorPredicate != null) {
            if (errorPredicate.test(object)) {
                return ValidationResult.anError(currentPath, errorCode, errorMessage);
            }
        }
        return ValidationResult.valid();
    }

    private ValidationResult callValidator() {
        String fieldName = validator.getDtoPathIdentifier(object);
        return validator.getAccumulator(object, getPath(currentPath, fieldName))
                .build().validate();
    }

    public static Validation notNull(Object object, String currentPath) {
        return new ValidationBuilder<>()
                .withObject(object)
                .withCurrentPath(currentPath)
                .withConditionOrElse((obj) -> nullOrEmpty(object), "MISSING", "Missing field")
                .build();
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

    public static final class ValidationBuilder<S> {
        protected S object;
        private String currentPath;
        private DtoValidator<S> validator;
        private Predicate<S> predicate;
        private String errorCode;
        private String errorMessage;

        public ValidationBuilder() {
        }

        public ValidationBuilder<S> withValidator(DtoValidator<S> validator) {
            this.validator = validator;
            return this;
        }

        public ValidationBuilder<S> withConditionOrElse(Predicate<S> predicate, String errorCode, String errorMessage) {
            this.predicate = predicate;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            return this;
        }

        public ValidationBuilder<S> withObject(S object) {
            this.object = object;
            return this;
        }

        public ValidationBuilder<S> withCurrentPath(String path) {
            this.currentPath = path;
            return this;
        }

        public Validation<S> build() {
            if (validator == null && predicate == null) {
                throw new IllegalArgumentException("Bad validation configuration");
            }

            if (predicate != null && (StringUtils.isEmpty(errorCode) || StringUtils.isEmpty(errorMessage))) {
                throw new IllegalArgumentException("Bad validation configuration");
            }

            Validation<S> validation = new Validation<S>(object, currentPath);
            validation.errorPredicate = predicate;
            validation.validator = validator;
            validation.errorCode = errorCode;
            validation.errorMessage = errorMessage;
            return validation;
        }

    }
}
