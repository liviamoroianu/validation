package ro.validation;

public class Person {

    private String uniqueId;
    private Integer age;

    public Person(PersonBuilder personBuilder) {
        this.uniqueId = personBuilder.uniqueId;
        this.age = personBuilder.age;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public static abstract class PersonBuilder<T extends PersonBuilder> {
        private String uniqueId;
        private Integer age;

        protected PersonBuilder() {
        }

        public T withUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
            return self();
        }

        public T withAge(Integer age) {
            this.age = age;
            return self();
        }

        protected abstract T self();
    }
}
