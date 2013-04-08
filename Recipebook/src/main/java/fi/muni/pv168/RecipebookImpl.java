package fi.muni.pv168;

import fi.muni.pv168.exceptions.InvalidEntityException;
import fi.muni.pv168.exceptions.ServiceFailureException;
import fi.muni.pv168.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * class represents recipe book itself
 *
 * @author Mimo
 */
public class RecipebookImpl implements Recipebook {

    private static final Logger logger = Logger.getLogger(
            IngredientManagerImpl.class.getName());
    
    private IngredientManagerImpl ingredientManager;
    
    private RecipeManagerImpl recipeManager;

    public RecipebookImpl(IngredientManagerImpl ingredientManager, RecipeManagerImpl recipeManager) {
        this.ingredientManager = ingredientManager;
        this.recipeManager = recipeManager;
    }

    // implementing IngredientManager.getIngredientsOfRecipe

    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException {
        validate(recipe);
        validate(ingredients);

        if (recipe.getId() == null) {
            throw new InvalidEntityException("recipe has null Id");
        }

        SortedSet<Ingredient> toRemove = new TreeSet<Ingredient>();
        for (Ingredient ingredient : ingredients) {
            if (this.isIngredientInRecipe(ingredient, recipe)) {
                toRemove.add(ingredient);
            }
        }
        ingredients.removeAll(toRemove);
        
        try {
            for (Ingredient ingredient : ingredients) {
                
                ingredientManager.createIngredient(ingredient, recipe.getId());
                
                recipe.addIngredient(ingredient);
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException {
        validate(recipe);
        validate(ingredients);

        System.out.println(recipe);
        
        if (recipe.getId() == null) {
            throw new InvalidEntityException("recipe has null Id");
        }

        for (Ingredient ingredient : ingredients) {
            if (!(this.isIngredientInRecipe(ingredient, recipe))) {
                throw new IllegalArgumentException("given recipe does not have one or more of given ingredients so they cannot be removed");
            }
        }

        try {

            for (Ingredient ingredient : ingredients) {
                
                ingredientManager.deleteIngredient(ingredient, recipe.getId());

                recipe.removeIngredient(ingredient);
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients) throws ServiceFailureException {
        validate(ingredients);

        SortedSet<Long> ids = new TreeSet<Long>();
        SortedSet<Recipe> result = new TreeSet<Recipe>();
        
        try {
            for (Ingredient ingredient : ingredients) {
                ids.addAll( ingredientManager.getRecipeIDByIngredient(ingredient) );
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceFailureException();
        } 
        
        try {
            for (Long id : ids) {
                result.add(recipeManager.findRecipeById(id));
            }
            
            System.out.println("AAAAAAAAAAAAA: "+result);
            
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceFailureException();
        }
        
        SortedSet<Recipe> toRemove = new TreeSet<Recipe>();
        for (Recipe r : result) {
            if (!(r.getIngredients().containsAll(ingredients))){
                toRemove.add(r);
            }
        }
        result.removeAll(toRemove);
        return result;
    }
    
    public SortedSet<Ingredient> getIngredientsOfRecipe(Recipe recipe) throws ServiceFailureException {
        validate(recipe);
        
        if (recipe.getId() == null) {
            throw new InvalidEntityException("recipe has null Id");
        }
        
         SortedSet<Ingredient> result= null; 
        
        try{
            
            result = ingredientManager.getIngredientsOfRecipe(recipe.getId());
            
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceFailureException();
        }
        
        if(result.isEmpty() ) throw new ServiceFailureException("no ingredients for recipe in database");
        
        return result;
    }

    /**
     * validates, if given recipe is valid entity
     *
     * @param recipe
     */
    static private void validate(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe is null");
        }
        if (recipe.getName() == null) {
            throw new InvalidEntityException("name is null");
        }
        if (recipe.getType() == null) {
            throw new InvalidEntityException("type is null");
        }
        if (recipe.getCategory() == null) {
            throw new InvalidEntityException("category is null");
        }
        if (recipe.getCookingTime() <= 0) {
            throw new InvalidEntityException("cooking time is negative");
        }
        if (recipe.getNumPortions() <= 0) {
            throw new InvalidEntityException("number of portions is negative");
        }
        if (recipe.getInstructions() == null) {
            throw new InvalidEntityException("instructions are null");
        }
    }

    /**
     * validates, if given set of ingredients is valid entity
     *
     * @param ingredients
     */
    static private void validate(SortedSet<Ingredient> ingredients) {
        if (ingredients == null) {
            throw new IllegalArgumentException("'Ingredients' is null");
        }
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("Ingredients are empty");
        }
        for (Ingredient ingredient : ingredients) {
            if (ingredient == null) {
                throw new IllegalArgumentException("Ingredient is null");
            }
            if (ingredient.getName() == null) {
                throw new InvalidEntityException("name is null");
            }
            if (ingredient.getUnit() == null) {
                throw new InvalidEntityException("unit is null");
            }
            if ((Double.compare(ingredient.getAmount(), 0.0) == 0) || (ingredient.getAmount() < 0)) {
                throw new InvalidEntityException("amount is 0 or less");
            }
        }
    }

    /**
     * validates, if given ingredient is valid entity
     *
     * @param ingredient
     */
    static private void validate(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient is null");
        }
        if (ingredient.getName() == null) {
            throw new InvalidEntityException("name is null");
        }
        if (ingredient.getUnit() == null) {
            throw new InvalidEntityException("unit is null");
        }
        if ((Double.compare(ingredient.getAmount(), 0.0) == 0) || (ingredient.getAmount() < 0)) {
            throw new InvalidEntityException("amount is 0 or less");
        }
    }
    
    /**
     * checks, if ingredient is in given recipe
     *
     * @param ingredient
     * @param recipe
     * @return
     * @throws ServiceFailureException
     */
    private boolean isIngredientInRecipe(Ingredient ingredient, Recipe recipe) throws ServiceFailureException {
        if (recipe.getIngredients().contains(ingredient)) {
            return true;
        } else {
            return false;
        }
    }
}
