package beans;

public enum Category {
    FOOD,
    PC,
    VACATION;

    public static Category getCategoryFromDBValue(int value) {
        return Category.values()[value-1];
    }

    public static int getCategoryIntValue(Category value) {
        return value.ordinal()+1;
    }

}
