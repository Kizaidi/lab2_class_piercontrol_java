package org.example;

public class Boat {
    private final String name;              // название лодки
    private final double basePrice;         // базовая стоимость
    private final String boatClass;         // класс: "Single", "Double", "Family"
    private LifeVest appliedVest = null;    // агрегация

    // Коэффициенты тарифов
    private static final double COEF_SINGLE = 1.0;
    private static final double COEF_DOUBLE = 1.5;   // +50%
    private static final double COEF_FAMILY = 2.0;   // +100%
    private static final double XL_SURCHARGE = 0.2;  // +20% за XL

    // Основной конструктор
    public Boat(String name, double basePrice, String boatClass) {
        this.name = name;
        this.basePrice = basePrice;
        this.boatClass = boatClass;
    }

    // Дополнительный конструктор — цепочка через this(...)
    public Boat(String name, double basePrice) {
        this(name, basePrice, "Single");
    }

    // Геттеры
    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
    public String getBoatClass() { return boatClass; }
    public LifeVest getAppliedVest() { return appliedVest; }

    // Коэффициент по классу лодки — switch expression
    private double getClassCoefficient() {
        return switch (boatClass) {
            case "Single" -> COEF_SINGLE;
            case "Double" -> COEF_DOUBLE;
            case "Family" -> COEF_FAMILY;
            default -> throw new IllegalArgumentException("Неизвестный класс лодки: " + boatClass);
        };
    }

    // 1-й перегруженный метод — базовая цена с учётом тарифа
    public double calculateFinalPrice() {
        return basePrice * getClassCoefficient();
    }

    // 2-й перегруженный метод — с добавлением жилета
    public double calculateFinalPrice(LifeVest vest) {
        this.appliedVest = vest;
        return calculateFinalPrice() + vest.getPrice();
    }

    // 3-й перегруженный метод — жилет с учётом размера (XL +20%)
    public double calculateFinalPrice(LifeVest vest, String size) {
        if (vest == null) {
            throw new IllegalArgumentException("Жилет не может быть null");
        }
        if (size == null || size.isBlank()) {
            throw new IllegalArgumentException("Размер не может быть пустым");
        }

        double vestPrice = vest.getPrice();
        if ("XL".equalsIgnoreCase(size)) {
            vestPrice *= (1 + XL_SURCHARGE);
        }

        this.appliedVest = vest;
        return calculateFinalPrice() + vestPrice;
    }

    @Override
    public String toString() {
        return String.format("Boat{name='%s', class='%s', base=%.2f, vest=%s}",
                name, boatClass, basePrice,
                appliedVest == null ? "нет" : appliedVest.getName());
    }
}