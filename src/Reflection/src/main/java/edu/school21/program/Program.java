package edu.school21.program;

import edu.school21.employee.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    static private final String classes = "Classes:";
    static private final String user = "  - User";
    static private final String car = "  - Car";


    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(classes);
        System.out.println(user);
        System.out.println(car);
        System.out.println("---------------------");
        System.out.println("Enter class name:");
        String nameClass = scanner.nextLine();
        if ("User".equals(nameClass)) {
            Class<?> aClass = Class.forName("edu.school21.employee.User");
            Field[] fields = aClass.getDeclaredFields();
            Arrays.stream(fields).map(field ->  field.getGenericType().getTypeName()
                    + " " +
                    field.getName()).forEach(System.out::println);
        }
        else if ("Car".equals(nameClass)) {

        } else {
            throw new RuntimeException("нет такого класса");
        }
    }
}
