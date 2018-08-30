package ro.validation;

import static ro.validation.NotNullValidation.notNull;

public abstract class PersonValidatorImpl<T extends Person> extends DtoValidator<T> {

    public boolean isValidatorFor(T dto) {
        return true;
    }

    @Override
    public ValidationChecks.ValidationChecksBuilder getAccumulator(T dto, String validationPath) {
        return ValidationChecks.ValidationChecksBuilder.aValidationChecks()
                .withPath(validationPath)
                .withValidation(notNull(dto.getAge(), "age"))
                .withValidation(isValidAge(dto));
    }

    private Validation isValidAge(T dto) {
        return Validation.ValidationBuilder
                .aValidation()
                .withObject(dto)
                .withCurrentPath("age")
                .withValidatorCall((Validator<T>) person -> {
                    if (person.getAge() <= 0) {
                        return ValidationResult.anError("age", "invalid age", "age should be a positive number");
                    }
                    return ValidationResult.valid();
                })
                .build();
    }

    @Override
    public String getDtoPathIdentifier(T dto) {
        return dto.getUniqueId();
    }

}
