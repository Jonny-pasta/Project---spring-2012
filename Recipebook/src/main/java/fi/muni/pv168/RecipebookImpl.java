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
    private DataSource dataSource;

    public RecipebookImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException();
        }
        this.dataSource = dataSource;
    }

    // implementing IngredientManager.getIngredientsOfRecipe
    public SortedSet<Ingredient> getIngredientsOfRecipe(Recipe recipe) throws ServiceFailureException {
        checkDataSource();
        validate(recipe);

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            query = connection.prepareStatement("SELECT * FROM INGREDIENTS WHERE RECIPEID = ?");

            query.setLong(1, recipe.getId());

            ResultSet resultsDB = query.executeQuery();

            SortedSet<Ingredient> result = new TreeSet<Ingredient>();
            while (resultsDB.next()) {
                Ingredient output = rowToIngredient(resultsDB);
                validate(output);

                result.add(output);
            }
            connection.commit();
            return result;

        } catch (SQLException ex) {
            String msg = "Error getting ingredient for recipe id " + recipe.getId() + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.doRollbackQuietly(connection);
            DBUtils.closeQuietly(connection, query);
        }
    }

    public void addIngredientsToRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException {
        checkDataSource();
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
        
        Connection con = null;
        PreparedStatement query = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            int count;
            long newId;

            for (Ingredient ingredient : ingredients) {
                query = con.prepareStatement(
                        "INSERT INTO INGREDIENTS (NAME, AMOUNT, UNIT, RECIPEID) VALUES(?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                query.setString(1, ingredient.getName());
                query.setDouble(2, ingredient.getAmount());
                query.setString(3, ingredient.getUnit());
                query.setLong(4, recipe.getId());

                count = query.executeUpdate();

                DBUtils.checkUpdatesCount(count, true);

                newId = DBUtils.getId(query.getGeneratedKeys());

                ingredient.setId(newId);
                recipe.addIngredient(ingredient);
            }
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtils.doRollbackQuietly(con);
            DBUtils.closeQuietly(con, query);
        }
    }

    public void removeIngredientsFromRecipe(SortedSet<Ingredient> ingredients, Recipe recipe) throws ServiceFailureException {
        checkDataSource();
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

        Connection con = null;
        PreparedStatement query = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            int count = 0;

            for (Ingredient ingredient : ingredients) {
                query = con.prepareStatement(
                        "DELETE FROM INGREDIENTS WHERE RECIPEID = ? AND ID = ? AND AMOUNT = ? AND NAME = ? AND UNIT = ?");
                query.setLong(1, recipe.getId());
                query.setLong(2, ingredient.getId());
                query.setDouble(3, ingredient.getAmount());
                query.setString(4, ingredient.getName());
                query.setString(5, ingredient.getUnit());

                count = query.executeUpdate();
                DBUtils.checkUpdatesCount(count, false);

                recipe.removeIngredient(ingredient);
            }

            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtils.doRollbackQuietly(con);
            DBUtils.closeQuietly(con, query);
        }
    }

    public SortedSet<Recipe> findRecipesByIngredients(SortedSet<Ingredient> ingredients) throws ServiceFailureException {
        checkDataSource();
        validate(ingredients);

        Connection con = null;
        PreparedStatement query = null;
        SortedSet<Long> ids = new TreeSet<Long>();
        SortedSet<Recipe> result = new TreeSet<Recipe>();
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            ResultSet resultsDB;
            for (Ingredient ingredient : ingredients) {
                query = con.prepareStatement(
                        "SELECT RECIPEID FROM INGREDIENTS WHERE NAME = ? AND AMOUNT = ? AND UNIT = ?");
                query.setString(1, ingredient.getName());
                query.setDouble(2, ingredient.getAmount());
                query.setString(3, ingredient.getUnit());

                resultsDB = query.executeQuery();

                while (resultsDB.next()) {
                    Long idDB = resultsDB.getLong("RECIPEID");
                    ids.add(idDB);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceFailureException();
        } finally {
            DBUtils.doRollbackQuietly(con);
            DBUtils.closeQuietly(con, query);
        }
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            ResultSet resultsDB;
            for (Long id : ids) {
                query = con.prepareStatement(
                        "SELECT * FROM RECIPES WHERE ID = ?");
                query.setLong(1, id);

                resultsDB = query.executeQuery();

                while (resultsDB.next()) {
                    Recipe output = rowToRecipe(resultsDB);
                    validate(output);
                    output.setIngredients(this.getIngredientsOfRecipe(output));
                    result.add(output);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(RecipebookImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceFailureException();
        } finally {
            DBUtils.doRollbackQuietly(con);
            DBUtils.closeQuietly(con, query);
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

    public void setDataSource(DataSource ds) {
        if (ds == null) {
            throw new IllegalArgumentException();
        }
        this.dataSource = ds;
    }

    /**
     * checks if dataSource is not null
     */
    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
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
     * transforms rows from database into new ingredient
     *
     * @param results result set from database
     * @return new ingredient with attributes from database
     */
    static private Ingredient rowToIngredient(ResultSet results) {
        Ingredient newIngredient = new Ingredient();

        try {
            newIngredient.setId(results.getLong("id"));
            newIngredient.setName(results.getString("name"));
            newIngredient.setAmount(results.getDouble("amount"));
            newIngredient.setUnit(results.getString("unit"));
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newIngredient;
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

    /**
     * transfers results from database into new recipe
     *
     * @param results database output
     * @return new recipe
     */
    static private Recipe rowToRecipe(ResultSet results) {
        Recipe newRecipe = new Recipe();

        try {
            newRecipe.setId(results.getLong("id"));
            newRecipe.setName(results.getString("name"));
            newRecipe.setType(MealType.fromInt(results.getInt("type")));
            newRecipe.setCategory(MealCategory.fromInt(results.getInt("category")));
            newRecipe.setCookingTime(results.getInt("cookingTime"));
            newRecipe.setNumPortions(results.getInt("numPortions"));
            newRecipe.setInstructions(results.getString("instructions"));
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return newRecipe;
    }
}
