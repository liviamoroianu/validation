package ro.validation;

import java.util.Objects;

import static ro.validation.NotNullValidation.notNull;

public class ProjectValidator extends DtoValidator<Project> {

    @Override
    public ValidationChecks.ValidationChecksBuilder getAccumulator(Project dto, String path) {
        return super.getAccumulator(dto, path)
                .withValidation(notNull(dto.id, append(path, "id")))
                .withValidation(notNull(dto.priority, append(path, "priority")));
    }

    @Override
    public String getDtoPathIdentifier(Project dto) {
        return Objects.toString(dto.id, null);
    }

}