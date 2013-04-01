package fi.muni.pv168;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * class that represents single recipe in our recipe book
 * Note: This class has a natural ordering that is inconsistent with equals.
 * @date 5.3.2013
 * @author Mimo
 */
public class Recipe implements Comparable<Recipe> {
    
    /**
     * constructor
     */
    public Recipe(){}
    
    /**
     * attributes of the recipe: ID, name, type, category, cooking time (in minutes), number of portions, instructions to cook and a set of ingredients
     */
    private Long id;
    private String name;
    private MealType type;
    private MealCategory category;
    //cooking time in minutes
    private int cookingTime;
    private int numPortions;
    private String instructions;
    private SortedSet<Ingredient> ingredients;
           
    /**
     * getters, setters
     */
    public Long getId() {return id;}
    public void setId(Long id) {
        if (id<1) {
            throw new IllegalArgumentException("id has to be possitive");
        }
        this.id = id;
    }

    public String getName() {return name;}
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
    }

    public MealType getType() {return type;}
    public void setType(MealType type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        this.type = type;
    }

    public MealCategory getCategory() {return category;}
    public void setCategory(MealCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        this.category = category;
    }

    public SortedSet<Ingredient> getIngredients() {return Collections.unmodifiableSortedSet(ingredients);}
    public void setIngredients(SortedSet<Ingredient> ingredients) {
        if (ingredients == null) {
            throw new IllegalArgumentException("ingredients cannot be null");
        }
        this.ingredients = new TreeSet<Ingredient>();
        this.ingredients.addAll(ingredients);
    }

    public int getCookingTime() {return cookingTime;}
    public void setCookingTime(int cookingTime) {
        if (cookingTime<0) {
            throw new IllegalArgumentException("time has to be possitive");
        }
        this.cookingTime = cookingTime;
    }
    
    public int getNumPortions() {return numPortions;}
    public void setNumPortions(int numPortions) {
        if (numPortions < 1) {
            throw new IllegalArgumentException("num of portions has to be possitive");
        }
        this.numPortions = numPortions;
    }

    public String getInstructions() {return instructions;}
    public void setInstructions(String instructions) {
        if (instructions == null) {
            throw new IllegalArgumentException("instructions cannot be null");
        }
        this.instructions = instructions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id.hashCode();
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 97 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 97 * hash + this.cookingTime;
        hash = 97 * hash + this.numPortions;
        hash = 97 * hash + (this.instructions != null ? this.instructions.hashCode() : 0);
        hash = 97 * hash + (this.ingredients != null ? this.ingredients.hashCode() : 0);
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
        if (this.cookingTime != other.cookingTime) {
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
    
    @Override
    public int compareTo(Recipe o) {
        return new Long(this.getId()).compareTo(new Long(o.getId()));
    }   
}