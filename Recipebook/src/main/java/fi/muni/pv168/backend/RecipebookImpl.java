package fi.muni.pv168.backend;

import fi.muni.pv168.exceptions.InvalidEntityException;
import fi.muni.pv168.exceptions.ServiceFailureException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class represents recipe book itself
 *
 * @author Mimo
 */
public class RecipebookImpl implements Recipebook {

    private static final Logger logger = Logger.getLogger(
            RecipebookImpl.class.getName());
    
    private IngredientManager ingredientManager;
    private RecipeManager recipeManager;
    
    private static final Object LOCK = new Object();

    /**
     * constructor, creates recipe book that uses given ingredient and recipe managers
     * @param ingredientManager ingredient manager to use
     * @param recipeManager recipe manager to use
     */
    public RecipebookImpl(IngredientManager ingredientManager, RecipeManager recipeManager) {
        this.ingredientManager = ingredientManager;
        this.recipeManager = recipeManager;
    }

    @Override
    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException {
        
        synchronized(LOCK){
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
                logger.log(Level.SEVERE, "check exception's message", ex);
            }
        }
    }

    @Override
    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException {
        validate(recipe);
        validate(ingredients);
        
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
            logger.log(Level.SEVERE, "check exception's message", ex);
        }
    }
    
    @Override
    public SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients) throws ServiceFailureException {
        validate(ingredients);

        SortedSet<Long> ids = new TreeSet<Long>();
        SortedSet<Recipe> result = new TreeSet<Recipe>();
        
        try {
            for (Ingredient ingredient : ingredients) {
                ids.addAll( ingredientManager.getRecipeIdsByIngredient(ingredient) );
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceFailureException();
        } 
        
        try {
            for (Long id : ids) {
                Recipe recipe = recipeManager.findRecipeById(id);
                recipe.setIngredients(ingredientManager.getIngredientsOfRecipe(recipe.getId()));
                result.add(recipe);
            }
            
        } catch (ServiceFailureException ex) {
            logger.log(Level.SEVERE, "check exception's message", ex);
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
    
    @Override
    public SortedSet<Recipe> findRecipesByIngredientName(String ingredientName) throws ServiceFailureException {
        SortedSet<Long> ids;
        SortedSet<Recipe> result = new TreeSet<Recipe>();
        
        try {
            ids = ingredientManager.getRecipeIdsByIngredientName(ingredientName);
            
            for (Long id : ids) {
                Recipe recipe = recipeManager.findRecipeById(id);
                recipe.setIngredients(ingredientManager.getIngredientsOfRecipe(id));
                result.add(recipe);
            }
        } catch (ServiceFailureException e) {
            logger.log(Level.SEVERE, "check exception's message", e);
            throw new ServiceFailureException();
        }
        return result;
    }
    
    @Override
    public SortedSet<Ingredient> getIngredientsOfRecipe(Recipe recipe) throws ServiceFailureException {
        validate(recipe);
        
        if (recipe.getId() == null) {
            throw new InvalidEntityException("recipe has null Id");
        }
        
         SortedSet<Ingredient> result= null; 
        
        try{
            
            result = ingredientManager.getIngredientsOfRecipe(recipe.getId());
            
        } catch (ServiceFailureException ex) {
            logger.log(Level.SEVERE, "check exception's message", ex);
        }
        return result;
    }

    /**
     * validates, that given recipe is a valid entity
     * @param recipe recipe to check
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
     * validates, that every ingredient in given sets of ingredients is a valid entity
     * @param ingredients given set of ingredients
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
     * checks, if ingredient is in given recipe
     *
     * @param ingredient given ingredient
     * @param recipe recipe to search in
     * @return yes, if recipe contains ingredient, no otherwise
     */
    private boolean isIngredientInRecipe(Ingredient ingredient, Recipe recipe) {
        if (recipe.getIngredients().contains(ingredient)) {
            return true;
        } else {
            return false;
        }
    }
}
