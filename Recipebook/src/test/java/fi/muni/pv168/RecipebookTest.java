package fi.muni.pv168;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
        
        Recipe result;
        try {
            result = recipebook.findRecipeById(1);
            assertEquals(recipe, result);
            assertNotSame(recipe, result);
        } catch (Exception e){
            fail();
        }
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
        
        try{
            recipebook.deleteRecipe(r1);
            fail();
        } catch (Exception e) {
            //OK
        }
                
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
    
    @Test
    public void addIngredientsToRecipe()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient bread = new Ingredient("chlieb", 1, "kg");
        
        List<Ingredient> startingIngredients = new ArrayList<Ingredient>();
        List<Ingredient> ingredientsToAdd = new ArrayList<Ingredient>();
        
        List<Ingredient> expectedIngredients = new ArrayList<Ingredient>();
        
        startingIngredients.add(bread);
        
        ingredientsToAdd.add(sosage);
        ingredientsToAdd.add(beacon);
        
        expectedIngredients.add(bread);
        expectedIngredients.add(sosage);
        expectedIngredients.add(beacon);
        
        Recipe r1 = new Recipe(1, "Slanina s klobasou", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "k slanine pridajte klobasu jedzte s chlebom", startingIngredients);
        
        recipebook.createRecipe(r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), startingIngredients);
        
        recipebook.addIngredientsToRecipe(ingredientsToAdd, r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), expectedIngredients);
    }
    
    @Test
    public void addIngredientsToRecipeWithWrongAttributes()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient bread = new Ingredient("chlieb", 1, "kg");
        Ingredient beaconFromOrava = new Ingredient("oravska slanina", 1, "kg");
        
        List<Ingredient> startingIngredients = new ArrayList<Ingredient>();
        List<Ingredient> emptyIngredients = new ArrayList<Ingredient>();
        
        List<Ingredient> ingredientsToAdd = new ArrayList<Ingredient>();
        
        startingIngredients.add(bread);
        startingIngredients.add(sosage);
        startingIngredients.add(beacon);
        ingredientsToAdd.add(beaconFromOrava);
        
        Recipe r1 = new Recipe(1, "Slanina s klobasou", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "k slanine pridajte klobasu jedzte s chlebom", startingIngredients);
        Recipe r2 = new Recipe(1, "Slanina", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "lala", startingIngredients);
        
        recipebook.createRecipe(r1);
        
        try{
            recipebook.addIngredientsToRecipe(null, r1);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.addIngredientsToRecipe(ingredientsToAdd, null);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.addIngredientsToRecipe(emptyIngredients, r1);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.addIngredientsToRecipe(ingredientsToAdd, r2);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.addIngredientsToRecipe(startingIngredients, r1);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
    }
    
    @Test
    public void removeIngredientsFromRecipe()
    {
        Ingredient butter = new Ingredient("butter", 0.1, "kg");
        Ingredient asparagus = new Ingredient("asparagus", 0.1, "kg");
        Ingredient bread = new Ingredient("chlieb", 0.1, "kg");
        Ingredient grepfruit = new Ingredient("grepfruit", 0.1, "kg");
        
        List<Ingredient> startingIngredients = new ArrayList<Ingredient>();
        List<Ingredient> ingredientsToRem = new ArrayList<Ingredient>();
        
        List<Ingredient> expectedIngredients = new ArrayList<Ingredient>();
        
        startingIngredients.add(butter);
        startingIngredients.add(asparagus);
        startingIngredients.add(bread);
        startingIngredients.add(grepfruit);
        
        ingredientsToRem.add(asparagus);
        
        expectedIngredients.add(bread);
        expectedIngredients.add(butter);
        expectedIngredients.add(grepfruit);
        
        Recipe r1 = new Recipe(1, "Chleba s maslom", MealType.APPETIZER, MealCategory.MEATLESS, new Time(20), 5, "Natrite chlieb s maslom", startingIngredients);
        
        recipebook.createRecipe(r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), startingIngredients);
        
        recipebook.removeIngredientsFromRecipe(ingredientsToRem, r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), expectedIngredients);
        
        expectedIngredients.remove(grepfruit);
        String [] rem = {"grepfruit"};
        
        recipebook.removeIngredientsFromRecipe(rem, r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), expectedIngredients);
    }
    
    @Test
    public void removeIngredientsFromRecipeWithWrongAttributes()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient bread = new Ingredient("chlieb", 1, "kg");
        Ingredient beaconFromOrava = new Ingredient("oravska slanina", 1, "kg");
        
        List<Ingredient> startingIngredients = new ArrayList<Ingredient>();
        List<Ingredient> emptyIngredients = new ArrayList<Ingredient>();
        
        List<Ingredient> wrongIngredients = new ArrayList<Ingredient>();
        
        startingIngredients.add(bread);
        startingIngredients.add(sosage);
        startingIngredients.add(beacon);
        
        wrongIngredients.add(beaconFromOrava);
        
        Recipe r1 = new Recipe(1, "Slanina s klobasou", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "k slanine pridajte klobasu jedzte s chlebom", startingIngredients);
        Recipe r2 = new Recipe(1, "Slanina", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "lala", startingIngredients);
        
        recipebook.createRecipe(r1);
        
        try{
            recipebook.removeIngredientsFromRecipe(wrongIngredients, r1);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.removeIngredientsFromRecipe(startingIngredients, null);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.removeIngredientsFromRecipe(emptyIngredients, r1);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            recipebook.removeIngredientsFromRecipe(startingIngredients, r2);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
            String [] str= {"ahoj", "ako", "sa", "mas"};
            
            recipebook.removeIngredientsFromRecipe(str, r1);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
        
        try{
             String [] str= {"beacon"};
            
            recipebook.removeIngredientsFromRecipe(str, r2);
            fail();
        }catch(IllegalArgumentException ex){
            //OK
        }
    }
    
    @Test
    public void findRecipeByType()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient butter = new Ingredient("butter", 0.1, "kg");
        Ingredient bread = new Ingredient("chlieb", 0.1, "kg");
        Ingredient mustard = new Ingredient("horcica", 0.1, "kg");
        
        List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
        
        ingredients1.add(bread);
        ingredients1.add(butter);
        
        ingredients2.add(bread);
        ingredients2.add(beacon);
        ingredients2.add(sosage);
        
        ingredients3.add(bread);
        ingredients3.add(beacon);
        ingredients3.add(mustard);
        
        Recipe r1 = new Recipe(1, "Chleba s maslom", MealType.APPETIZER, MealCategory.MEATLESS, new Time(20), 5, "Natrite chlieb s maslom", ingredients1);
        Recipe r2 = new Recipe(1, "Slanina s klobasou", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "k slanine pridajte klobasu jedzte s chlebom", ingredients2);
        Recipe r3 = new Recipe(1, "Slanina s horcicou", MealType.MAIN_DISH, MealCategory.MEAT, new Time(20), 5, "namocte slaninu do horcice zajedajte chlebom", ingredients3);
    
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        recipebook.createRecipe(r3);
        
        List<Recipe> expected = new ArrayList<Recipe>();
        List<Recipe> result;
        
        expected.add(r2);
        expected.add(r3);
        
        result = recipebook.findRecipeByType(MealType.MAIN_DISH);
        
        assertEquals(result, expected);
    }
    
    private static Comparator<Recipe> idComparator = new Comparator<Recipe>() {

        @Override
        public int compare(Recipe o1, Recipe o2) {
            return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
        }
    };
}
