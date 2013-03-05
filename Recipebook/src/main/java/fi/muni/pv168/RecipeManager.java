package fi.muni.pv168;

import java.sql.Time;
import java.util.List;


/**
 * interface that represents managers of the recipes
 * @author Mimo
 */
public interface RecipeManager {
    
    /**
     * getters, setters 
     */
    int getId();
    void setId(int id);    
    String getName();
    void setName(String name);   
    MealType getType();
    void setType(MealType type);   
    MealCategory getCategory();
    void setCategory(MealCategory category);   
    List<Ingredient> getIngredients();
    void setIngredients(List<Ingredient> ingredients);    
    Time getCookingTime();
    void setCookingTime(Time cookingTime);   
    int getNumPortions();
    void setNumPortions(int numPortions);
    String getInstructions();
    void setInstructions(String instructions); 
    
    /**
     * creates a recipe
     * @param recipe recipe to be created
     */
    void createRecipe(Recipe recipe);
    
    /**
     * deletes a recipe
     * @param recipe recipe to be deleted
     */
    void deleteRecipe(Recipe recipe);
    
    /**
     * edit a recipe
     * @param recipe recipe to be edited 
     */
    void editRecipe(Recipe recipe);
    
    /**
     * find recipe by ID
     * @param id id of the searched recipe
     * @return recipe with entered ID
     */    
    Recipe findRecipeById(int id);
    
    /**
     * find recipes by it's type
     * @param type type of the searched recipes
     * @return list of recipes with entered type
     */
    List<Recipe> findRecipeByType(MealType type);
    
    /**
     * find recipes by it's category
     * @param category category of the searched recipes
     * @return list of recipes with entered category
     */
    List<Recipe> findRecipeByCategory(MealCategory category);
    
    /**
     * find recipes by it's ingredients
     * @param ingredients string array of ingredients of the searched recipes
     * @return list of recipes with entered ingredients
     */
    List<Recipe> findRecipesByIngredients(String[] ingredients);
    /**
     * find recipes by it's ingredients
     * @param ingredients list of ingredients of the searched recipes
     * @return list of recipes with entered ingredients
     */
    List<Recipe> findRecipesByIngredients(List<Ingredient> ingredients);
    
    /**
     * find recipes by it's cooking time
     * @param fromTime lower border of the searched cooking time
     * @param toTime upper border of the searched cooking time
     * @return list of recipes with cooking time between lower and upper borders
     */
    List<Recipe> findRecipesByCookingTime(Time fromTime, Time toTime);
    
    /**
     * find all recipes in the recipe book
     * @return all recipes in the system
     */
    List<Recipe> FindAllRecipes();
    
    /**
     * adds ingredients into existing recipe
     * @param ingredients list of ingredients to be added
     * @param recipe recipe that ingredients should be added into
     */
    void addIngredientsToRecipe(List<Ingredient> ingredients, Recipe recipe);
    
    /**
     * remove ingredients from existing recipe
     * @param ingredients list of ingredients to be removed
     * @param recipe recipe that ingredients should be removed from
     */
    void removeIngredientsFromRecipe(List<Ingredient> ingredients, Recipe recipe);
    /**
     * remove ingredients from existing recipe
     * @param ingredients string array of ingredients to be removed
     * @param recipe recipe that ingredients should be removed from
     */
    void removeIngredientsFromRecipe(String[] ingredients, Recipe recipe);
}
