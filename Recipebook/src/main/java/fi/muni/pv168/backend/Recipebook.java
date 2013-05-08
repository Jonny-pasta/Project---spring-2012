package fi.muni.pv168;

import fi.muni.pv168.exceptions.ServiceFailureException;
import java.util.SortedSet;

/**
 * interface represents recipe book itself - manager of ingredients and recipes
 * @author Mimo
 */
public interface Recipebook {
    
    /**
     * adds given ingredients into selected recipe
     * @param ingredients ingredients to insert
     * @param recipe recipe, to which should ingredients be added
     */
    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException;
    
    /**
     * remove ingredients from selected recipe
     * @param ingredients ingredients to remove
     * @param recipe recipe, from which should ingredients be removed
     */
    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException;
    
    /**
     * find recipes by given ingredients
     * @param ingredients searching filter
     * @return all recipes with given ingredients
     */
    public SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients) throws ServiceFailureException;
        
    /**
     * gets ingredients of selected recipe
     * @param recipe recipe, which ingredients you want
     * @return SortedSet of ingredients
     * @throws ServiceFailureException problem with database
     */
    public SortedSet<Ingredient> getIngredientsOfRecipe(Recipe recipe) throws ServiceFailureException;
}
