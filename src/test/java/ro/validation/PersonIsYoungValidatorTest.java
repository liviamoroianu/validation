package ro.validation;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonIsYoungValidatorTest extends TestUtils {

    private PersonIsYoungValidator validator;

    @Before
    public void setup() {
        validator = new PersonIsYoungValidator();
    }

    @Test
    public void employeeHasNoAge() {
        Employee employeeWithNoAge = anEmployeeDefaultBuilder().withAge(null).build();
        ValidationResult validationResult = validator.validate(employeeWithNoAge, "employee");

        assertThat(validationResult.hasErrors()).isTrue();
        assertThat(validationResult.getFieldPath()).isEqualTo("employee.age");
        assertThat(validationResult.getErrorCode()).isEqualTo("MISSING");
        assertThat(validationResult.getMessage()).isEqualTo("Missing field");
    }

    @Test
    public void employeeHasInvalidAge() {
        Employee employeeWithNoAge = anEmployeeDefaultBuilder().withAge(-1).build();
        ValidationResult validationResult = validator.validate(employeeWithNoAge, "employee");

        assertThat(validationResult.hasErrors()).isTrue();
        assertThat(validationResult.getFieldPath()).isEqualTo("employee.age");
        assertThat(validationResult.getErrorCode()).isEqualTo("invalid age");
        assertThat(validationResult.getMessage()).isEqualTo("age should be a positive number");
    }


    @Test
    public void employeeIsValid() {
        Employee employee = anEmployee();
        ValidationResult validationResult = validator.validate(employee, "");
        assertThat(validationResult.hasErrors()).isFalse();
    }

}