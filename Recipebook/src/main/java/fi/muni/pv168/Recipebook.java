package fi.muni.pv168;

import java.util.SortedSet;

/**
 *
 * @author Mimo
 */
public class Recipebook implements RecipeEditor, RecipeViewer {

    public void createRecipe(Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteRecipe(Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateRecipe(Recipe recipeToBeUpdated, Recipe update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Recipe findRecipeById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SortedSet<Recipe> findRecipesByType(MealType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SortedSet<Recipe> findRecipesByCategory(MealCategory category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SortedSet<Recipe> findRecipesByCookingTime(long fromTime, long toTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SortedSet<Recipe> findAllRecipes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
