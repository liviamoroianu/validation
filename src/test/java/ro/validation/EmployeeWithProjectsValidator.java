package ro.validation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeWithProjectsValidator extends PersonValidatorImpl<Employee> {

    private ProjectValidator projectValidator;

    public EmployeeWithProjectsValidator(ProjectValidator projectValidator) {
        this.projectValidator = projectValidator;
    }

    @Override
    public boolean isValidatorFor(Employee dto) {
        return CollectionUtils.isNotEmpty(dto.assignedProjects);
    }

    @Override
    public ValidationChecks.ValidationChecksBuilder getAccumulator(Employee employee, String validationPath) {
        return super.getAccumulator(employee, validationPath)
                .withValidations(validateProjects(employee.assignedProjects, validationPath));
    }

    private List<Validation> validateProjects(List<Project> projects, String validationPath) {
        return projects.stream()
                .map(project -> Validation.ValidationBuilder
                        .aValidation()
                        .withObject(project)
                        .withCurrentPath(append(validationPath, "assignedProjects"))
                        .withValidator(projectValidator)
                        .build())
                .collect(Collectors.toList());
    }


}
