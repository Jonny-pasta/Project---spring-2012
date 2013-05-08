package fi.muni.pv168;

/**
 * enumeration of meal category, what's the key element in the meal
 * @author Mimo
 */
public enum MealCategory {
    MEAT, MEATLESS, FISH, SWEET, ALCOHOLIC, NONALCOHOLIC, PASTA;
    
    /**
     * return enum value for given int
     * @param x given int
     * @return enum value
     */
    public static MealCategory fromInt(int x){
        switch(x) {
            case 0:
                return MEAT;
            case 1:
                return MEATLESS;
            case 2:
                return FISH;
            case 3:
                return SWEET;
            case 4:
                return ALCOHOLIC;
            case 5:
                return NONALCOHOLIC;
            case 6:
                return PASTA;
        }
        return null;
    }
    
    /**
     * return int representation of given enum
     * @param category given enum
     * @return int representation
     */
    public static int toInt(MealCategory category){
        switch(category) {
            case MEAT:
                return 0;
            case MEATLESS:
                return 1;
            case FISH:
                return 2;
            case SWEET:
                return 3;
            case ALCOHOLIC:
                return 4;
            case NONALCOHOLIC:
                return 5;
            case PASTA:
                return 6;
        }
        return -1;
    }
}
