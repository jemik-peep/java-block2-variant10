import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public final class RailwayBooking {
    private static int totalTicketsCount = 0;
    private static double totalRevenue = 0.0;

    private RailwayBooking() {
    }

    public static int getTotalTicketsCount() {
        return totalTicketsCount;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static void registerPayment(double amount) {
        if (!Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть конечной и неотрицательной");
        }
        totalTicketsCount++;
        totalRevenue = BigDecimal.valueOf(totalRevenue)
                .add(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static void printGlobalStats() {
        System.out.println("=== Статистика железнодорожной кассы ===");
        System.out.println("Оплачено билетов: " + totalTicketsCount);
        System.out.printf(Locale.US, "Общая выручка: %.2f руб.%n", totalRevenue);
    }

    public static void main(String[] args) {
        System.out.println("Лабораторная работа. Блок 2, часть 1. Вариант 10");
        System.out.println("Продажа билетов: железнодорожный билет и план питания");

        MealPlan standardMeal = new MealPlan("Комплексный обед", 350.0);
        System.out.printf(Locale.US, "Цена одной порции питания: %.2f руб.%n%n", standardMeal.getPricePerPortion());

        TrainTicket ticket1 = new TrainTicket("Иван Иванов", 2000.0);
        double price1 = ticket1.calculateFinalPrice();
        registerPayment(price1);
        System.out.printf(Locale.US, "Билет 1 | %-13s | %-8s | без питания        | %.2f руб.%n",
                ticket1.getPassengerName(), ticket1.getCarriageClass(), price1);

        TrainTicket ticket2 = new TrainTicket("Анна Смирнова", 2000.0, "Купе");
        double price2 = ticket2.addMeal(standardMeal);
        registerPayment(price2);
        System.out.printf(Locale.US, "Билет 2 | %-13s | %-8s | питание (1 порция) | %.2f руб.%n",
                ticket2.getPassengerName(), ticket2.getCarriageClass(), price2);

        TrainTicket ticket3 = new TrainTicket("Петр Сидоров", 2000.0, "СВ");
        double price3 = ticket3.addMeal(standardMeal, 3);
        registerPayment(price3);
        System.out.printf(Locale.US, "Билет 3 | %-13s | %-8s | питание (3 порции) | %.2f руб.%n%n",
                ticket3.getPassengerName(), ticket3.getCarriageClass(), price3);

        System.out.println("Методы addMeal из таблицы варианта (повторный расчёт без оплаты):");
        System.out.printf(Locale.US, "addMeal(meal): %.2f руб.%n", ticket2.addMeal(standardMeal));
        System.out.printf(Locale.US, "addMeal(meal, 3): %.2f руб.%n%n", ticket3.addMeal(standardMeal, 3));

        printGlobalStats();
    }
}
