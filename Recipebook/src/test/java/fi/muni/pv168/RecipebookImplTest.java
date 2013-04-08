package fi.muni.pv168;

import fi.muni.pv168.exceptions.InvalidEntityException;
import fi.muni.pv168.exceptions.ServiceFailureException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mimo
 */
public class RecipebookImplTest {

    private RecipebookImpl manager;
    private RecipeManagerImpl recipeManager;
    private IngredientManagerImpl ingredientManager;
    private BasicDataSource ds;

    private static BasicDataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:memory:recipebook-test;create=true");
        
        return ds;
    }

    @Before
    public void setUp() throws SQLException {
        ds = prepareDataSource();
        recipeManager = new RecipeManagerImpl(ds);
        ingredientManager = new IngredientManagerImpl(ds);
        
        manager = new RecipebookImpl(ingredientManager, recipeManager);

        String createTableSQL1 = "CREATE TABLE INGREDIENTS("
                + "ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "NAME VARCHAR(255), "
                + "AMOUNT DOUBLE, "
                + "UNIT VARCHAR(255), "
                + "RECIPEID INTEGER NOT NULL "
                + ")";
        String createTableSQL2 = "CREATE TABLE RECIPES("
                + "ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "NAME VARCHAR(255), "
                + "TYPE INT, "
                + "CATEGORY INT, "
                + "COOKINGTIME INT, "
                + "NUMPORTIONS INT, "
                + "INSTRUCTIONS VARCHAR(255)"
                + ")";
        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        PreparedStatement query = con.prepareStatement(createTableSQL1);
        query.executeUpdate();
        query = con.prepareStatement(createTableSQL2);
        query.executeUpdate();
        con.commit();
    }

    @After
    public void tearDown() throws SQLException {
        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        PreparedStatement query = con.prepareStatement("DROP TABLE INGREDIENTS");
        query.executeUpdate();
        query = con.prepareStatement("DROP TABLE RECIPES");
        query.executeUpdate();
        con.commit();
        ds.close();
    }

    @Test
    public void getIngredientsFromRecipeTest() {
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> expected = new TreeSet<Ingredient>();


        Recipe recipe = new Recipe();
        recipe.setName("chicken");
        recipe.setType(MealType.MAIN_DISH);
        recipe.setCookingTime(120);
        recipe.setNumPortions(5);
        recipe.setInstructions("cook chiken");
        recipe.setCategory(MealCategory.MEAT);

        SortedSet<Ingredient> result;
        try {
            recipeManager.createRecipe(recipe);
            ingredientManager.createIngredient(chicken, recipe.getId());
            ingredientManager.createIngredient(potatoes, recipe.getId());
            expected.add(chicken);
            expected.add(potatoes);
            manager.addIngredientsToRecipe(expected, recipe);
            result = manager.getIngredientsOfRecipe(recipe);
            assertEquals(expected, result);
            assertNotSame(expected, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /**
     * Test of addIngredientsToRecipe method, of class RecipebookImpl.
     */
    @Test
    public void testAddIngredientsToRecipe() {
        try {
            Ingredient chicken = new Ingredient("chicken", 1, "kg");
            Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
            Ingredient milk = new Ingredient("milk", 1, "l");

            SortedSet<Ingredient> ing1 = new TreeSet<Ingredient>();
            SortedSet<Ingredient> ing2 = new TreeSet<Ingredient>();
            SortedSet<Ingredient> emptySet = new TreeSet<Ingredient>();

            Recipe r1 = new Recipe();

            r1.setName("chicken");
            r1.setType(MealType.MAIN_DISH);
            r1.setCookingTime(120);
            r1.setNumPortions(5);
            r1.setInstructions("cook chiken");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();

            r2.setName("potatoes with milk");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(120);
            r2.setNumPortions(5);
            r2.setInstructions("put it together");
            r2.setCategory(MealCategory.MEAT);

            recipeManager.createRecipe(r1);
            recipeManager.createRecipe(r2);

            ing1.add(chicken);
            ing1.add(potatoes);

            ing2.add(potatoes);
            ing2.add(milk);

            manager.addIngredientsToRecipe(ing1, r1);
            manager.addIngredientsToRecipe(ing2, r2);

            assertEquals(ing1, manager.getIngredientsOfRecipe(r1));
            assertEquals(ing2, manager.getIngredientsOfRecipe(r2));

            try {
                manager.addIngredientsToRecipe(emptySet, r2);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            try {
                manager.addIngredientsToRecipe(null, r2);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            Ingredient i1 = new Ingredient();
            i1.setName("mlieko");
            i1.setUnit("1");
            SortedSet<Ingredient> in1 = new TreeSet<Ingredient>();
            in1.add(i1);

            try {
                manager.addIngredientsToRecipe(in1, r2);
                fail();
            } catch (InvalidEntityException ex) {
                //OK
            }

            assertEquals(ing1, manager.getIngredientsOfRecipe(r1));
            assertEquals(ing2, manager.getIngredientsOfRecipe(r2));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /**
     * Test of removeIngredientsFromRecipe method, of class RecipebookImpl.
     */
    @Test
    public void testRemoveIngredientsFromRecipe() {
        try {
            Ingredient chicken = new Ingredient("chicken", 1.0, "kg");
            Ingredient potatoes = new Ingredient("potatoes", 1.0, "kg");
            Ingredient milk = new Ingredient("milk", 1.0, "l");

            SortedSet<Ingredient> ing1 = new TreeSet<Ingredient>();
            SortedSet<Ingredient> ing2 = new TreeSet<Ingredient>();
            SortedSet<Ingredient> emptySet = new TreeSet<Ingredient>();

            Recipe r1 = new Recipe();

            r1.setName("chicken");
            r1.setType(MealType.MAIN_DISH);
            r1.setCookingTime(120);
            r1.setNumPortions(5);
            r1.setInstructions("cook chiken");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();

            r2.setName("potatoes with milk");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(120);
            r2.setNumPortions(5);
            r2.setInstructions("put it together");
            r2.setCategory(MealCategory.MEAT);

            recipeManager.createRecipe(r1);
            recipeManager.createRecipe(r2);

            ing1.add(chicken);
            ing1.add(potatoes);

            ing2.add(potatoes);
            ing2.add(milk);

            manager.addIngredientsToRecipe(ing1, r1);
            manager.addIngredientsToRecipe(ing2, r2);

            assertEquals(ing1, manager.getIngredientsOfRecipe(r1));
            assertEquals(ing2, manager.getIngredientsOfRecipe(r2));

            SortedSet<Ingredient> remove = new TreeSet<Ingredient>();
            remove.add(milk);

            manager.removeIngredientsFromRecipe(ing1, r1);
            manager.removeIngredientsFromRecipe(remove, r2);

            SortedSet<Ingredient> expected = new TreeSet<Ingredient>();
            remove.add(potatoes);

            assertEmpty(manager.getIngredientsOfRecipe(r1));
            assertEquals(expected, manager.getIngredientsOfRecipe(r2));

            try {
                manager.removeIngredientsFromRecipe(ing2, r1);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            try {
                manager.removeIngredientsFromRecipe(emptySet, r2);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            try {
                manager.removeIngredientsFromRecipe(null, r2);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            Ingredient i1 = new Ingredient();
            i1.setName("mlieko");
            i1.setUnit("1");
            SortedSet<Ingredient> in1 = new TreeSet<Ingredient>();
            in1.add(i1);

            try {
                manager.removeIngredientsFromRecipe(in1, r2);
                fail();
            } catch (InvalidEntityException ex) {
                //OK
            }

            Ingredient i2 = new Ingredient();
            i2.setAmount(1);
            i2.setUnit("1");
            SortedSet<Ingredient> in2 = new TreeSet<Ingredient>();
            in1.add(i2);

            try {
                manager.removeIngredientsFromRecipe(in2, r2);
                fail();
            } catch (InvalidEntityException ex) {
                //OK
            }

            Ingredient i3 = new Ingredient();
            i3.setName("mlieko");
            i3.setAmount(1);
            SortedSet<Ingredient> in3 = new TreeSet<Ingredient>();
            in1.add(i3);

            try {
                manager.removeIngredientsFromRecipe(in3, r2);
                fail();
            } catch (InvalidEntityException ex) {
                //OK
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /**
     * Test of findRecipesByIngredients method, of class RecipebookImpl.
     */
    @Test
    public void testFindRecipesByIngredients() {
        try {
            Ingredient chicken = new Ingredient("chicken", 1, "kg");
            Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
            Ingredient milk = new Ingredient("milk", 1, "l");

            SortedSet<Ingredient> ing1 = new TreeSet<Ingredient>();
            SortedSet<Ingredient> ing2 = new TreeSet<Ingredient>();
            SortedSet<Ingredient> emptySet = new TreeSet<Ingredient>();

            Recipe r1 = new Recipe();

            r1.setName("chicken");
            r1.setType(MealType.MAIN_DISH);
            r1.setCookingTime(120);
            r1.setNumPortions(5);
            r1.setInstructions("cook chiken");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();

            r2.setName("potatoes with milk");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(120);
            r2.setNumPortions(5);
            r2.setInstructions("put it together");
            r2.setCategory(MealCategory.MEAT);

            recipeManager.createRecipe(r1);
            recipeManager.createRecipe(r2);

            ing1.add(chicken);
            ing1.add(potatoes);

            ing2.add(potatoes);
            ing2.add(milk);
            
            manager.addIngredientsToRecipe(ing1, r1);
            manager.addIngredientsToRecipe(ing2, r2);
            
            assertEquals(ing1, manager.getIngredientsOfRecipe(r1));
            assertEquals(ing2, manager.getIngredientsOfRecipe(r2));

            Ingredient slanina = new Ingredient("slanina", 1, "kg");
            SortedSet<Ingredient> slaninas = new TreeSet<Ingredient>();
            slaninas.add(slanina);
            SortedSet<Ingredient> in1 = new TreeSet<Ingredient>();
            in1.add(potatoes);
            SortedSet<Recipe> expected1 = new TreeSet<Recipe>();
            SortedSet<Recipe> expected2 = new TreeSet<Recipe>();
            expected1.add(r1);
            expected1.add(r2);
            expected2.add(r1);

            assertEquals(expected1, manager.findRecipesByIngredients(in1));
            assertEquals(expected2, manager.findRecipesByIngredients(ing1));
            assertEquals(new TreeSet<Recipe>(), manager.findRecipesByIngredients(slaninas));

            try {
                manager.findRecipesByIngredients(null);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            try {
                manager.findRecipesByIngredients(emptySet);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }

            Ingredient i1 = new Ingredient();
            i1.setName("mlieko");
            i1.setUnit("1");
            SortedSet<Ingredient> ingr1 = new TreeSet<Ingredient>();
            ingr1.add(i1);

            try {
                manager.findRecipesByIngredients(ingr1);
                fail();
            } catch (InvalidEntityException ex) {
                //OK
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    private void assertEmpty(SortedSet<Ingredient> ingredients) {
        SortedSet<Ingredient> empty = new TreeSet<Ingredient>();
        assertEquals(ingredients, empty);
    }
}