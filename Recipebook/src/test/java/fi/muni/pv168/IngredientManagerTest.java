/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.muni.pv168;

import fi.muni.pv168.exceptions.InvalidEntityException;
import fi.muni.pv168.exceptions.ServiceFailureException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.derby.jdbc.ClientDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mimo
 */
public class IngredientManagerTest {
    private IngredientManager manager;
    private static final Logger logger = Logger.getLogger(
            IngredientManager.class.getName());
    @Before
    public void setUp() throws Exception{
        manager = new IngredientManager();
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:memory:ingredient;create=true");       
        manager.setDataSource(ds);
        manager.createTables();
        logger.log(Level.SEVERE, "VYTVORIL SOM TABULKU");
    }
    
    @After
    public void end() throws Exception{
        manager.dropTable();
    }
    
    @Test
    public void getIngredientsFromRecipeTest(){
        logger.log(Level.SEVERE, "SOM V PRVEJ METODE");
        /* try {
            assertNull(manager.getIngredientsOfRecipe(1l));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        try {
            logger.log(Level.SEVERE, "PRIDAVAM PRVU INGREDIENCIU");
            manager.createIngredient(chicken, 1);
            logger.log(Level.SEVERE, "PRIDAL SOM PRVU, PRIDAVAM DRUHU INGREDIENCIU");
            manager.createIngredient(potatoes, 1);
            logger.log(Level.SEVERE, "PRIDAL SOM DRUHU INGREDIENCIU");            
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        SortedSet<Ingredient> expected = new TreeSet<Ingredient>();
        expected.add(chicken);
        expected.add(potatoes);
        
        SortedSet<Ingredient> result;
        try {
            result = manager.getIngredientsOfRecipe(1l);
            assertEquals(expected, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        
        
        
    }
    
    @Test
    public void createIngredient(){
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        Ingredient milk = new Ingredient("milk", 1, "l");
        
        try {
            manager.createIngredient(chicken, 1);
            manager.createIngredient(potatoes, 2);
            manager.createIngredient(milk, 2);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SortedSet<Ingredient> expected = new TreeSet<Ingredient>();
        expected.add(milk);
        expected.add(potatoes);
        
        SortedSet<Ingredient> result;
        try {
            result = manager.getIngredientsOfRecipe(2l);
            assertEquals(expected, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }    
    }
    
    @Test
    public void createIngredientWithWrongAtributes(){
        try {
            manager.createIngredient(null,1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
        
        Ingredient chicken = new Ingredient();
        chicken.setName("chicken");
        chicken.setUnit("kg");
        try {
            manager.createIngredient(chicken,1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
        
        Ingredient potatoes = new Ingredient();
        potatoes.setAmount(1);
        potatoes.setUnit("kg");
        try {
            manager.createIngredient(potatoes,1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
        
        Ingredient milk = new Ingredient();
        chicken.setName("milk");
        chicken.setAmount(1);
        try {
            manager.createIngredient(milk,1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
    }
    
    public void updateIngredientTest(){
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient chicken1 = new Ingredient("chicken", 2, "kg");
        try {
            manager.createIngredient(chicken, 1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        try {
            manager.updateIngredient(chicken1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SortedSet<Ingredient> expected = new TreeSet<Ingredient>();
        expected.add(chicken1);
        try {
            assertEquals(expected,manager.getIngredientsOfRecipe(1l));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
    
    public void updateIngredientWithWrongAtributes(){
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        try {
            manager.createIngredient(chicken,1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        
        Ingredient chicken1 = new Ingredient();
        chicken.setName("chicken");
        chicken.setUnit("g");
        try {
            manager.updateIngredient(chicken1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
        
        Ingredient chicken2 = new Ingredient();
        chicken.setName("chicken");
        chicken.setAmount(2);
        try {
            manager.updateIngredient(chicken2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
        
        Ingredient chicken3 = new Ingredient();
        chicken.setAmount(2);
        chicken.setUnit("kg");
        try {
            manager.updateIngredient(chicken3);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
    }
}
