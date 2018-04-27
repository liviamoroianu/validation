package ro.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation accumulator
 */
public class ValidationChecks {

    private List<Validation> validations;

    public ValidationChecks(List<Validation> validations) {
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
                .orElse(ValidationResult.valid());
    }

    public static final class ValidationChecksBuilder {
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

        public ValidationChecksBuilder withValidation(Validation validation) {
            this.validations.add(validation);
            return this;
        }

        public ValidationChecks build() {
            return new ValidationChecks(validations);
        }
    }
}
