package fi.muni.pv168.backend;

/**
 * enumeration of meal types, when and how should be the meal served
 * @author Mimo
 */
public enum MealType {
    BREAKFAST, APPETIZER, SOUP, MAIN_DISH, DESSERT, SALAD, DRINK;
    
    /**
     * return enum value for given int
     * @param x given int
     * @return enum value
     */
    public static MealType fromInt(int x){
        switch(x) {
            case 0:
                return BREAKFAST;
            case 1:
                return APPETIZER;
            case 2:
                return SOUP;
            case 3:
                return MAIN_DISH;
            case 4:
                return DESSERT;
            case 5:
                return SALAD;
            case 6:
                return DRINK;
        }
        return null;
    }
    
    /**
     * return int representation of given enum
     * @param type given enum
     * @return int representation
     */
    public static int toInt(MealType type){
        switch(type) {
            case BREAKFAST:
                return 0;
            case APPETIZER:
                return 1;
            case SOUP:
                return 2;
            case MAIN_DISH:
                return 3;
            case DESSERT:
                return 4;
            case SALAD:
                return 5;
            case DRINK:
                return 6;
        }
        return -1;
    }
    
    // overriding Object.toString
    @Override
    public String toString() {
                switch(this) {
            case BREAKFAST:
                return "breakfast";
            case APPETIZER:
                return "appetizer";
            case SOUP:
                return "soup";
            case MAIN_DISH:
                return "main dish";
            case DESSERT:
                return "dessert";
            case SALAD:
                return "salad";
            case DRINK:
                return "drink";
        }
        return "";
    }
}
