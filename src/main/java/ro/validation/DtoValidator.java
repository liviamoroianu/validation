package ro.validation;

import ro.validation.ValidationChecks.ValidationChecksBuilder;

import java.util.List;

/**
 * This is the base implementation for a business dto validator.
 * It accumulates errors in the an object of type ValidationChecksBuilder
 * A customd validator should implement this class and add validations to be checked
 *
 * @param <T>
 */
public abstract class DtoValidator<T> {

    /**
     * Override if a validator should be applied only in certain conditions
     *
     * @param dto
     * @return
     */
    public boolean isValidatorFor(T dto) {
        return true;
    }

    /**
     * to be implemented in validators in order to initialize an accumulator for a dto
     *
     * @param dto
     * @param validationPath used for keeping the append of current validated object inside a hierarchy of dto objects
     * @return
     */
    protected abstract List<Validation> validations(T dto, String validationPath);

    public ValidationResult validate(T dto, String validationPath) {
        List<Validation> validations = validations(dto, validationPath);
        ValidationChecks validationChecks = ValidationChecksBuilder.aValidationChecks()
                .withPath(validationPath)
                .withValidations(validations)
                .build();
        return validationChecks.validate();
    }

    /**
     * Get an unique identifier for this dto to use in the validation result tree
     *
     * @param dto
     * @return
     */
    public String getDtoPathIdentifier(T dto) {
        return null;
    }
}
