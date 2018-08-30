package ro.validation;

import ro.validation.ValidationChecks.ValidationChecksBuilder;

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
    public abstract ValidationChecks.ValidationChecksBuilder getAccumulator(T dto, String validationPath);

    /**
     * initialize an accumulator for a dto and add a validation check to it
     *
     * @param validation
     * @return
     */
    public ValidationChecksBuilder getAccumulator(Validation validation) {
        return ValidationChecksBuilder
                .aValidationChecks()
                .withValidation(validation);
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
