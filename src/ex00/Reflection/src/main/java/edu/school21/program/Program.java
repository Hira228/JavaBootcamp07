package edu.school21.program;

import edu.school21.employee.User;
import edu.school21.models.Car;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

            System.out.println("fields:");
            Arrays.stream(fields).map(field -> "\t" + field.getType().getSimpleName()
                    + " " +
                    field.getName()).forEach(System.out::println);

            System.out.println("methods:");
            Method[] methods = aClass.getDeclaredMethods();
            Arrays.stream(methods)
                    .map(method -> "\t" + Arrays.stream(method.getParameterTypes())
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(", "))
                            + " " + method.getName() +  " " + Arrays.toString(method.getParameterTypes()))
                    .forEach(System.out::println);

            System.out.println("---------------------");

            System.out.println("Let’s create an object.");
            Object user = null;
            try {
                Class<?> clazz = Class.forName(User.class.getName());
                Class<?>[] params = {String.class, String.class, int.class};
                List<String> list= new ArrayList<>();
                for(Field field : fields) {
                    System.out.println(field.getName()+":");
                    list.add(scanner.nextLine());
                }
                try {
                    user = clazz.getConstructor(params).newInstance(list.get(0), list.get(1), Integer.parseInt(list.get(2)));
                } catch (NumberFormatException err) {
                    System.out.println("Норм данные вводи");
                    System.exit(0);
                }
                System.out.println(user);
                System.out.println("---------------------");
                System.out.println("Enter name of the field for changing:");
                String nameFieldForChanging = scanner.nextLine();
                for(Field field : fields) {
                    if(field.getName().equals(nameFieldForChanging)) {
                        if(field.getType() == String.class) {
                            System.out.println("Enter String value:");
                            field.setAccessible(true);
                            field.set(user, scanner.nextLine());
                        } else if(field.getType() == int.class) {
                            System.out.println("Enter Integer value:");
                            field.setAccessible(true);
                            field.set(user, Integer.parseInt(scanner.nextLine()));
                        }
                        System.out.println(user);
                    }
                }
                System.out.println("---------------------");
                System.out.println("Enter name of the method for call:");
                String nameCallsMethod = scanner.nextLine();
                for(Method method : methods) {
                    if(method.getName().equals(nameCallsMethod)) {
                        method.setAccessible(true);
                        System.out.println("Enter int value:");
                        System.out.println("Method returned: \n" + method.invoke(user, Integer.parseInt(scanner.nextLine())));
                    }
                }



            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
        }
        else if ("Car".equals(nameClass)) {
            Class<?> aClass = Class.forName("edu.school21.models.Car");
            Field[] fields = aClass.getDeclaredFields();

            System.out.println("fields:");
            Arrays.stream(fields).map(field -> "\t" + field.getType().getSimpleName()
                    + " " +
                    field.getName()).forEach(System.out::println);

            System.out.println("methods:");
            Method[] methods = aClass.getDeclaredMethods();
            Arrays.stream(methods)
                    .map(method -> "\t" + Arrays.stream(method.getParameterTypes())
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(", "))
                            + " " + method.getName() + " " + Arrays.toString(method.getParameterTypes()))
                    .forEach(System.out::println);

            System.out.println("---------------------");

            System.out.println("Let’s create an object.");
            Object car = null;
            try {
                Class<?> clazz = Class.forName(Car.class.getName());
                Class<?>[] params = {String.class, Integer.class, Long.class};
                List<String> list = new ArrayList<>();
                for (Field field : fields) {
                    System.out.println(field.getName() + ":");
                    list.add(scanner.nextLine());
                }
                try {
                    car = clazz.getConstructor(params).newInstance(list.get(0), Integer.parseInt(list.get(1)), Long.parseLong(list.get(2)));
                } catch (NumberFormatException err) {
                    System.out.println("Норм данные вводи");
                    System.exit(0);
                }
                System.out.println(car);
                System.out.println("---------------------");
                System.out.println("Enter name of the field for changing:");
                String nameFieldForChanging = scanner.nextLine();
                for (Field field : fields) {
                    if (field.getName().equals(nameFieldForChanging)) {
                        if (field.getType() == String.class) {
                            System.out.println("Enter String value:");
                            field.setAccessible(true);
                            field.set(car, scanner.nextLine());
                        } else if (field.getType() == Integer.class) {
                            System.out.println("Enter Integer value:");
                            field.setAccessible(true);
                            field.set(car, Integer.parseInt(scanner.nextLine()));
                        } else if (field.getType() == Long.class) {
                            System.out.println("Enter Long value:");
                            field.setAccessible(true);
                            field.set(car, Long.parseLong(scanner.nextLine()));
                        }
                        System.out.println(car);
                    }
                }
                System.out.println("---------------------");
                System.out.println("Enter name of the method for call:");
                String nameCallsMethod = scanner.nextLine();
                for (Method method : methods) {
                    if (method.getName().equals(nameCallsMethod)) {
                        method.setAccessible(true);
                        System.out.println("Method returned: \n" + method.invoke(car));
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
        }else {
            throw new RuntimeException("нет такого класса");
        }
    }
}
