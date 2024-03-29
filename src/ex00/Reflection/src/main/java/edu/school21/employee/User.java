package edu.school21.employee;

public class User {
    private String firstName;
    private String lastName;
    private int height;

    public User() {
        this.firstName = "Default first name";
        this.lastName = "Default last name";
        this.height = 0;
    }

    public User(String firstName, String lastName, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    private int grow(int value) {
        this.height += value;
        return height;
    }

    @Override
    public String toString() {
        return new StringBuilder(User.class.getSimpleName())
                .append("[")
                .append("firstName='").append(firstName).append("', ")
                .append("lastName='").append(lastName).append("', ")
                .append("height=").append(height)
                .append("]")
                .toString();
    }
}

