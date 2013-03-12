package fi.muni.pv168;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
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
        Recipe recipe = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
        
        recipe.setId(1);
        recipe.setName("chicken");
        recipe.setType(MealType.MAIN_DISH);
        recipe.setCookingTime(120);
        recipe.setNumPortions(5);
        recipe.setInstructions("cook chiken");
        recipe.setIngredients(ingredients);
        recipe.setCategory(MealCategory.MEAT);
        
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
    public void addRecipeWithWrongAttributes() {

        try {
            recipebook.createRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Recipe r1 = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
        
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        r1 = new Recipe();
        r1.setId(1);
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        SortedSet<Ingredient> set = new TreeSet<Ingredient>();
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(set);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void updateRecipe(){
        Recipe r1 = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
        
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        
        
        Ingredient goat = new Ingredient("goat", 1, "kg");
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        ingredients2.add(goat);
        ingredients2.add(potatoes);
        Recipe r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
        recipebook.createRecipe(r1);
        
        recipebook.updateRecipe(r1, r2);
        
        assertNull(recipebook.findRecipeById(1));
        assertEquals(r2,recipebook.findRecipeById(2));
    }
    
    @Test
    public void updateRecipeWithWrongAttributes() {
        Recipe r1 = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
        
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        
        
        Ingredient goat = new Ingredient("goat", 1, "kg");
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        ingredients2.add(goat);
        ingredients2.add(potatoes);
                
        recipebook.createRecipe(r1);
        
        try {
            recipebook.updateRecipe(r1,null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Recipe r2 = new Recipe();;
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.updateRecipe(r1,r2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        r2 = new Recipe();
        r2.setId(2);
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        SortedSet<Ingredient> set = new TreeSet<Ingredient>();
        r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(set);
        r2.setCategory(MealCategory.MEAT);
        try {
            recipebook.createRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void deleteRecipe() {
        Recipe r1 = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
        
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        
        
        Ingredient goat = new Ingredient("goat", 1, "kg");
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        ingredients2.add(goat);
        ingredients2.add(potatoes);
        Recipe r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(120);
        r2.setNumPortions(5);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
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
        Recipe r1 = new Recipe();
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();
        ingredients.add(chicken);
        ingredients.add(potatoes);
         
        try {
            recipebook.deleteRecipe(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setInstructions("cook chiken");
        r1.setIngredients(ingredients);
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setCategory(MealCategory.MEAT);
        try {
            recipebook.deleteRecipe(r1);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        SortedSet<Ingredient> set = new TreeSet<Ingredient>();
        r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(120);
        r1.setNumPortions(5);
        r1.setInstructions("cook chiken");
        r1.setIngredients(set);
        r1.setCategory(MealCategory.MEAT);
        try {
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
        
        SortedSet<Ingredient> startingIngredients = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredientsToAdd = new TreeSet<Ingredient>();
        
        SortedSet<Ingredient> expectedIngredients = new TreeSet<Ingredient>();
        
        startingIngredients.add(bread);
        
        ingredientsToAdd.add(sosage);
        ingredientsToAdd.add(beacon);
        
        expectedIngredients.add(bread);
        expectedIngredients.add(sosage);
        expectedIngredients.add(beacon);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("klobasa,slanina, chlieb");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("put it together");
        r1.setIngredients(startingIngredients);
        r1.setCategory(MealCategory.MEAT);
        
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
        
        SortedSet<Ingredient> startingIngredients = new TreeSet<Ingredient>();
        SortedSet<Ingredient> emptyIngredients = new TreeSet<Ingredient>();
        
        SortedSet<Ingredient> ingredientsToAdd = new TreeSet<Ingredient>();
        
        startingIngredients.add(bread);
        startingIngredients.add(sosage);
        startingIngredients.add(beacon);
        ingredientsToAdd.add(beaconFromOrava);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("Slanina s klobasou");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r1.setIngredients(startingIngredients);
        r1.setCategory(MealCategory.MEAT);
        
        Recipe r2 = new Recipe();
        r2.setId(2);
        r2.setName("Slanina");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("slaninu jedzte s chlebom");
        r2.setIngredients(startingIngredients);
        r2.setCategory(MealCategory.MEAT);
        
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
        
        SortedSet<Ingredient> startingIngredients = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredientsToRem = new TreeSet<Ingredient>();
        
        SortedSet<Ingredient> expectedIngredients = new TreeSet<Ingredient>();
        
        startingIngredients.add(butter);
        startingIngredients.add(asparagus);
        startingIngredients.add(bread);
        startingIngredients.add(grepfruit);
        
        ingredientsToRem.add(asparagus);
        
        expectedIngredients.add(bread);
        expectedIngredients.add(butter);
        expectedIngredients.add(grepfruit);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("chleba s maslom");
        r1.setType(MealType.APPETIZER);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("chlieb natrite maslom");
        r1.setIngredients(startingIngredients);
        r1.setCategory(MealCategory.MEATLESS);
        
        recipebook.createRecipe(r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), startingIngredients);
        
        recipebook.removeIngredientsFromRecipe(ingredientsToRem, r1);
        
        assertEquals(recipebook.findRecipeById(1).getIngredients(), expectedIngredients);
        
        expectedIngredients.remove(grepfruit);
    }
    
    @Test
    public void removeIngredientsFromRecipeWithWrongAttributes()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient bread = new Ingredient("chlieb", 1, "kg");
        Ingredient beaconFromOrava = new Ingredient("oravska slanina", 1, "kg");
        
        SortedSet<Ingredient> startingIngredients = new TreeSet<Ingredient>();
        SortedSet<Ingredient> emptyIngredients = new TreeSet<Ingredient>();
        
        SortedSet<Ingredient> wrongIngredients = new TreeSet<Ingredient>();
        
        startingIngredients.add(bread);
        startingIngredients.add(sosage);
        startingIngredients.add(beacon);
        
        wrongIngredients.add(beaconFromOrava);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("Slanina s klobasou");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r1.setIngredients(startingIngredients);
        r1.setCategory(MealCategory.MEAT);
                
        Recipe r2 = new Recipe();
        r2.setId(2);
        r2.setName("Slanina");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("lala");
        r2.setIngredients(startingIngredients);
        r2.setCategory(MealCategory.MEAT);
        
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
    }
    
    @Test
    public void findRecipeById(){
        
        assertNull(recipebook.findRecipeById(1));
        
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
        ingredients1.add(chicken);
        ingredients1.add(potatoes);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(5);
        r1.setInstructions("cook chicken");
        r1.setIngredients(ingredients1);
        r1.setCategory(MealCategory.MEAT);
                
        recipebook.createRecipe(r1);
        
        int i = r1.getId();
        
        Recipe result = recipebook.findRecipeById(i);
        assertEquals(r1,result);
        
        
    }
    
    @Test
    public void findAllRecipes(){
        
        assertTrue(recipebook.findAllRecipes().isEmpty());
        
        Ingredient chicken = new Ingredient("chicken", 1, "kg");
        Ingredient potatoes = new Ingredient("potatoes", 1, "kg");
        SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
        ingredients1.add(chicken);
        ingredients1.add(potatoes);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("chicken");
        r1.setType(MealType.MAIN_DISH);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("cook chicken");
        r1.setIngredients(ingredients1);
        r1.setCategory(MealCategory.MEAT);
        
        
        Ingredient goat = new Ingredient("goat", 1, "kg");
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        ingredients2.add(goat);
        ingredients2.add(potatoes);
        
        Recipe r2 = new Recipe();
        r2.setId(2);
        r2.setName("goat");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("cook goat");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        
        List<Recipe> expected = Arrays.asList(r1,r2);
        List<Recipe> actual = recipebook.findAllRecipes();
        
        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);
        
        assertEquals(expected, actual);
    }
     
    @Test
    public void findRecipeByType()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient butter = new Ingredient("butter", 0.1, "kg");
        Ingredient bread = new Ingredient("chlieb", 0.1, "kg");
        Ingredient mustard = new Ingredient("horcica", 0.1, "kg");
        
        SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients3 = new TreeSet<Ingredient>();
        
        ingredients1.add(bread);
        ingredients1.add(butter);
        
        ingredients2.add(bread);
        ingredients2.add(beacon);
        ingredients2.add(sosage);
        
        ingredients3.add(bread);
        ingredients3.add(beacon);
        ingredients3.add(mustard);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("Chleba s maslom");
        r1.setType(MealType.APPETIZER);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("natrite chlieb maslom");
        r1.setIngredients(ingredients1);
        r1.setCategory(MealCategory.MEAT);
        
        Recipe r2 = new Recipe();
        r2.setId(1);
        r2.setName("Slanina s klobasou");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
        Recipe r3 = new Recipe();
        r3.setId(1);
        r3.setName("Slanina s horcicou");
        r3.setType(MealType.MAIN_DISH);
        r3.setCookingTime(20);
        r3.setNumPortions(1);
        r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
        r3.setIngredients(ingredients3);
        r3.setCategory(MealCategory.MEAT);
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        recipebook.createRecipe(r3);
        
        List<Recipe> expected = new ArrayList<Recipe>();
        List<Recipe> result;
        
        expected.add(r2);
        expected.add(r3);
        
        result = recipebook.findRecipesByType(MealType.MAIN_DISH);
        
        assertEquals(result, expected);
    }
    
    @Test
    public void findRecipeByCategory()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient butter = new Ingredient("butter", 0.1, "kg");
        Ingredient bread = new Ingredient("chlieb", 0.1, "kg");
        Ingredient mustard = new Ingredient("horcica", 0.1, "kg");
        
        SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients3 = new TreeSet<Ingredient>();
        
        ingredients1.add(bread);
        ingredients1.add(butter);
        
        ingredients2.add(bread);
        ingredients2.add(beacon);
        ingredients2.add(sosage);
        
        ingredients3.add(bread);
        ingredients3.add(beacon);
        ingredients3.add(mustard);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("Chleba s maslom");
        r1.setType(MealType.APPETIZER);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("natrite chlieb maslom");
        r1.setIngredients(ingredients1);
        r1.setCategory(MealCategory.MEAT);
        
        Recipe r2 = new Recipe();
        r2.setId(1);
        r2.setName("Slanina s klobasou");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
        Recipe r3 = new Recipe();
        r3.setId(1);
        r3.setName("Slanina s horcicou");
        r3.setType(MealType.MAIN_DISH);
        r3.setCookingTime(20);
        r3.setNumPortions(1);
        r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
        r3.setIngredients(ingredients3);
        r3.setCategory(MealCategory.MEAT);
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        recipebook.createRecipe(r3);
        
        List<Recipe> expected = new ArrayList<Recipe>();
        List<Recipe> result;
        
        expected.add(r2);
        expected.add(r3);
        
        result = recipebook.findRecipesByCategory(MealCategory.MEAT);
        
        assertEquals(result, expected);
    }
    
    @Test
    public void findRecipeByIngredients()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient butter = new Ingredient("butter", 0.1, "kg");
        Ingredient bread = new Ingredient("chlieb", 0.1, "kg");
        Ingredient mustard = new Ingredient("horcica", 0.1, "kg");
        
        SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients3 = new TreeSet<Ingredient>();
        
        ingredients1.add(bread);
        ingredients1.add(butter);
        
        ingredients2.add(bread);
        ingredients2.add(beacon);
        ingredients2.add(sosage);
        
        ingredients3.add(bread);
        ingredients3.add(beacon);
        ingredients3.add(mustard);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("Chleba s maslom");
        r1.setType(MealType.APPETIZER);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("natrite chlieb maslom");
        r1.setIngredients(ingredients1);
        r1.setCategory(MealCategory.MEAT);
        
        Recipe r2 = new Recipe();
        r2.setId(1);
        r2.setName("Slanina s klobasou");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
        Recipe r3 = new Recipe();
        r3.setId(1);
        r3.setName("Slanina s horcicou");
        r3.setType(MealType.MAIN_DISH);
        r3.setCookingTime(20);
        r3.setNumPortions(1);
        r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
        r3.setIngredients(ingredients3);
        r3.setCategory(MealCategory.MEAT);
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        recipebook.createRecipe(r3);
        
        SortedSet<Ingredient> set = new TreeSet<Ingredient>();
        set.add(bread);
        set.add(beacon);
        List<Recipe> expected = new ArrayList<Recipe>();
        List<Recipe> result;
        
        expected.add(r2);
        expected.add(r3);
        
        result = recipebook.findRecipesByIngredients(set);
        
        assertEquals(result, expected);
    }
    
    @Test
    public void findRecipesByCookingTime()
    {
        Ingredient beacon = new Ingredient("slanina", 1, "kg");
        Ingredient sosage = new Ingredient("klobasa", 1, "kg");
        Ingredient butter = new Ingredient("butter", 0.1, "kg");
        Ingredient bread = new Ingredient("chlieb", 0.1, "kg");
        Ingredient mustard = new Ingredient("horcica", 0.1, "kg");
        
        SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
        SortedSet<Ingredient> ingredients3 = new TreeSet<Ingredient>();
        
        ingredients1.add(bread);
        ingredients1.add(butter);
        
        ingredients2.add(bread);
        ingredients2.add(beacon);
        ingredients2.add(sosage);
        
        ingredients3.add(bread);
        ingredients3.add(beacon);
        ingredients3.add(mustard);
        
        Recipe r1 = new Recipe();
        r1.setId(1);
        r1.setName("Chleba s maslom");
        r1.setType(MealType.APPETIZER);
        r1.setCookingTime(20);
        r1.setNumPortions(1);
        r1.setInstructions("natrite chlieb maslom");
        r1.setIngredients(ingredients1);
        r1.setCategory(MealCategory.MEAT);
        
        Recipe r2 = new Recipe();
        r2.setId(1);
        r2.setName("Slanina s klobasou");
        r2.setType(MealType.MAIN_DISH);
        r2.setCookingTime(20);
        r2.setNumPortions(1);
        r2.setInstructions("k slanine pridajte klobasu jedzte s chlebom");
        r2.setIngredients(ingredients2);
        r2.setCategory(MealCategory.MEAT);
        
        Recipe r3 = new Recipe();
        r3.setId(1);
        r3.setName("Slanina s horcicou");
        r3.setType(MealType.MAIN_DISH);
        r3.setCookingTime(20);
        r3.setNumPortions(1);
        r3.setInstructions("namocte slaninu do horcice, zajedzte chlebom");
        r3.setIngredients(ingredients3);
        r3.setCategory(MealCategory.MEAT);
        
        recipebook.createRecipe(r1);
        recipebook.createRecipe(r2);
        recipebook.createRecipe(r3);
        
        List<Recipe> expected = new ArrayList<Recipe>();
        List<Recipe> result;
        
        expected.add(r1);
        expected.add(r2);
        
        result = recipebook.findRecipesByCookingTime(new Time(15), new Time(20));
        
        assertEquals(result, expected);
    }
    
    
    private static Comparator<Recipe> idComparator = new Comparator<Recipe>() {

        @Override
        public int compare(Recipe o1, Recipe o2) {
            return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
        }
    };
}
