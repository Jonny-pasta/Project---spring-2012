package fi.muni.pv168;

import fi.muni.pv168.exceptions.ServiceFailureException;
import java.util.SortedSet;

/**
 * interface represents manager for handling recipes in database
 * @author mimo
 */
public interface RecipeManager {
    
    /** 
     * creates a recipe
     * @param recipe recipe to be created
     */
    void createRecipe(Recipe recipe) throws ServiceFailureException;
    
    /**
     * deletes a recipe
     * @param recipe recipe to be deleted
     */
    void deleteRecipe(Recipe recipe) throws ServiceFailureException;
    
    /**
     * edit a recipe
     * @param recipe recipe to be edited 
     */
    void updateRecipe(Recipe recipe) throws ServiceFailureException;
       
    /**
     * find recipe by ID
     * @param id id of the searched recipe
     * @return recipe with entered ID
     */    
    Recipe findRecipeById(Long id) throws ServiceFailureException;
    
    /**
     * find recipes, that have this substring in their names
     * @param name name of the searched recipes
     * @return set of recipes with entered substring
     */
    SortedSet<Recipe> findRecipesByName(String name) throws ServiceFailureException;
    
    /**
     * find recipes by it's type
     * @param type type of the searched recipes
     * @return set of recipes with entered type
     */
    SortedSet<Recipe> findRecipesByType(MealType type) throws ServiceFailureException;
    
    /**
     * find recipes by it's category
     * @param category category of the searched recipes
     * @return set of recipes with entered category
     */
    SortedSet<Recipe> findRecipesByCategory(MealCategory category) throws ServiceFailureException;
    
    /**
     * find recipes by it's cooking time
     * @param fromTime lower border of the searched cooking time
     * @param toTime upper border of the searched cooking time
     * @return set of recipes with cooking time between lower and upper borders
     */
    SortedSet<Recipe> findRecipesByCookingTime(int fromTime, int toTime) throws ServiceFailureException;
    SortedSet<Recipe> findRecipesUptoCookingTime(int toTime) throws ServiceFailureException;
    SortedSet<Recipe> findRecipesFromCookingTime(int fromTime) throws ServiceFailureException;
    
    /**
     * find all recipes in the recipe book
     * @return all recipes in the system
     */
    SortedSet<Recipe> findAllRecipes() throws ServiceFailureException;
}
