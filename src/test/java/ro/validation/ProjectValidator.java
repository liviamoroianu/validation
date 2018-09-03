package ro.validation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.validation.Validation.notNull;

public class ProjectValidator extends DtoValidator<Project> {

    @Override
    public List<Validation> validations(Project dto, String validationPath) {
        return Stream.of(notNull(dto.id, "id"), notNull(dto.priority, "priority")).collect(Collectors.toList());
    }

    @Override
    public String getDtoPathIdentifier(Project dto) {
        return Objects.toString(dto.id, null);
    }

}