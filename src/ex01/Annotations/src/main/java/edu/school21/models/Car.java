package edu.school21.models;

public class Car {
    private String name;
    private Integer year;
    private Long price;

    public Car() {}

    public Car(String name, Integer year, Long price) {
        this.name = name;
        this.year = year;
        this.price = price;
    }

    public int newYear(){
        year++;
        return year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
}

