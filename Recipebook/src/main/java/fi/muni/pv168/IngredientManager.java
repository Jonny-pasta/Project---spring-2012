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
 * @author mulan
 */
public class IngredientManager {

    private static final Logger logger = Logger.getLogger(
            IngredientManager.class.getName());
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    public SortedSet<Ingredient> getIngredientsOfRecipe(Long recipeId) throws ServiceFailureException {
        checkDataSource();

        Connection connection = null;
        PreparedStatement querry = null;

        try {
            connection = dataSource.getConnection();
            querry = connection.prepareStatement("SELECT id, name, amount, unit FROM Ingredients WHERE recipeId = ?");

            querry.setLong(1, recipeId);

            ResultSet resultsDB = querry.executeQuery();

            SortedSet<Ingredient> result = new TreeSet<Ingredient>();
            logger.log(Level.SEVERE, "SOM PRED WHILOM");
            while (resultsDB.next()) {
                Ingredient output = rowToIngredient(resultsDB);
                validate(output);

                result.add(output);
            }

            return result;

        } catch (SQLException ex) {

            String msg = "Error getting ingredients for recipe id " + recipeId + " from DB";

            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);

        } finally {
            DBUtils.closeQuietly(connection, querry);
        }
    }

    public void createIngredient(Ingredient ingredient, long recipeId) throws ServiceFailureException {
        checkDataSource();
        validate(ingredient);

        Connection connection = null;
        PreparedStatement query = null;

        try {
            connection = dataSource.getConnection();

            connection.setAutoCommit(false);

            query = connection.prepareStatement("INSERT INTO INGREDIENTS (NAME, AMOUNT, UNIT, RECIPEID) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            query.setString(1, ingredient.getName());
            query.setDouble(2, ingredient.getAmount());
            query.setString(3, ingredient.getUnit());
            query.setLong(4, recipeId);

            int count = query.executeUpdate();

            DBUtils.checkUpdatesCount(count, true);

            long newId = DBUtils.getId(query.getGeneratedKeys());

            ingredient.setID(newId);

            connection.commit();
        } catch (SQLException ex) {
            String msg = "Error when inserting ingredent into DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.doRollbackQuietly(connection);
            DBUtils.closeQuietly(connection, query);
        }
    }

    public void updateIngredient(Ingredient ingredient) throws ServiceFailureException {
        if (ingredient == null) {
            throw new IllegalArgumentException("ingredient");
        }

        checkDataSource();
        validate(ingredient);

        if (ingredient.getID() == null) {
            throw new InvalidEntityException("ingredient id is null");
        }

        Connection connection = null;
        PreparedStatement querry = null;

        try {
            connection = dataSource.getConnection();

            connection.setAutoCommit(false);

            querry = connection.prepareStatement(
                    "UPDATE Ingredients SET name = ?, amount = ?, unit = ? WHERE id = ?");

            querry.setString(1, ingredient.getName());
            querry.setDouble(2, ingredient.getAmount());
            querry.setString(3, ingredient.getUnit());

            int count = querry.executeUpdate();

            DBUtils.checkUpdatesCount(count, false);

            connection.commit();

        } catch (SQLException ex) {
            String msg = "Error when updating ingredient in the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.doRollbackQuietly(connection);
            DBUtils.closeQuietly(connection, querry);
        }
    }

    public void deleteIngredient(Ingredient ingredient) throws ServiceFailureException {
        if (ingredient == null) {
            throw new IllegalArgumentException("ingredient");
        }

        if (ingredient.getID() == null) {
            throw new InvalidEntityException("ingredient id is null");
        }

        checkDataSource();
        validate(ingredient);

        Connection connection = null;
        PreparedStatement querry = null;

        try {
            connection = dataSource.getConnection();

            connection.setAutoCommit(false);

            querry = connection.prepareStatement(
                    "DELETE FROM Ingredients WHERE id = ?");

            querry.setLong(1, ingredient.getID());

            int count = querry.executeUpdate();

            DBUtils.checkUpdatesCount(count, false);

            connection.commit();

        } catch (SQLException ex) {
            String msg = "Error when updating ingredient in the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        } finally {
            DBUtils.doRollbackQuietly(connection);
            DBUtils.closeQuietly(connection, querry);
        }
    }

    static private Ingredient rowToIngredient(ResultSet results) throws SQLException {
        Ingredient newIngredient = new Ingredient();

        newIngredient.setID(results.getLong("id"));
        newIngredient.setName(results.getString("name"));
        newIngredient.setAmount(results.getDouble("amount"));
        newIngredient.setUnit(results.getString("unit"));

        return newIngredient;
    }

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
        if (Double.compare(ingredient.getAmount(), 0.0) == 0) {
            throw new InvalidEntityException("amount is 0");
        }
    }
    
    public void createTables() throws ServiceFailureException, SQLException {
        logger.log(Level.SEVERE, "SOM V CREATETABLES");
        Connection con = dataSource.getConnection();
        con.setAutoCommit(false);
        logger.log(Level.SEVERE, "PRESLO CONNECTIONOM");
        String createTableSQL = "CREATE TABLE INGREDIENTS("
                + "ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "NAME VARCHAR(255), "
                + "AMOUNT DOUBLE, "
                + "UNIT VARCHAR(255), "
                + "RECIPEID INTEGER NOT NULL "
                + ")";
                
        PreparedStatement query = con.prepareStatement(createTableSQL);
        query.executeUpdate();
        con.commit();
        logger.log(Level.SEVERE, "PRESLO STATEMENTOM");
    }
    
    public void dropTable() throws ServiceFailureException, SQLException {
        logger.log(Level.SEVERE, "DROPUJEM TABLE");
        Connection con = dataSource.getConnection();
        con.setAutoCommit(false);
        PreparedStatement query = con.prepareStatement("DROP TABLE INGREDIENTS");
        query.executeUpdate();
        con.commit();
    }
}
