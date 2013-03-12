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

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + this.amount;
        hash = 97 * hash + (this.unit != null ? this.unit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.amount != other.amount) {
            return false;
        }
        if ((this.unit == null) ? (other.unit != null) : !this.unit.equals(other.unit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "name=" + name + ", amount=" + amount + ", unit=" + unit + "}";
    }   
}
