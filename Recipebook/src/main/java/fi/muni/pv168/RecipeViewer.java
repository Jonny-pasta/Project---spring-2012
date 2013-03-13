package fi.muni.pv168;

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
    Recipe findRecipeById(long id);
    
    /**
     * find recipes by it's type
     * @param type type of the searched recipes
     * @return list of recipes with entered type
     */
    SortedSet<Recipe> findRecipesByType(MealType type);
    
    /**
     * find recipes by it's category
     * @param category category of the searched recipes
     * @return list of recipes with entered category
     */
    SortedSet<Recipe> findRecipesByCategory(MealCategory category);
    
    /**
     * find recipes by it's ingredients
     * @param ingredients list of ingredients of the searched recipes
     * @return list of recipes with entered ingredients
     */
    SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients);
    
    /**
     * find recipes by it's cooking time
     * @param fromTime lower border of the searched cooking time
     * @param toTime upper border of the searched cooking time
     * @return list of recipes with cooking time between lower and upper borders
     */
    SortedSet<Recipe> findRecipesByCookingTime(long fromTime, long toTime);
    SortedSet<Recipe> findRecipesUptoCookingTime(long toTime);
    SortedSet<Recipe> findRecipesFromCookingTime(long fromTime);
    
    /**
     * find all recipes in the recipe book
     * @return all recipes in the system
     */
    SortedSet<Recipe> findAllRecipes();
}

