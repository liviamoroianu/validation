package ro.validation;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static final long LOW_PRIORITY = 10L;

    protected Employee anEmployee() {
        return Employee.EmployeeBuilder.anEmployee()
                .withUniqueId("232ASDF")
                .withAge(25)
                .withAssignedProjects(someProjects())
                .withDepartmentId(2L)
                .build();
    }

    protected List<Project> someProjects() {
        return Arrays.asList(aProject());
    }

    private Project aProject() {
        return new Project(1L, LOW_PRIORITY);
    }

    protected Employee.EmployeeBuilder anEmployeeDefaultBuilder() {
        return Employee.EmployeeBuilder.anEmployee()
                .withUniqueId("232ASDF")
                .withAge(25)
                .withAssignedProjects(someProjects())
                .withDepartmentId(2L);
    }


}
