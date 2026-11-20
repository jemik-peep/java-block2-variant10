public final class MealPlan {
    private final String mealName;
    private double pricePerPortion;

    public MealPlan(String mealName, double pricePerPortion) {
        if (mealName == null || mealName.isBlank()) {
            throw new IllegalArgumentException("Название плана питания не может быть пустым");
        }
        this.mealName = mealName;
        setPricePerPortion(pricePerPortion);
    }

    public String getMealName() {
        return mealName;
    }

    public double getPricePerPortion() {
        return pricePerPortion;
    }

    public void setPricePerPortion(double pricePerPortion) {
        if (!Double.isFinite(pricePerPortion) || pricePerPortion < 0) {
            throw new IllegalArgumentException("Цена порции должна быть конечным неотрицательным числом");
        }
        this.pricePerPortion = pricePerPortion;
    }
}
