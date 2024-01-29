package edu.school21.models;

public class Car {
    private String make;
    private Integer year;
    private Double price;
    private Boolean isNew;
    private Long mileage;

    public Car() {}

    public Car(String make, Integer year, Double price, Boolean isNew, Long mileage) {
        this.make = make;
        this.year = year;
        this.price = price;
        this.isNew = isNew;
        this.mileage = mileage;
    }

    public int grow(){
        year++;
        return year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", isNew=" + isNew +
                ", mileage=" + mileage +
                '}';
    }
}

