package ro.validation;

import static ro.validation.Validation.notNull;

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

    private Validation<T> isValidAge(T dto) {
        /*return Validation.ValidationBuilder
                .aValidation()
                .withObject(dto)
                .withCurrentPath("age")
                .withConditionOrElse((Validator<T>) person -> {
                    if (person.getAge() <= 0) {
                        return ValidationResult.anError("age", "invalid age", "age should be a positive number");
                    }
                    return ValidationResult.valid();
                })
                .build();*/
        return new Validation.ValidationBuilder<T>()
                .withObject(dto)
                .withCurrentPath("age")
                .withConditionOrElse((person) -> person.getAge() <= 0, "invalid age", "age should be a positive number")
                .build();
    }

    @Override
    public String getDtoPathIdentifier(T dto) {
        return dto.getUniqueId();
    }

}
