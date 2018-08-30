package ro.validation;

public class PersonIsYoungValidator extends PersonValidatorImpl<Person> {

    @Override
    public boolean isValidatorFor(Person person) {
        return true;
    }

    @Override
    public ValidationChecks.ValidationChecksBuilder getAccumulator(Person person, String validationPath) {
        return super.getAccumulator(person, validationPath)
                .withValidation(personIsYoung(person));
    }

    private Validation personIsYoung(Person person) {
        return new Validation.ValidationBuilder<Person>()
                .withObject(person)
                .withConditionOrElse(pers -> !isYoung(pers), "PERSON_AGE", "Person is old")
                .build();
    }

    private boolean isYoung(Person person) {
        return person.getAge() < 35;
    }

}
