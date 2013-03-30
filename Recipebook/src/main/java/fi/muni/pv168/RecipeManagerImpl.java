package fi.muni.pv168;

import java.util.SortedSet;
import javax.sql.DataSource;

/**
 *
 * @author Mimo
 */
public class RecipeManagerImpl implements RecipeManager {

    public RecipeManagerImpl(DataSource ds) {
    }
    
    public void createRecipe(Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteRecipe(Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateRecipe(Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Recipe findRecipeById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesByType(MealType type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesByCategory(MealCategory category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesByCookingTime(int fromTime, int toTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesUptoCookingTime(int toTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findRecipesFromCookingTime(int fromTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SortedSet<Recipe> findAllRecipes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }   
}
