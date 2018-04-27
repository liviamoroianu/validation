package ro.validation;

@FunctionalInterface
public interface Validator<T> {

    ValidationResult validate(T object);

}
