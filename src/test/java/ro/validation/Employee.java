package ro.validation;

import java.util.List;

public class Employee extends Person {

    public Long departmentId;
    public List<Project> assignedProjects;
    public Boolean senior;

    public Employee(EmployeeBuilder employeeBuilder) {
        super(employeeBuilder);
        this.departmentId = employeeBuilder.departmentId;
        this.assignedProjects = employeeBuilder.projects;
        this.senior = employeeBuilder.senior;
    }

    public boolean isSenior() {
        return senior;
    }

    public static final class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {

        private Long departmentId;
        private List<Project> projects;
        public Boolean senior;

        private EmployeeBuilder() {

        }

        public static EmployeeBuilder anEmployee() {
            return new EmployeeBuilder();
        }

        @Override
        protected EmployeeBuilder self() {
            return this;
        }

        public EmployeeBuilder withDepartmentId(Long departmentId) {
            this.departmentId = departmentId;
            return self();
        }


        public EmployeeBuilder withSenior(Boolean senior) {
            this.senior = senior;
            return self();
        }

        public EmployeeBuilder withAssignedProjects(List<Project> projects) {
            this.projects = projects;
            return self();
        }

        public Employee build() {
            return new Employee(this);
        }
    }

}
