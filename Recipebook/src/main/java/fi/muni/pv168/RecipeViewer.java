package fi.muni.pv168;

import java.sql.Time;
import java.util.List;
import java.util.SortedSet;


/**
 * interface that represents viewer of the recipes
 * @author Mimo
 */
public interface RecipeViewer {
    
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
    List<Recipe> findRecipesByType(MealType type);
    
    /**
     * find recipes by it's category
     * @param category category of the searched recipes
     * @return list of recipes with entered category
     */
    List<Recipe> findRecipesByCategory(MealCategory category);
    
    /**
     * find recipes by it's ingredients
     * @param ingredients list of ingredients of the searched recipes
     * @return list of recipes with entered ingredients
     */
    List<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients);
    
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
    List<Recipe> findAllRecipes();
}

