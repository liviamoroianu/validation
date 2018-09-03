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
    public List<Validation> validations(Employee employee, String validationPath) {
        List<Validation> validations = super.validations(employee, validationPath);
        validations.addAll(validateProjects(employee.assignedProjects));
        return validations;
    }

    private List<Validation> validateProjects(List<Project> projects) {
        return projects.stream()
                .map(project -> new Validation.ValidationBuilder<Project>()
                        .withObject(project)
                        .withCurrentPath("assignedProjects")
                        .withValidator(projectValidator)
                        .build())
                .collect(Collectors.toList());
    }


}
