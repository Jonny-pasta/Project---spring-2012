package fi.muni.pv168;

import java.util.SortedSet;

/**
 *
 * @author mimo
 */
public interface RecipeManager {
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
    void updateRecipe(Recipe recipe);
    
    /**
     * adds ingredients into existing recipe
     * @param ingredients list of ingredients to be added
     * @param recipe recipe that ingredients should be added into
     */
    void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe);
    
    /**
     * remove ingredients from existing recipe
     * @param ingredients list of ingredients to be removed
     * @param recipe recipe that ingredients should be removed from
     */
    void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe);
    
    /**
     * find recipe by ID
     * @param id id of the searched recipe
     * @return recipe with entered ID
     */    
    Recipe findRecipeById(Long id);
    
    /**
     * find recipes, that have this substring in their names
     * @param name name of the searched recipes
     * @return set of recipes with entered substring
     */
    SortedSet<Recipe> findRecipesByName(String name);
    
    /**
     * find recipes by it's type
     * @param type type of the searched recipes
     * @return set of recipes with entered type
     */
    SortedSet<Recipe> findRecipesByType(MealType type);
    
    /**
     * find recipes by it's category
     * @param category category of the searched recipes
     * @return set of recipes with entered category
     */
    SortedSet<Recipe> findRecipesByCategory(MealCategory category);
    
    /**
     * find recipes by it's ingredients
     * @param ingredients list of ingredients of the searched recipes
     * @return set of recipes with entered ingredients
     */
    SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients);
    
    /**
     * find recipes by it's cooking time
     * @param fromTime lower border of the searched cooking time
     * @param toTime upper border of the searched cooking time
     * @return set of recipes with cooking time between lower and upper borders
     */
    SortedSet<Recipe> findRecipesByCookingTime(int fromTime, int toTime);
    SortedSet<Recipe> findRecipesUptoCookingTime(int toTime);
    SortedSet<Recipe> findRecipesFromCookingTime(int fromTime);
    
    /**
     * find all recipes in the recipe book
     * @return all recipes in the system
     */
    SortedSet<Recipe> findAllRecipes();
}
