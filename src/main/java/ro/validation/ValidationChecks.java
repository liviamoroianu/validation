package ro.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation accumulator
 */
public class ValidationChecks {

    private List<Validation> validations;
    private String path;

    private ValidationChecks(String path, List<Validation> validations) {
        this.path = path;
        this.validations = validations;
    }

    /**
     * Validate object by iterating over all checks and returning on first failed check
     *
     * @return
     */
    public ValidationResult validate() {
        return validations
                .stream()
                .map(validation -> validation.validate())
                .filter(validationResult -> validationResult.hasErrors())
                .findFirst()
                .map(validationResult -> appendCurrentPathToErrorResult(validationResult))
                .orElse(ValidationResult.valid());
    }

    private ValidationResult appendCurrentPathToErrorResult(ValidationResult validationResult) {
        return ValidationResult.anError(append(path, validationResult.getFieldPath()), validationResult.getErrorCode(), validationResult.getMessage());
    }

    private String append(String path, String fieldPath) {
        if (StringUtils.isEmpty(path)) {
            return fieldPath;
        }

        return path + "." + fieldPath;
    }

    public static final class ValidationChecksBuilder {
        private String path;
        private List<Validation> validations;

        private ValidationChecksBuilder() {
            validations = new ArrayList<>();
        }

        public static ValidationChecksBuilder aValidationChecks() {
            return new ValidationChecksBuilder();
        }

        public ValidationChecksBuilder withValidations(List<Validation> validations) {
            this.validations.addAll(validations);
            return this;
        }

        public ValidationChecksBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public ValidationChecksBuilder withValidation(Validation validation) {
            this.validations.add(validation);
            return this;
        }

        public ValidationChecks build() {
            return new ValidationChecks(path, validations);
        }
    }
}
