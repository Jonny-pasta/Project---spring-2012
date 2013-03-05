/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
