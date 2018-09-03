package ro.validation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeWithProjectsValidatorTest extends TestUtils {

    EmployeeWithProjectsValidator validator;

    @Before
    public void setup() {
        ProjectValidator projectValidator = new ProjectValidator();
        validator = new EmployeeWithProjectsValidator(projectValidator);
    }

    @Test
    public void appliesOnlyToEmployeesWithProjects() throws Exception {
        Employee employeeWithProjects = anEmployee();

        boolean validatorApplies = validator.isValidatorFor(employeeWithProjects);

        assertThat(validatorApplies).isTrue();
    }

    @Test
    public void employeeHavingUnknownProject() {
        Employee employeeWithProjects = anEmployeeDefaultBuilder().withAssignedProjects(someInvalidProjects()).build();

        ValidationResult validationResult = this.validator.validate(employeeWithProjects, "employee");

        assertThat(validationResult.hasErrors()).isTrue();
        assertThat(validationResult.getFieldPath()).isEqualTo("employee.assignedProjects.id");
        assertThat(validationResult.getErrorCode()).isEqualTo("MISSING");
        assertThat(validationResult.getMessage()).isEqualTo("Missing field");
    }

    protected List<Project> someInvalidProjects() {
        Project valid = new Project(1L, LOW_PRIORITY);
        Project invalid = new Project(null, null);
        return Arrays.asList(valid, invalid);
    }

    @Test
    public void employeeHavingProjectWithNoPriority() throws Exception {
        Employee employeeWithProjects = anEmployeeDefaultBuilder().withAssignedProjects(projectWithNoPriority()).build();

        ValidationResult validationResult = this.validator.validate(employeeWithProjects, "employee");

        assertThat(validationResult.hasErrors()).isTrue();
        assertThat(validationResult.getFieldPath()).isEqualTo("employee.assignedProjects.2.priority");
        assertThat(validationResult.getErrorCode()).isEqualTo("MISSING");
        assertThat(validationResult.getMessage()).isEqualTo("Missing field");
    }

    protected List<Project> projectWithNoPriority() {
        Project valid = new Project(1L, LOW_PRIORITY);
        Project invalid = new Project(2L, null);
        return Arrays.asList(valid, invalid);
    }


    @Test
    public void employeeWithProjectsIsValid() throws Exception {
        Employee employeeWithProjects = anEmployee();

        ValidationResult validationResult = this.validator.validate(employeeWithProjects, "employee");

        assertThat(validationResult.hasErrors()).isFalse();
    }

}