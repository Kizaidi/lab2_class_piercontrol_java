package org.example;

public class PierControl {
    private static int totalOrdersCount = 0;
    private static double totalRevenue = 0.0;

    public static int getTotalOrdersCount() { return totalOrdersCount; }
    public static double getTotalRevenue() { return totalRevenue; }

    // Фиксация оплаты — контролируемое изменение статики
    public static void registerOrder(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма заказа не может быть отрицательной");
        }
        totalOrdersCount++;
        totalRevenue += amount;
    }

    public static void printGlobalStats() {
        System.out.println("\n========== ГЛОБАЛЬНАЯ СТАТИСТИКА ==========");
        System.out.printf("Всего оплаченных заказов: %d%n", totalOrdersCount);
        System.out.printf("Суммарная выручка: %.2f руб.%n", totalRevenue);
        System.out.println("============================================");
    }

    public static void main(String[] args) {
        // 1. Создаём дополнение
        LifeVest standardVest = new LifeVest("Спасательный жилет Standart", 500.0);
        LifeVest premiumVest = new LifeVest("Спасательный жилет Premium", -554);

        // 2. Создаём 3 объекта Boat разными конструкторами
        Boat boat1 = new Boat("Каяк «Волна»", 1000.0);                 // доп. конструктор -> Single
        Boat boat2 = new Boat("Лодка «Бриз»", 2000.0, "Double");       // основной -> Double
        Boat boat3 = new Boat("Катамаран «Семья»", 3500.0, "Family");  // основной -> Family

        System.out.println("--- Демонстрация перегрузки calculateFinalPrice ---\n");

        // 3. Последовательно вызываем все три перегруженных варианта

        // Вариант 1: базовая цена
        double price1 = boat1.calculateFinalPrice();
        System.out.printf("%s -> базовая цена: %.2f руб.%n", boat1.getName(), price1);
        registerOrder(price1);

        // Вариант 2: базовая цена + жилет
        double price2 = boat2.calculateFinalPrice(standardVest);
        System.out.printf("%s -> с жилетом «%s»: %.2f руб.%n",
                boat2.getName(), standardVest.getName(), price2);
        registerOrder(price2);

        // Вариант 3: базовая цена + жилет размера XL (+20%)
        double price3 = boat3.calculateFinalPrice(premiumVest, "XL");
        System.out.printf("%s -> с жилетом «%s» (XL): %.2f руб.%n",
                boat3.getName(), premiumVest.getName(), price3);
        registerOrder(price3);

        // Дополнительно: сравнение обычного и XL
        double priceRegular = boat2.calculateFinalPrice(premiumVest, "M");
        System.out.printf("\n[Сравнение] %s с Premium (M): %.2f руб.%n",
                boat2.getName(), priceRegular);
        registerOrder(priceRegular);

        // 5. Итоговая статистика
        printGlobalStats();
    }
}