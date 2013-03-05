package fi.muni.pv168;

/**
 * class that represents an ingredient, that should be added into some meal
 * it contains name, amount and unit of amount
 * @date 5.3.2013
 * @author Mimo
 */
public class Ingredient {
    
    /**
     * attributes
     */
    private String name;
    private int amount;
    private String unit;
    
    /**
     * constructors
     * @param name name of the ingredient - what it is
     * @param amount amount to add to the meal
     * @param unit amount of what do add to the meal
     */
    public Ingredient(){}
    public Ingredient(String name, int amount, String unit){
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
    
    /**
     * getters, setters
     */
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAmount() {return amount;}
    public void setAmount(int amount) {this.amount = amount;}
    public String getUnit() {return unit;}
    public void setUnit(String unit) {this.unit = unit;}
}
