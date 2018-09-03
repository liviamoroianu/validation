package ro.validation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.validation.Validation.notNull;

public abstract class PersonValidatorImpl<T extends Person> extends DtoValidator<T> {

    public boolean isValidatorFor(T dto) {
        return true;
    }

    @Override
    public List<Validation> validations(T dto, String validationPath) {
        return Stream.of(notNull(dto.getAge(), "age"), isValidAge(dto)).collect(Collectors.toList());
    }

    private Validation<T> isValidAge(T dto) {
        /*return Validation.ValidationBuilder
                .aValidation()
                .withObject(dto)
                .withCurrentPath("age")
                .withErrorOnConditions((Validator<T>) person -> {
                    if (person.getAge() <= 0) {
                        return ValidationResult.anError("age", "invalid age", "age should be a positive number");
                    }
                    return ValidationResult.valid();
                })
                .build();*/
        return new Validation.ValidationBuilder<T>()
                .withObject(dto)
                .withCurrentPath("age")
                .withErrorOnConditions((person) -> person.getAge() <= 0, "invalid age", "age should be a positive number")
                .build();
    }

    @Override
    public String getDtoPathIdentifier(T dto) {
        return dto.getUniqueId();
    }

}
