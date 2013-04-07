package fi.muni.pv168;

import fi.muni.pv168.exceptions.ServiceFailureException;

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
}
