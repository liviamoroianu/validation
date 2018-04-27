package ro.validation;

import static ro.validation.NotNullValidation.notNull;

public abstract class PersonValidatorImpl<T extends Person> extends DtoValidator<T> {

    public boolean isValidatorFor(T dto) {
        return true;
    }

    @Override
    protected ValidationChecks.ValidationChecksBuilder getAccumulator(T dto, String validationPath) {
        return super.getAccumulator(dto, validationPath)
                .withValidation(notNull(dto.getAge(), append(validationPath, "age")));
    }

    @Override
    public String getDtoPathIdentifier(T dto) {
        return dto.getUniqueId();
    }

}
