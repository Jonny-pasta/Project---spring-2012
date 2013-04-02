package fi.muni.pv168;

import java.util.SortedSet;


/**
 * class represents recipe book itself - manager of ingredients and recipes
 * @author Mimo
 */
public class Recipebook {
    
    /**
     * adds given ingredients into selected recipe
     * @param ingredients ingredients to insert
     * @param recipe recipe, to which should ingredients be added
     */
    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    /**
     * remove ingredients from selected recipe
     * @param ingredients ingredients to remove
     * @param recipe recipe, from which should ingredients be removed
     */
    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    /**
     * find recipes by given ingredients
     * @param ingredients searching filter
     * @return all recipes with given ingredients
     */
    public Recipe findRecipesByIngredients(SortedSet<Ingredient> ingredients){
        throw new UnsupportedOperationException("not implemented yet");
    }
}
