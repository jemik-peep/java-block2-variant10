import java.math.BigDecimal;
import java.math.RoundingMode;

public final class TrainTicket {
    private static final String DEFAULT_CLASS = "Плацкарт";
    private static final double PLATZKART_MULT = 1.0;
    private static final double COUPE_MULT = 1.7;
    private static final double SV_MULT = 2.8;

    private final String passengerName;
    private double basePrice;
    private String carriageClass;
    private MealPlan appliedMeal;

    public TrainTicket(String passengerName, double basePrice) {
        this(passengerName, basePrice, DEFAULT_CLASS);
    }

    public TrainTicket(String passengerName, double basePrice, String carriageClass) {
        if (passengerName == null || passengerName.isBlank()) {
            throw new IllegalArgumentException("Имя пассажира не может быть пустым");
        }
        this.passengerName = passengerName;
        setBasePrice(basePrice);
        setCarriageClass(carriageClass);
    }

    public String getPassengerName() {
        return passengerName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getCarriageClass() {
        return carriageClass;
    }

    public MealPlan getAppliedMeal() {
        return appliedMeal;
    }

    public void setBasePrice(double basePrice) {
        if (!Double.isFinite(basePrice) || basePrice < 0) {
            throw new IllegalArgumentException("Базовая цена билета должна быть конечной и неотрицательной");
        }
        this.basePrice = basePrice;
    }

    public void setCarriageClass(String carriageClass) {
        if (carriageClass == null || carriageClass.isBlank()) {
            throw new IllegalArgumentException("Класс вагона не может быть пустым");
        }
        getClassMultiplier(carriageClass);
        this.carriageClass = carriageClass;
    }

    public double calculateFinalPrice() {
        BigDecimal price = BigDecimal.valueOf(basePrice)
                .multiply(BigDecimal.valueOf(getClassMultiplier(carriageClass)));
        return toMoney(price);
    }

    public double calculateFinalPrice(MealPlan meal) {
        return calculateFinalPrice(meal, 1);
    }

    public double calculateFinalPrice(MealPlan meal, int portions) {
        if (meal == null) {
            throw new IllegalArgumentException("План питания не задан");
        }
        if (portions <= 0) {
            throw new IllegalArgumentException("Количество порций должно быть положительным целым числом");
        }
        BigDecimal mealCost = BigDecimal.valueOf(meal.getPricePerPortion())
                .multiply(BigDecimal.valueOf(portions));
        return toMoney(BigDecimal.valueOf(calculateFinalPrice()).add(mealCost));
    }

    public double addMeal(MealPlan meal) {
        return addMeal(meal, 1);
    }

    public double addMeal(MealPlan meal, int portions) {
        double finalPrice = calculateFinalPrice(meal, portions);
        this.appliedMeal = meal;
        return finalPrice;
    }

    private static double getClassMultiplier(String carriageClass) {
        return switch (carriageClass.trim().toLowerCase()) {
            case "плацкарт" -> PLATZKART_MULT;
            case "купе" -> COUPE_MULT;
            case "св" -> SV_MULT;
            default -> throw new IllegalArgumentException("Неизвестный класс вагона: " + carriageClass);
        };
    }

    private static double toMoney(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
