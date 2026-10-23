package org.example;

public class LifeVest {
    private final String name;      // наименование
    private double price;           // базовая стоимость

    public LifeVest(String name, double price) {
        this.name = name;
        setPrice(price);
    }

    public String getName() { return name; }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Стоимость жилета не может быть отрицательной");
        }
        this.price = price;
    }
}