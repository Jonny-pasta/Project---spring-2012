/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.muni.pv168;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Mimo
 */
public class RecipebookTest {
    
    private Recipebook recipebook;

    @Before
    public void setUp() throws Exception {
        recipebook = new Recipebook();
    }
    
    @Test
    public void createRecipe(){
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
        Recipe recipe = new Recipe(1, "chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients);
        
        recipebook.createRecipe(recipe);
        
        Recipe result = recipebook.findRecipeById(1);
        assertEquals(recipe, result);
        assertNotSame(recipe, result);
    }

    @Test
    public void findAllRecipes(){
        
        assertTrue(recipebook.findAllRecipes().isEmpty());
        
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add(chicken);
        ingredients1.add(potatoes);
        Recipe r1 = new Recipe(1, "chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients1);
        
        Ingredient goat = new Ingredient("goat", 1, "kg");
        List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add(goat);
        ingredients2.add(potatoes);
        Recipe r2 = new Recipe(2, "goat", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook goat", ingredients2);
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        
        List<Recipe> expected = Arrays.asList(r1,r2);
        List<Recipe> actual = recipebook.findAllRecipes();
        
        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void addRecipeWithWrongAttributes() {

        try {
            recipebook.createRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add(chicken);
        ingredients1.add(potatoes);
        Recipe r1 = new Recipe(-1, "chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        r1 = new Recipe(1, null, MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe(1, "Chicken", null, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe(1, "Chicken", MealType.MAIN_DISH, null, new Time(15000000), 5, "cook chicken", ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe(1, "Chicken", MealType.MAIN_DISH, MealCategory.MEAT, null, 5, "cook chicken", ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe(1, "Chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), -1, "cook chicken", ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe(1, "Chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, null, ingredients1);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        List<Ingredient> list = new ArrayList<Ingredient>();
        r1 = new Recipe(1, "Chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", list);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void deleteRecipe() {

        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add(chicken);
        ingredients1.add(potatoes);
        Recipe r1 = new Recipe(1, "chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients1);
        
        Ingredient goat = new Ingredient("goat", 1, "kg");
        List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add(goat);
        ingredients2.add(potatoes);
        Recipe r2 = new Recipe(2, "goat", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook goat", ingredients2);
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        
        recipebook.deleteRecipe(r1);
        
        assertNull(recipebook.findRecipeById(r1.getId()));
        assertNotNull(recipebook.findRecipeById(r2.getId()));
                
    }
    
    @Test
    public void deleteRecipeWithWrongAttributes() {

        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add(chicken);
        ingredients1.add(potatoes);
        Recipe r1 = new Recipe(1, "chicken", MealType.MAIN_DISH, MealCategory.MEAT, new Time(15000000), 5, "cook chicken", ingredients1);
        
        try {
            recipebook.deleteRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            r1.setId(0);
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    private static Comparator<Recipe> idComparator = new Comparator<Recipe>() {

        @Override
        public int compare(Recipe o1, Recipe o2) {
            return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
        }
    };
}
