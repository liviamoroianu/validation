package ro.validation;

public class PersonIsYoungValidator extends PersonValidatorImpl<Person> {

    @Override
    public boolean isValidatorFor(Person person) {
        return true;
    }

    @Override
    public ValidationChecks.ValidationChecksBuilder getAccumulator(Person person, String validationPath) {
        return super.getAccumulator(person, validationPath)
                .withValidation(personIsYoung(person, validationPath));
    }

    private Validation personIsYoung(Person person, String validationPath) {
        return new Validation(person, validationPath, (Validator<Person>) object -> {
            if (!isYoung(person)) {
                return ValidationResult.anError(validationPath, "PERSON_AGE", "Person is old");
            }
            return ValidationResult.valid();

        });
    }

    private boolean isYoung(Person person) {
        return person.getAge() < 35;
    }

}
