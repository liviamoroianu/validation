package ro.validation;

import java.util.Objects;

import static ro.validation.Validation.notNull;

public class ProjectValidator extends DtoValidator<Project> {

    @Override
    public ValidationChecks.ValidationChecksBuilder getAccumulator(Project dto, String validationPath) {
        return ValidationChecks.ValidationChecksBuilder.aValidationChecks()
                .withPath(validationPath)
                .withValidation(notNull(dto.id, "id"))
                .withValidation(notNull(dto.priority, "priority"));
    }

    @Override
    public String getDtoPathIdentifier(Project dto) {
        return Objects.toString(dto.id, null);
    }

}