package ro.validation;

import org.junit.Before;
import org.junit.Test;
import ro.validation.ValidationChecks.ValidationChecksBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonIsYoungValidatorTest extends TestUtils {

    private PersonIsYoungValidator validator;

    @Before
    public void setup() {
        validator = new PersonIsYoungValidator();
    }

    @Test
    public void isValidatorFor() throws Exception {
    }

    @Test
    public void employeeHasNoAge() throws Exception {
        Employee employeeWithNoAge = anEmployeeDefaultBuilder().withAge(null).build();
        ValidationChecksBuilder checker = validator.getAccumulator(employeeWithNoAge, "employee");
        ValidationResult validationResult = checker.build().validate();

        assertThat(validationResult.hasErrors()).isTrue();
        assertThat(validationResult.getFieldPath()).isEqualTo("employee.age");
        assertThat(validationResult.getErrorCode()).isEqualTo("MISSING");
        assertThat(validationResult.getMessage()).isEqualTo("Missing field");
    }

    @Test
    public void employeeIsValid() throws Exception {
        Employee employee = anEmployee();
        ValidationChecksBuilder checker = validator.getAccumulator(employee, "");
        ValidationResult validationResult = checker.build().validate();
        assertThat(validationResult.hasErrors()).isFalse();
    }

}