package ro.validation;

import java.util.List;

public class PersonIsYoungValidator extends PersonValidatorImpl<Person> {

    @Override
    public boolean isValidatorFor(Person person) {
        return true;
    }

    @Override
    public List<Validation> validations(Person person, String validationPath) {
        List<Validation> validations = super.validations(person, validationPath);
        validations.add(personIsYoung(person));
        return validations;
    }

    private Validation personIsYoung(Person person) {
        return new Validation.ValidationBuilder<Person>()
                .withObject(person)
                .withErrorOnConditions(pers -> !isYoung(pers), "PERSON_AGE", "Person is old")
                .build();
    }

    private boolean isYoung(Person person) {
        return person.getAge() < 35;
    }

}
