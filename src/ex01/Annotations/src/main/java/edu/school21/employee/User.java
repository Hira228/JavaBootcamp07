package edu.school21.employee;


import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

@HtmlForm(fileName = "user_form.html", action = "/users", method = "post")
public class User {
    @HtmlInput(type = "text", name = "first_name", placeholder = "Enter First Name")
    private String firstName;
    @HtmlInput(type = "text", name = "last_name", placeholder = "Enter Last Name")
    private String lastName;
    @HtmlInput(type = "password", name = "password", placeholder = "Enter Password")
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

