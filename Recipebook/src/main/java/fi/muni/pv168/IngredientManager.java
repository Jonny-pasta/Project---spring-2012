package fi.muni.pv168;

import fi.muni.pv168.exceptions.ServiceFailureException;
import java.util.SortedSet;

/**
 *
 * @author Mimo
 */
public interface IngredientManager {
    
    /**
     * gets ingredients of selected recipe
     * @param recipeId recipe, which ingredients you want
     * @return SortedSet of ingredients
     * @throws ServiceFailureException problem with database
     */
    SortedSet<Ingredient> getIngredientsOfRecipe(long recipeId) throws ServiceFailureException;
    
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
}
