package fi.muni.pv168.backend;

import fi.muni.pv168.exceptions.ServiceFailureException;
import java.util.SortedSet;

/**
 * interface for handling ingredients in database
 * @author Mimo
 */
public interface IngredientManager {
    
    /**
     * create ingredient, adds it to the recipe
     * @param ingredient ingredient you want to create
     * @param recipeId recipe, you want to add ingredient into
     * @throws ServiceFailureException problem with database
     */
    void createIngredient(Ingredient ingredient, long recipeId) throws ServiceFailureException;
    
    /**
     * update ingredient with new one
     * @param ingredient ingredient you want to update
     * @throws ServiceFailureException problem with database
     */
    void updateIngredient(Ingredient ingredient) throws ServiceFailureException;
    
    /**
     * delete ingredient from recipe
     * @param ingredient ingredient to delete
     * @param recipeId recipe, from which you want to delete ingredient
     * @throws ServiceFailureException problem with database
     */
    void deleteIngredient(Ingredient ingredient, long recipeId) throws ServiceFailureException;
    
    /**
     * retrieves ingredient from database
     * @param id of wanted ingredient
     * @return ingredient with given ID
     * @throws ServiceFailureException if something went wrong
     */
    Ingredient getIngredient(Long id) throws ServiceFailureException;
    
     /**
     * returns sorted set of recipe ids associated ingredients similar to the one given as parameter
     * @param ingredient ingredient is used as model, to find similar records
     * @return sorted set of ids of recipes associated with ingredients similar to ingredient
     * @throws ServiceFailureException problem with database
     */
    SortedSet<Long> getRecipeIdsByIngredient(Ingredient ingredient) throws ServiceFailureException;
    
    /**
     * returns sorted set of recipe ids that have ingredient with given name
     * @param ingredientName given name
     * @return sorted set of results
     * @throws ServiceFailureException DB failure 
     */
    SortedSet<Long> getRecipeIdsByIngredientName(String ingredientName) throws ServiceFailureException;
    
     /**
     * returns ingredients containing recipe IDs given as parameter
     * @param recipeId id of recipe from witch ingredients will be returned
     * @return sorted set ingredients containing given recipe id  
     * @throws ServiceFailureException problem with database
     */
    SortedSet<Ingredient> getIngredientsOfRecipe(long recipeid) throws ServiceFailureException;
    
    /**
     * returns all ingredients in the database
     * @return all ingredients in the database
     * @throws ServiceFailureException problem with database 
     */
    SortedSet<Ingredient> getAllIngredients() throws ServiceFailureException;
    
}
