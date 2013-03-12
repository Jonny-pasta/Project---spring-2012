package fi.muni.pv168;

import java.sql.Time;
import java.util.Collections;
import java.util.List;

/**
 * class that represents single recipe in our recipe book
 * @date 5.3.2013
 * @author Mimo
 */
public class Recipe {

    public Recipe(int id, String name, MealType type, MealCategory category, Time cookingTime, int numPortions, String instructions, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.cookingTime = cookingTime;
        this.numPortions = numPortions;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }
    
    private int id;
    private String name;
    private MealType type;
    private MealCategory category;
    private Time cookingTime;
    private int numPortions;
    private String instructions;
    private List<Ingredient> ingredients;
       
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public MealType getType() {return type;}
    public void setType(MealType type) {this.type = type;}

    public MealCategory getCategory() {return category;}
    public void setCategory(MealCategory category) {this.category = category;}

    public List<Ingredient> getIngredients() {return Collections.unmodifiableList(ingredients);}
    public void setIngredients(List<Ingredient> ingredients) {ingredients.addAll(ingredients);}

    public Time getCookingTime() {return cookingTime;}
    public void setCookingTime(Time cookingTime) {this.cookingTime = cookingTime;}
    
    public int getNumPortions() {return numPortions;}
    public void setNumPortions(int numPortions) {this.numPortions = numPortions;}

    public String getInstructions() {return instructions;}
    public void setInstructions(String instructions) {this.instructions = instructions;}
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 89 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 89 * hash + (this.cookingTime != null ? this.cookingTime.hashCode() : 0);
        hash = 89 * hash + this.numPortions;
        hash = 89 * hash + (this.instructions != null ? this.instructions.hashCode() : 0);
        hash = 89 * hash + (this.ingredients != null ? this.ingredients.hashCode() : 0);
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
        final Recipe other = (Recipe) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.category != other.category) {
            return false;
        }
        if (this.cookingTime != other.cookingTime && (this.cookingTime == null || !this.cookingTime.equals(other.cookingTime))) {
            return false;
        }
        if (this.numPortions != other.numPortions) {
            return false;
        }
        if ((this.instructions == null) ? (other.instructions != null) : !this.instructions.equals(other.instructions)) {
            return false;
        }
        if (this.ingredients != other.ingredients && (this.ingredients == null || !this.ingredients.equals(other.ingredients))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", type=" + type + ", category=" + category + ", cookingTime=" + cookingTime + ", numPortions=" + numPortions + ", instructions=" + instructions + ", ingredients=" + ingredients + "}";
    }
    
    
}
