package fi.muni.pv168;

import fi.muni.pv168.exceptions.InvalidEntityException;
import fi.muni.pv168.exceptions.ServiceFailureException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * class contains tests for RecipeManagerImpl
 * @author Mimo
 */
public class RecipeManagerImplTest {

    private RecipeManagerImpl manager;
    private BasicDataSource ds;

    @Before
    public void setUp() throws Exception {
        ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:memory:recipe;create=true");
        manager = new RecipeManagerImpl(ds);
        String createTableSQL = "CREATE TABLE RECIPES("
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
        PreparedStatement query = con.prepareStatement(createTableSQL);
        query.executeUpdate();
        con.commit();
    }

    @After
    public void tearDown() throws Exception {
        Connection con = ds.getConnection();
        con.setAutoCommit(false);
        PreparedStatement query = con.prepareStatement("DROP TABLE RECIPES");
        query.executeUpdate();
        con.commit();
        ds.close();
    }

    @Test
    public void createRecipe() {
        Recipe recipe = new Recipe();
        
        recipe.setName("chicken");
        recipe.setType(MealType.MAIN_DISH);
        recipe.setCookingTime(120);
        recipe.setNumPortions(5);
        recipe.setInstructions("cook chiken");
        recipe.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(recipe);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        Long recipeId = recipe.getId();

        assertNotNull(recipeId);

        Recipe result;
        try {
            result = manager.findRecipeById(recipeId);
            assertEquals(recipe, result);
            assertNotSame(recipe, result);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addRecipeWithWrongAttributes() {

        try {
            manager.createRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        Recipe r1 = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);

        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
    //    r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
    //    r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        try {
            manager.createRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
   //     r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setInstructions("cook chiken");
  //      r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
  //      r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void updateRecipe() {
        Recipe r1 = new Recipe();

        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);

        Recipe r2 = new Recipe();
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        Long recipeId = r1.getId();

        r2.setId(recipeId);
        try {
            manager.updateRecipe(r2);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        try {
            assertEquals(r2, manager.findRecipeById(recipeId));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void updateRecipeWithWrongAttributes() {
        Recipe r1 = new Recipe();

        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);

        try {
            manager.createRecipe(r1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        Long recipeId = r1.getId();

        try {
            manager.updateRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        Recipe r2 = new Recipe();

        r2.setId(recipeId);
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.updateRecipe(r2);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r2 = new Recipe();
        r2.setId(recipeId);
        r2.setName("goat");
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.updateRecipe(r2);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r2 = new Recipe();
        r2.setId(recipeId);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        try {
            manager.updateRecipe(r2);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r2 = new Recipe();
        r2.setId(recipeId);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.updateRecipe(r2);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r2 = new Recipe();
        r2.setId(recipeId);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.updateRecipe(r2);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r2 = new Recipe();
        r2.setId(recipeId);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.updateRecipe(r2);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void deleteRecipe() {
        Recipe r1 = new Recipe();
        
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);

        Recipe r2 = new Recipe();
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            manager.createRecipe(r2);

            assertNotNull(manager.findRecipeById(r1.getId()));

            manager.deleteRecipe(r1);

            try{
                manager.findRecipeById(r1.getId());
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }
            assertNotNull(manager.findRecipeById(r2.getId()));

            try {
                manager.deleteRecipe(r1);
                fail();
            } catch (IllegalArgumentException e) {
                //OK
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }


    }

    @Test
    public void deleteRecipeWithWrongAttributes() {
        Recipe r1 = new Recipe();

        try {
            manager.deleteRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.deleteRecipe(r1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void findRecipeById() {

        Recipe r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(5);
        r1.setInstructions("cook chicken");
        r1.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);

            Long i = r1.getId();

            Recipe result = manager.findRecipeById(i);
            assertEquals(r1, result);

            try {
                manager.findRecipeById(null);
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void findRecipeByName() {

        Recipe r1 = new Recipe();
        r1.setName("Chleba s maslom");
        r1.setType(MealType.APPETIZER);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("natrite chlieb maslom");
        r1.setCategory(MealCategory.MEAT);

        Recipe r2 = new Recipe();
        r2.setName("Slanina s chlebom");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r2.setCategory(MealCategory.MEAT);

        Recipe r3 = new Recipe();
        r3.setName("slanina s horcicou");
        r3.setType(MealType.MAIN_DISH);
        r3.setCookingTime(20);
        r3.setNumPortions(1);
        r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
        r3.setCategory(MealCategory.MEAT);
        
        Recipe r4 = new Recipe();
        r4.setName("superslanina s horcicou");
        r4.setType(MealType.MAIN_DISH);
        r4.setCategory(MealCategory.MEAT);
        r4.setCookingTime(20);
        r4.setNumPortions(1);
        r4.setInstructions("namocte superslaninu do horcice, zajedzte chlebom");
        
        try {
            manager.createRecipe(r1);
            manager.createRecipe(r2);
            manager.createRecipe(r3);
            manager.createRecipe(r4);
            
            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            SortedSet<Recipe> result;

            expected.add(r2);
            expected.add(r3);
            expected.add(r4);

            result = manager.findRecipesByName("slanina");

            assertEquals(expected, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

    }

    @Test
    public void findAllRecipes() {
        try {
            assertTrue(manager.findAllRecipes().isEmpty());
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }


        Recipe r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("cook chicken");
        r1.setCategory(MealCategory.MEAT);

        Recipe r2 = new Recipe();
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("cook goat");
        r2.setCategory(MealCategory.MEAT);
        try {
            manager.createRecipe(r1);
            manager.createRecipe(r2);

            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            expected.add(r1);
            expected.add(r2);
            SortedSet<Recipe> actual = manager.findAllRecipes();

            assertEquals(expected, actual);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

    }

    @Test
    public void findRecipeByType() {
        try {
            Recipe r1 = new Recipe();
            r1.setName("Chleba s maslom");
            r1.setType(MealType.APPETIZER);
            r1.setCookingTime(20);
            r1.setNumPortions(1);
            r1.setInstructions("natrite chlieb maslom");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();
            r2.setName("slanina");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(20);
            r2.setNumPortions(1);
            r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
            r2.setCategory(MealCategory.MEAT);

            Recipe r3 = new Recipe();
            r3.setName("slanina");
            r3.setType(MealType.MAIN_DISH);
            r3.setCookingTime(20);
            r3.setNumPortions(1);
            r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
            r3.setCategory(MealCategory.MEAT);

            manager.createRecipe(r1);
            manager.createRecipe(r2);
            manager.createRecipe(r3);

            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            SortedSet<Recipe> result;

            expected.add(r2);
            expected.add(r3);

            result = manager.findRecipesByType(MealType.MAIN_DISH);

            assertEquals(result, expected);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void findRecipeByCategory() {
        try {
            Recipe r1 = new Recipe();
            r1.setName("Chleba s maslom");
            r1.setType(MealType.APPETIZER);
            r1.setCookingTime(20);
            r1.setNumPortions(1);
            r1.setInstructions("natrite chlieb maslom");
            r1.setCategory(MealCategory.MEATLESS);

            Recipe r2 = new Recipe();
            r2.setName("Slanina s klobasou");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(20);
            r2.setNumPortions(1);
            r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
            r2.setCategory(MealCategory.MEAT);

            Recipe r3 = new Recipe();
            r3.setName("Slanina s horcicou");
            r3.setType(MealType.MAIN_DISH);
            r3.setCookingTime(20);
            r3.setNumPortions(1);
            r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
            r3.setCategory(MealCategory.MEAT);

            manager.createRecipe(r1);
            manager.createRecipe(r2);
            manager.createRecipe(r3);

            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            SortedSet<Recipe> result;

            expected.add(r2);
            expected.add(r3);

            result = manager.findRecipesByCategory(MealCategory.MEAT);

            assertEquals(result, expected);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void findRecipesByCookingTime() {
        try {
            Recipe r1 = new Recipe();
            r1.setName("Chleba s maslom");
            r1.setType(MealType.APPETIZER);
            r1.setCookingTime(15);
            r1.setNumPortions(1);
            r1.setInstructions("natrite chlieb maslom");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();
            r2.setName("Slanina s klobasou");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(20);
            r2.setNumPortions(1);
            r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
            r2.setCategory(MealCategory.MEAT);

            Recipe r3 = new Recipe();
            r3.setName("Slanina s horcicou");
            r3.setType(MealType.MAIN_DISH);
            r3.setCookingTime(25);
            r3.setNumPortions(1);
            r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
            r3.setCategory(MealCategory.MEAT);

            manager.createRecipe(r1);
            manager.createRecipe(r2);
            manager.createRecipe(r3);

            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            SortedSet<Recipe> result;

            expected.add(r1);
            expected.add(r2);

            result = manager.findRecipesByCookingTime(15, 20);

            assertEquals(result, expected);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void findRecipesFromCookingTime() {
        try {
            Recipe r1 = new Recipe();
            r1.setName("Chleba s maslom");
            r1.setType(MealType.APPETIZER);
            r1.setCookingTime(15);
            r1.setNumPortions(1);
            r1.setInstructions("natrite chlieb maslom");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();
            r2.setName("Slanina s klobasou");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(20);
            r2.setNumPortions(1);
            r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
            r2.setCategory(MealCategory.MEAT);

            Recipe r3 = new Recipe();
            r3.setName("Slanina s horcicou");
            r3.setType(MealType.MAIN_DISH);
            r3.setCookingTime(25);
            r3.setNumPortions(1);
            r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
            r3.setCategory(MealCategory.MEAT);

            manager.createRecipe(r1);
            manager.createRecipe(r2);
            manager.createRecipe(r3);

            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            SortedSet<Recipe> result;

            expected.add(r3);
            expected.add(r2);

            result = manager.findRecipesFromCookingTime(20);

            assertEquals(result, expected);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void findRecipesUptoCookingTime() {
        try {
            Recipe r1 = new Recipe();
            r1.setName("Chleba s maslom");
            r1.setType(MealType.APPETIZER);
            r1.setCookingTime(15);
            r1.setNumPortions(1);
            r1.setInstructions("natrite chlieb maslom");
            r1.setCategory(MealCategory.MEAT);

            Recipe r2 = new Recipe();
            r2.setName("Slanina s klobasou");
            r2.setType(MealType.MAIN_DISH);
            r2.setCookingTime(20);
            r2.setNumPortions(1);
            r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
            r2.setCategory(MealCategory.MEAT);

            Recipe r3 = new Recipe();
            r3.setName("Slanina s horcicou");
            r3.setType(MealType.MAIN_DISH);
            r3.setCookingTime(25);
            r3.setNumPortions(1);
            r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
            r3.setCategory(MealCategory.MEAT);

            manager.createRecipe(r1);
            manager.createRecipe(r2);
            manager.createRecipe(r3);

            SortedSet<Recipe> expected = new TreeSet<Recipe>();
            SortedSet<Recipe> result;

            expected.add(r1);
            expected.add(r2);

            result = manager.findRecipesUptoCookingTime(20);

            assertEquals(result, expected);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipeManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
}