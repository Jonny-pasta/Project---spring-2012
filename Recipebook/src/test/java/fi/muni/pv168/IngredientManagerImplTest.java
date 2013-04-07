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
 * unit tests for IngredientManagerImpl
 * @author Mimo
 */
public class IngredientManagerImplTest {

    private IngredientManagerImpl manager;
    private static final Logger logger = Logger.getLogger(
            IngredientManagerImpl.class.getName());
    private BasicDataSource ds;

    @Before
    public void setUp() throws Exception {
        ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:memory:ingredient;create=true");
        manager = new IngredientManagerImpl(ds);
        String createTableSQL = "CREATE TABLE INGREDIENTS("
                + "ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "NAME VARCHAR(255), "
                + "AMOUNT DOUBLE, "
                + "UNIT VARCHAR(255), "
                + "RECIPEID INTEGER NOT NULL "
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
        PreparedStatement query = con.prepareStatement("DROP TABLE INGREDIENTS");
        query.executeUpdate();
        con.commit();
        ds.close();
    }

    @Test
    public void getBody() {
        try {
            manager.getIngredient(1l);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        
        Ingredient ingredient = new Ingredient("chicken", 1, "kg");
        try {
            manager.createIngredient(ingredient, 1l);
            Long iId = ingredient.getId();
            assertNotNull(iId);
            
            Ingredient result = manager.getIngredient(iId);
            assertEquals(ingredient, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
    
    @Test
    public void createIngredient() {
        Ingredient chicken = new Ingredient("chicken", 1, "kg");

        try {
            manager.createIngredient(chicken, 1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        Long iId = chicken.getId();
        assertNotNull(iId);

        try {
            Ingredient result = manager.getIngredient(iId);
            assertEquals(chicken, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void createIngredientWithWrongAtributes() {
        try {
            manager.createIngredient(null, 1);
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
            manager.createIngredient(chicken, 1);
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
            manager.createIngredient(potatoes, 1);
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
            manager.createIngredient(milk, 1);
            fail();
        } catch (InvalidEntityException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
    }

    public void updateIngredientTest() {
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        try {
            manager.createIngredient(chicken, 1l);
            Long id = chicken.getId();
            Ingredient result;
            
            chicken = manager.getIngredient(id);
            chicken.setName("better chicken");
            manager.updateIngredient(chicken);
            id = chicken.getId();
            result = manager.getIngredient(id);
            assertEquals(chicken, result);
            
            chicken = manager.getIngredient(id);
            chicken.setAmount(2);
            manager.updateIngredient(chicken);
            id = chicken.getId();
            result = manager.getIngredient(id);
            assertEquals(chicken, result);
            
            chicken = manager.getIngredient(id);
            chicken.setUnit("dg");
            manager.updateIngredient(chicken);
            id = chicken.getId();
            result = manager.getIngredient(id);
            assertEquals(chicken, result);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    public void updateIngredientWithWrongAtributes() {
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        try {
            manager.createIngredient(chicken, 1);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(IngredientManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
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

    @Test
    public void deleteIngredientTest() {
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient goose = new Ingredient("goose", 1, "kg");

        try {
            manager.createIngredient(goose, 1);
            manager.createIngredient(chicken, 1);
            
            try {
                manager.getIngredient(chicken.getId());
                manager.getIngredient(goose.getId());
            } catch (IllegalArgumentException ex) {
                fail();
            }
            
            manager.deleteIngredient(chicken, 1);
            
            try {
                manager.getIngredient(goose.getId());
            } catch (IllegalArgumentException ex) {
                fail();
            }
            
            try {
                manager.getIngredient(chicken.getId());
                fail();
            } catch (IllegalArgumentException ex) {
                //OK
            }
            
        } catch (ServiceFailureException ex) {
            logger.log(Level.SEVERE, "error when testing deleteIngredient", ex);
            fail();
        }
    }

    @Test
    public void deleteIngredientWithWrongAtributes() {
        try {
            manager.deleteIngredient(null, 1l);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (ServiceFailureException ex) {
            fail();
        }
    }

    private void assertNotEmpty(SortedSet<Ingredient> ingredients) {
        SortedSet<Ingredient> empty = new TreeSet<Ingredient>();
        assertNotEquals(ingredients, empty);
    }

    private void assertEmpty(SortedSet<Ingredient> ingredients) {
        SortedSet<Ingredient> empty = new TreeSet<Ingredient>();
        assertEquals(ingredients, empty);
    }
}
