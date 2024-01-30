package edu.school21.employee;


import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

@OrmEntity(table = "simple_user")
public class User {

    @OrmColumnId
    private Long id;

    @OrmColumn(name = "first_name", length = 10)
    private String firstName;

    @OrmColumn(name = "last_name", length = 10)
    private String lastName;

    @OrmColumn(name = "age")
    private Integer age;



    public User() {
        this.firstName = "Default first name";
        this.lastName = "Default last name";
        this.age = 0;
    }

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    private int grow(int value) {
        this.age += value;
        return age;
    }

    @Override
    public String toString() {
        return new StringBuilder(User.class.getSimpleName())
                .append("[")
                .append("firstName='").append(firstName).append("', ")
                .append("lastName='").append(lastName).append("', ")
                .append("age=").append(age)
                .append("]")
                .toString();
    }
}

