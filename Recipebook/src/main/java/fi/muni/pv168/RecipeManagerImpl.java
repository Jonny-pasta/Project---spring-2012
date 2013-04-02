package fi.muni.pv168;

import fi.muni.pv168.exceptions.InvalidEntityException;
import fi.muni.pv168.exceptions.ServiceFailureException;
import fi.muni.pv168.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Mimo
 */
public class RecipeManagerImpl implements RecipeManager {

    private static final Logger logger = Logger.getLogger(
            IngredientManagerImpl.class.getName());
    private DataSource dataSource;

    public RecipeManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createRecipe(Recipe recipe) throws ServiceFailureException {
        checkDataSource();
        validate(recipe);

        Connection con = null;
        PreparedStatement query = null;

        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);

            query = con.prepareStatement(
                    "INSERT INTO RECIPES (NAME, TYPE, CATEGORY, COOKINGTIME, NUMPORTIONS, INSTRUCTIONS) VALUES(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setString(1, recipe.getName());
            query.setInt(2, MealType.toInt(recipe.getType()));
            query.setInt(3, MealCategory.toInt(recipe.getCategory()));
            query.setInt(4, recipe.getCookingTime());
            query.setInt(5, recipe.getNumPortions());
            query.setString(6, recipe.getInstructions());

            int count = query.executeUpdate();

            DBUtils.checkUpdatesCount(count, true);

            long newId = DBUtils.getId(query.getGeneratedKeys());

            recipe.setId(newId);

            con.commit();
        } catch (SQLException ex) {
            String msg = "Error when inserting recipe into ingredient";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.doRollbackQuietly(con);
            DBUtils.closeQuietly(con, query);
        }
    }

    public void updateRecipe(Recipe recipe) throws ServiceFailureException {
        checkDataSource();
        validate(recipe);

        if (recipe.getId() == null) {
            throw new InvalidEntityException("recipe id is null");
        }

        Connection con = null;
        PreparedStatement query = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);

            query = con.prepareStatement(
                    "UPDATE RECIPES SET NAME = ?, TYPE = ?, CATEGORY = ?, COOKINGTIME = ?, NUMPORTIONS = ?, INSTRUCTIONS = ? WHERE ID = ?");

            query.setString(1, recipe.getName());
            query.setInt(2, MealType.toInt(recipe.getType()));
            query.setInt(3, MealCategory.toInt(recipe.getCategory()));
            query.setInt(4, recipe.getCookingTime());
            query.setInt(5, recipe.getNumPortions());
            query.setString(6, recipe.getInstructions());
            query.setLong(7, recipe.getId());

            int count = query.executeUpdate();

            DBUtils.checkUpdatesCount(count, false);

            con.commit();
        } catch (SQLException ex) {
            String msg = "Error when updating recipe in the DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.doRollbackQuietly(con);
            DBUtils.closeQuietly(con, query);
        }
    }

    public void deleteRecipe(Recipe recipe) throws ServiceFailureException {
        checkDataSource();
        validate(recipe);

        if (recipe.getId() == null) {
            throw new InvalidEntityException("recipe ID is null");
        }

        if (this.findRecipeById(recipe.getId()) == null) {
            throw new IllegalArgumentException("recipe is not in DB");
        }

        Connection connection = null;
        PreparedStatement querry = null;

        try {
            connection = dataSource.getConnection();

            connection.setAutoCommit(false);

            querry = connection.prepareStatement(
                    "DELETE FROM RECIPES WHERE ID = ?");

            querry.setLong(1, recipe.getId());

            int count = querry.executeUpdate();

            DBUtils.checkUpdatesCount(count, false);

            connection.commit();

        } catch (SQLException ex) {
            String msg = "Error when updating recipe in the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.doRollbackQuietly(connection);
            DBUtils.closeQuietly(connection, querry);
        }
    }

    public Recipe findRecipeById(Long id) throws ServiceFailureException {
        checkDataSource();
        
        if (id == null) {
            throw new IllegalArgumentException();
        }

        Connection con = null;
        PreparedStatement query = null;

        try {
            con = dataSource.getConnection();
            query = con.prepareStatement("SELECT * FROM RECIPES WHERE ID = ?");

            query.setLong(1, id);

            ResultSet resultDB = query.executeQuery();

            boolean b = resultDB.next();
            
            if (!b) {
                throw new IllegalArgumentException();
            }
            
            Recipe output = rowToRecipe(resultDB);
            validate(output);

            return output;
        } catch (SQLException ex) {
            String msg = "Error getting recipe for ID " + id + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.closeQuietly(con, query);
        }
    }

    public SortedSet<Recipe> findRecipesByName(String name) throws ServiceFailureException {
        checkDataSource();

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();
            query = connection.prepareStatement("SELECT * FROM RECIPES WHERE NAME = ?");

            query.setString(1, name);

            ResultSet resultsDB = query.executeQuery();

            SortedSet<Recipe> result = new TreeSet<Recipe>();
            while (resultsDB.next()) {
                Recipe output = rowToRecipe(resultsDB);
                validate(output);

                result.add(output);
            }

            return result;

        } catch (SQLException ex) {
            String msg = "Error getting recipe for name " + name + " from DB";

            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.closeQuietly(connection, query);
        }
    }

    public SortedSet<Recipe> findRecipesByType(MealType type) throws ServiceFailureException {
        checkDataSource();

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();
            query = connection.prepareStatement("SELECT * FROM RECIPES WHERE TYPE = ?");

            query.setInt(1, MealType.toInt(type));

            ResultSet resultsDB = query.executeQuery();

            SortedSet<Recipe> result = new TreeSet<Recipe>();
            while (resultsDB.next()) {
                Recipe output = rowToRecipe(resultsDB);
                validate(output);

                result.add(output);
            }

            return result;

        } catch (SQLException ex) {
            String msg = "Error getting recipe for type " + type + " from DB";

            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.closeQuietly(connection, query);
        }
    }

    public SortedSet<Recipe> findRecipesByCategory(MealCategory category) throws ServiceFailureException {
        checkDataSource();

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();
            query = connection.prepareStatement("SELECT * FROM RECIPES WHERE CATEGORY = ?");

            query.setInt(1, MealCategory.toInt(category));

            ResultSet resultsDB = query.executeQuery();

            SortedSet<Recipe> result = new TreeSet<Recipe>();
            while (resultsDB.next()) {
                Recipe output = rowToRecipe(resultsDB);
                validate(output);

                result.add(output);
            }

            return result;

        } catch (SQLException ex) {
            String msg = "Error getting recipe for category " + category + " from DB";

            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.closeQuietly(connection, query);
        }
    }

    public SortedSet<Recipe> findRecipesByCookingTime(int fromTime, int toTime) throws ServiceFailureException {
        checkDataSource();

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();
            query = connection.prepareStatement("SELECT * FROM RECIPES WHERE COOKINGTIME BETWEEN ? AND ?");

            query.setInt(1, fromTime);
            query.setInt(2, toTime);
            
            ResultSet resultsDB = query.executeQuery();

            SortedSet<Recipe> result = new TreeSet<Recipe>();
            while (resultsDB.next()) {
                Recipe output = rowToRecipe(resultsDB);
                validate(output);

                result.add(output);
            }

            return result;

        } catch (SQLException ex) {
            String msg = "Error getting recipe for cooking times " + fromTime + " and " + toTime + " from DB";

            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.closeQuietly(connection, query);
        }
    }

    public SortedSet<Recipe> findRecipesUptoCookingTime(int toTime) throws ServiceFailureException {
        return this.findRecipesByCookingTime(0, toTime);
    }

    public SortedSet<Recipe> findRecipesFromCookingTime(int fromTime) throws ServiceFailureException {
        return this.findRecipesByCookingTime(fromTime, Integer.MAX_VALUE);
    }

    public SortedSet<Recipe> findAllRecipes() throws ServiceFailureException {
        checkDataSource();

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();
            query = connection.prepareStatement("SELECT * FROM RECIPES");

            ResultSet resultsDB = query.executeQuery();

            SortedSet<Recipe> result = new TreeSet<Recipe>();
            while (resultsDB.next()) {
                Recipe output = rowToRecipe(resultsDB);
                validate(output);

                result.add(output);
            }
            return result;

        } catch (SQLException ex) {
            String msg = "Error getting recipe from DB";

            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.closeQuietly(connection, query);
        }
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

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
}
