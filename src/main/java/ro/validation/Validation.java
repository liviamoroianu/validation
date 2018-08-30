package ro.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

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
    private DtoValidator validator;
    /**
     * Object to be validated
     */
    protected T object;
    /**
     * Current path in the validation result three
     */
    protected String currentPath;
    /**
     * A callable function for simpler validations
     */
    protected Validator<T> validatorCall;

    public Validation(T object, String currentPath, DtoValidator validator) {
        this.object = object;
        this.currentPath = currentPath;
        this.validator = validator;
    }

    public Validation(T object, String currentPath, Validator<T> validatorCall) {
        this.object = object;
        this.currentPath = currentPath;
        this.validatorCall = validatorCall;
    }

    public Validation(T object, String currentPath, DtoValidator validator, Validator<T> validatorCall) {
        this.object = object;
        this.currentPath = currentPath;
        this.validator = validator;
        this.validatorCall = validatorCall;
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
        if (validatorCall != null) {
            return validatorCall.validate(object);
        }
        return ValidationResult.valid();
    }

    private ValidationResult callValidator() {
        String fieldName = validator.getDtoPathIdentifier(object);
        return validator.getAccumulator(object, getPath(currentPath, fieldName))
                .build().validate();
    }

    public static final class ValidationBuilder<T> {
        protected T object;
        private String currentPath;
        private DtoValidator validator;
        private Validator<T> callableValidator;

        private ValidationBuilder() {
        }

        public static ValidationBuilder aValidation() {
            return new ValidationBuilder();
        }

        public ValidationBuilder withValidator(DtoValidator validator) {
            this.validator = validator;
            return this;
        }

        public ValidationBuilder withValidatorCall(Validator<T> callableValidator) {
            this.callableValidator = callableValidator;
            return this;
        }

        public ValidationBuilder withObject(T object) {
            this.object = object;
            return this;
        }

        public ValidationBuilder withCurrentPath(String path) {
            this.currentPath = path;
            return this;
        }

        public Validation build() {
            if (validator == null && callableValidator == null) {
                throw new IllegalArgumentException("Bad validation configuration");
            }
            Validation validation = new Validation(object, currentPath, validator, callableValidator);
            return validation;
        }

    }
}
