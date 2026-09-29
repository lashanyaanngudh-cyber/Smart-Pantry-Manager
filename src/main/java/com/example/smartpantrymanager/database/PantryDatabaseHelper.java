package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PantryDatabaseHelper extends SQLiteOpenHelper {

    // Database details
    private static final String database_name = "smart_pantry_management.db";
    private static final int database_version = 1;


    // Pantry Items table
    public static final String pantry_table = "pantry_items";

    public static final String pantry_id = "pItem_id";
    public static final String pantry_name = "pItem_name";
    public static final String pantry_quantity = "pItem_quantity";
    public static final String pantry_unit = "pItem_unit";
    public static final String pantry_expiry_date = "pItem_expiry_date";


    // Recipes table
    public static final String recipe_table = "recipes";

    public static final String recipe_id = "r_id";
    public static final String recipe_name = "r_name";
    public static final String recipe_instructions = "r_instructions";


    // Recipe Ingredients table
    public static final String recipe_ingredient_table = "recipe_ingredients";

    public static final String recipe_ingredient_id = "rIng_id";
    public static final String recipe_ingredient_recipe_id = "r_id";
    public static final String recipe_ingredient_name = "rIng_name";
    public static final String recipe_ingredient_quantity = "rIng_quantity";
    public static final String recipe_ingredient_unit = "rIng_unit";


    // Constructor
    public PantryDatabaseHelper(Context context) {
        super(context, database_name, null, database_version);
    }


    // Creates the database tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Pantry Items table
        String createPantryTable =
                "CREATE TABLE " + pantry_table + " (" +
                        pantry_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        pantry_name + " TEXT NOT NULL, " +
                        pantry_quantity + " REAL NOT NULL, " +
                        pantry_unit + " TEXT NOT NULL, " +
                        pantry_expiry_date + " TEXT" +
                        ")";

        db.execSQL(createPantryTable);


        // Create Recipes table
        String createRecipeTable =
                "CREATE TABLE " + recipe_table + " (" +
                        recipe_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        recipe_name + " TEXT NOT NULL, " +
                        recipe_instructions + " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeTable);


        // Create Recipe Ingredients table
        String createRecipeIngredientTable =
                "CREATE TABLE " + recipe_ingredient_table + " (" +
                        recipe_ingredient_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        recipe_ingredient_recipe_id + " INTEGER NOT NULL, " +
                        recipe_ingredient_name + " TEXT NOT NULL, " +
                        recipe_ingredient_quantity + " REAL NOT NULL, " +
                        recipe_ingredient_unit + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + recipe_ingredient_recipe_id + ") REFERENCES " +
                        recipe_table + "(" + recipe_id + ")" +
                        ")";

        db.execSQL(createRecipeIngredientTable);


        // Add the starting recipes
        seedRecipes(db);
    }


    // Adds one recipe
    private long addRecipe(SQLiteDatabase db, String r_name,
                           String r_instructions) {

        ContentValues values = new ContentValues();

        values.put(recipe_name, r_name);
        values.put(recipe_instructions, r_instructions);

        return db.insert(recipe_table, null, values);
    }


    // Adds one ingredient to a recipe
    private void addRecipeIngredient(SQLiteDatabase db, long r_id,
                                     String rIng_name,
                                     double rIng_quantity,
                                     String rIng_unit) {

        ContentValues values = new ContentValues();

        values.put(recipe_ingredient_recipe_id, r_id);
        values.put(recipe_ingredient_name, rIng_name);
        values.put(recipe_ingredient_quantity, rIng_quantity);
        values.put(recipe_ingredient_unit, rIng_unit);

        db.insert(recipe_ingredient_table, null, values);
    }


    // Adds the starting recipes
    private void seedRecipes(SQLiteDatabase db) {

        long r_id;



        // Recipe 1: Mac and Cheese

        r_id = addRecipe(db,
                "Mac and Cheese",
                "1. Boil and drain the macaroni.\n" +
                        "2. Melt butter in a pot and stir in flour for 2 minutes.\n" +
                        "3. Add milk and cream and stir until thick.\n" +
                        "4. Add Aromat, pepper and nutmeg.\n" +
                        "5. Remove from the heat and stir in 2 cups of cheese.\n" +
                        "6. Mix in the macaroni and top with the remaining cheese.\n" +
                        "7. Cover and allow the cheese to melt for 5 minutes.");

        addRecipeIngredient(db, r_id, "milk", 250, "ml");
        addRecipeIngredient(db, r_id, "cream", 250, "ml");
        addRecipeIngredient(db, r_id, "macaroni", 250, "g");
        addRecipeIngredient(db, r_id, "butter", 4, "tbsp");
        addRecipeIngredient(db, r_id, "flour", 2, "tbsp");
        addRecipeIngredient(db, r_id, "aromat", 1, "tsp");
        addRecipeIngredient(db, r_id, "cheddar cheese", 3, "cup");



        // Recipe 2: Baked Spicy Mushroom and Spinach Rigatoni

        r_id = addRecipe(db,
                "Baked Spicy Mushroom and Spinach Rigatoni",
                "1. Melt butter in a pan and cook the mushrooms, chilli, garlic and thyme.\n" +
                        "2. Add masala and turmeric and cook well.\n" +
                        "3. Remove the mushrooms and set aside.\n" +
                        "4. Cook the onion and spinach until soft.\n" +
                        "5. Boil and drain the rigatoni.\n" +
                        "6. Add butter and flour to the pan and stir for 1 minute.\n" +
                        "7. Slowly add milk and stir until thick.\n" +
                        "8. Add cream, vegetable stock and Parmesan cheese.\n" +
                        "9. Mix in the rigatoni and mushrooms.\n" +
                        "10. Top with mozzarella cheese.\n" +
                        "11. Bake at 180 degrees Celsius for 20 to 25 minutes.");

        addRecipeIngredient(db, r_id, "rigatoni", 250, "g");
        addRecipeIngredient(db, r_id, "milk", 250, "ml");
        addRecipeIngredient(db, r_id, "cream", 500, "ml");
        addRecipeIngredient(db, r_id, "flour", 1, "tbsp");
        addRecipeIngredient(db, r_id, "spinach", 2, "cup");
        addRecipeIngredient(db, r_id, "garlic", 3, "clove");
        addRecipeIngredient(db, r_id, "button mushrooms", 250, "g");
        addRecipeIngredient(db, r_id, "king oyster mushrooms", 150, "g");
        addRecipeIngredient(db, r_id, "butter", 2, "tbsp");
        addRecipeIngredient(db, r_id, "masala", 1, "tsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "onion", 0.5, "piece");
        addRecipeIngredient(db, r_id, "nutmeg", 0.25, "tsp");
        addRecipeIngredient(db, r_id, "parmesan cheese", 100, "g");
        addRecipeIngredient(db, r_id, "mozzarella cheese", 100, "g");
        addRecipeIngredient(db, r_id, "green chilli", 1, "piece");
        addRecipeIngredient(db, r_id, "thyme", 1, "tsp");
        addRecipeIngredient(db, r_id, "vegetable stock cube", 1, "piece");



        // Recipe 3: Butter Bean and Potato Curry

        r_id = addRecipe(db,
                "Butter Bean and Potato Curry",
                "1. Heat oil and add cumin seeds, mustard seeds, cinnamon, cardamom, star anise, bay leaf and cloves.\n" +
                        "2. Add onion, curry leaves, coriander and chillies and cook until soft.\n" +
                        "3. Add garlic and cook for 30 seconds.\n" +
                        "4. Add turmeric and masala.\n" +
                        "5. Add tomato and a little water and cook for 2 minutes.\n" +
                        "6. Add potatoes and butter beans and cook until the potatoes are soft.\n" +
                        "7. Stir in melted butter and garnish with coriander.");

        addRecipeIngredient(db, r_id, "cooking oil", 3, "tbsp");
        addRecipeIngredient(db, r_id, "butter beans", 2, "cup");
        addRecipeIngredient(db, r_id, "potato", 2, "piece");
        addRecipeIngredient(db, r_id, "onion", 0.5, "piece");
        addRecipeIngredient(db, r_id, "tomato", 1, "piece");
        addRecipeIngredient(db, r_id, "cumin seeds", 0.25, "tsp");
        addRecipeIngredient(db, r_id, "mustard seeds", 0.25, "tsp");
        addRecipeIngredient(db, r_id, "cinnamon stick", 2, "piece");
        addRecipeIngredient(db, r_id, "cardamom", 2, "piece");
        addRecipeIngredient(db, r_id, "star anise", 1, "piece");
        addRecipeIngredient(db, r_id, "bay leaf", 1, "piece");
        addRecipeIngredient(db, r_id, "cloves", 4, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 2, "piece");
        addRecipeIngredient(db, r_id, "masala", 3, "tsp");
        addRecipeIngredient(db, r_id, "turmeric", 1, "tsp");
        addRecipeIngredient(db, r_id, "garlic", 4, "clove");
        addRecipeIngredient(db, r_id, "butter", 1, "tbsp");



        // Recipe 4: Durban Style Mutton Curry

        r_id = addRecipe(db,
                "Durban Style Mutton Curry",
                "1. Heat oil in a pot.\n" +
                        "2. Add onion, curry leaves and whole spices and cook until the onion is soft.\n" +
                        "3. Add garlic and ginger paste and garlic.\n" +
                        "4. Add the masala and spices and cook for 1 minute.\n" +
                        "5. Add the mutton and coat it in the spices.\n" +
                        "6. Add tomatoes and cook them down.\n" +
                        "7. Add water and simmer until the mutton is tender.\n" +
                        "8. Add potatoes and cook until soft.\n" +
                        "9. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "mutton", 1, "kg");
        addRecipeIngredient(db, r_id, "potato", 3, "piece");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "cinnamon stick", 2, "piece");
        addRecipeIngredient(db, r_id, "star anise", 1, "piece");
        addRecipeIngredient(db, r_id, "cardamom", 4, "piece");
        addRecipeIngredient(db, r_id, "bay leaf", 2, "piece");
        addRecipeIngredient(db, r_id, "black cardamom", 2, "piece");
        addRecipeIngredient(db, r_id, "curry leaves", 2, "sprig");
        addRecipeIngredient(db, r_id, "masala", 4, "tbsp");
        addRecipeIngredient(db, r_id, "fennel seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "turmeric", 1, "tsp");
        addRecipeIngredient(db, r_id, "chilli powder", 1, "tsp");
        addRecipeIngredient(db, r_id, "garam masala", 0.75, "tbsp");
        addRecipeIngredient(db, r_id, "coriander cumin powder", 1, "tsp");
        addRecipeIngredient(db, r_id, "tomato", 2, "piece");
        addRecipeIngredient(db, r_id, "garlic", 3, "clove");
        addRecipeIngredient(db, r_id, "cooking oil", 0.25, "cup");



        // Recipe 5: Durban Style Tomato Chutney

        r_id = addRecipe(db,
                "Durban Style Tomato Chutney",
                "1. Heat oil in a pan.\n" +
                        "2. Cook onion, curry leaves and chillies until the onion is soft.\n" +
                        "3. Add garlic and cook for 1 minute.\n" +
                        "4. Add turmeric and masala and cook for 1 to 2 minutes.\n" +
                        "5. Add tomatoes and simmer for 15 to 20 minutes until thick.\n" +
                        "6. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "tomato", 2, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 5, "piece");
        addRecipeIngredient(db, r_id, "garlic", 3, "clove");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "cooking oil", 3, "tbsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.75, "tsp");
        addRecipeIngredient(db, r_id, "masala", 1.5, "tsp");



        // Recipe 6: Durban Style Chicken Curry

        r_id = addRecipe(db,
                "Durban Style Chicken Curry",
                "1. Heat oil, cumin seeds and whole spices in a pot.\n" +
                        "2. Add onion and curry leaves and cook until soft.\n" +
                        "3. Add ginger and garlic paste and cook for 30 seconds.\n" +
                        "4. Add masala and turmeric.\n" +
                        "5. Add the chicken and coat it in the spices.\n" +
                        "6. Add tomatoes and simmer for 5 minutes.\n" +
                        "7. Add potatoes and water.\n" +
                        "8. Cook until the chicken and potatoes are tender.\n" +
                        "9. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "chicken", 1, "whole");
        addRecipeIngredient(db, r_id, "cooking oil", 3, "tbsp");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "tomato", 2, "piece");
        addRecipeIngredient(db, r_id, "masala", 1, "tbsp");
        addRecipeIngredient(db, r_id, "water", 1, "cup");
        addRecipeIngredient(db, r_id, "potato", 3, "piece");



        // Recipe 7: Lamb Stew

        r_id = addRecipe(db,
                "Lamb Stew",
                "1. Brown the lamb with herbs, pepper and salt.\n" +
                        "2. Add ginger, garlic, onion and thyme and cook until the onion is soft.\n" +
                        "3. Add potatoes, carrots, green beans and cabbage.\n" +
                        "4. Add stock and water and simmer until cooked.\n" +
                        "5. Add cornstarch to thicken the stew.\n" +
                        "6. Cook for another 10 minutes and garnish with parsley.");

        addRecipeIngredient(db, r_id, "lamb", 1, "kg");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "carrot", 1, "piece");
        addRecipeIngredient(db, r_id, "potato", 1, "piece");
        addRecipeIngredient(db, r_id, "green beans", 1, "cup");
        addRecipeIngredient(db, r_id, "thyme", 5, "sprig");
        addRecipeIngredient(db, r_id, "salt", 1, "tsp");
        addRecipeIngredient(db, r_id, "black pepper", 1, "tsp");
        addRecipeIngredient(db, r_id, "cabbage", 1, "cup");
        addRecipeIngredient(db, r_id, "ginger and garlic", 1, "tsp");
        addRecipeIngredient(db, r_id, "dried herbs", 2, "tsp");
        addRecipeIngredient(db, r_id, "stock cube", 1, "piece");
        addRecipeIngredient(db, r_id, "water", 5, "cup");
        addRecipeIngredient(db, r_id, "cornstarch", 1, "tsp");



        // Recipe 8: Mushroom Curry

        r_id = addRecipe(db,
                "Mushroom Curry",
                "1. Cook chilli, bay leaf, cumin seeds, curry leaves, onion and garlic.\n" +
                        "2. Add turmeric, coriander cumin powder and masala.\n" +
                        "3. Add tomato and cook until soft.\n" +
                        "4. Add mushrooms and mix well.\n" +
                        "5. Cover and cook until the mushrooms are tender.\n" +
                        "6. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "mushrooms", 1, "punnet");
        addRecipeIngredient(db, r_id, "onion", 0.5, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 1, "piece");
        addRecipeIngredient(db, r_id, "bay leaf", 1, "piece");
        addRecipeIngredient(db, r_id, "garlic", 1, "tsp");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "cumin seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "coriander cumin powder", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "masala", 1, "tbsp");
        addRecipeIngredient(db, r_id, "tomato", 1, "piece");



        // Recipe 9: Green Bean Curry

        r_id = addRecipe(db,
                "Green Bean Curry",
                "1. Cook onion, curry leaves and cumin seeds until the onion is soft.\n" +
                        "2. Add masala and turmeric.\n" +
                        "3. Add tomatoes and cook until soft.\n" +
                        "4. Add green beans and cook for 10 minutes.\n" +
                        "5. Add salt and water.\n" +
                        "6. Simmer for about 20 minutes until the beans are tender.");

        addRecipeIngredient(db, r_id, "onion", 0.5, "piece");
        addRecipeIngredient(db, r_id, "green beans", 300, "g");
        addRecipeIngredient(db, r_id, "tomato", 2, "piece");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "cumin seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "masala", 2, "tbsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "salt", 1, "tsp");
        addRecipeIngredient(db, r_id, "water", 0.5, "cup");


        // Recipe 10: Spicy Creamy Prawns

        r_id = addRecipe(db,
                "Spicy Creamy Prawns",
                "1. Season the prawns with salt, pepper, masala, garlic, chilli oil and lemon juice.\n" +
                        "2. Add parsley and allow the prawns to marinate.\n" +
                        "3. Fry the prawns for 2 to 3 minutes on each side.\n" +
                        "4. Add sliced garlic while cooking the second batch.\n" +
                        "5. Cook tomato paste in the same pan for 5 minutes.\n" +
                        "6. Add chilli sauce, cream and water and simmer.\n" +
                        "7. Return the prawns to the sauce and simmer for another 5 minutes.");

        addRecipeIngredient(db, r_id, "prawns", 800, "g");
        addRecipeIngredient(db, r_id, "salt", 1, "tsp");
        addRecipeIngredient(db, r_id, "black pepper", 1, "tsp");
        addRecipeIngredient(db, r_id, "chilli oil", 0.33, "cup");
        addRecipeIngredient(db, r_id, "masala", 2, "tsp");
        addRecipeIngredient(db, r_id, "garlic", 1, "tsp");
        addRecipeIngredient(db, r_id, "orange pepper", 1, "tsp");
        addRecipeIngredient(db, r_id, "parsley", 1, "tbsp");
        addRecipeIngredient(db, r_id, "lemon juice", 0.25, "cup");
        addRecipeIngredient(db, r_id, "tomato paste", 50, "g");
        addRecipeIngredient(db, r_id, "chilli sauce", 0.5, "cup");
        addRecipeIngredient(db, r_id, "cream", 400, "ml");
        addRecipeIngredient(db, r_id, "water", 0.25, "cup");
        addRecipeIngredient(db, r_id, "garlic cloves", 6, "clove");



        // Recipe 11: Aloo Fry

        r_id = addRecipe(db,
                "Aloo Fry",
                "1. Add potatoes, salt and turmeric to a pot of water and boil until tender.\n" +
                        "2. Cook cumin seeds, dried chillies, green chillies and curry leaves in a pan.\n" +
                        "3. Add onion and cook until soft.\n" +
                        "4. Add masala and mix well.\n" +
                        "5. Add the cooked potatoes.\n" +
                        "6. Add coriander and mix well.");

        addRecipeIngredient(db, r_id, "potato", 6, "piece");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "turmeric", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "salt", 1.5, "tsp");
        addRecipeIngredient(db, r_id, "cumin seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "masala", 2, "tsp");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "dried chilli", 2, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 2, "piece");



        // Recipe 12: Smashed Sweet Butternut

        r_id = addRecipe(db,
                "Smashed Sweet Butternut",
                "1. Add salt to a large pot of water.\n" +
                        "2. Add the butternut and boil until tender.\n" +
                        "3. Drain the water.\n" +
                        "4. Gently mash the butternut.\n" +
                        "5. Add butter and sugar and mix well.\n" +
                        "6. Add parsley and serve.");

        addRecipeIngredient(db, r_id, "butternut", 1.5, "kg");
        addRecipeIngredient(db, r_id, "butter", 1, "tbsp");
        addRecipeIngredient(db, r_id, "sugar", 1, "tsp");
        addRecipeIngredient(db, r_id, "parsley", 1, "tbsp");
        addRecipeIngredient(db, r_id, "salt", 1, "tsp");



        // Recipe 13: Creamy Spinach

        r_id = addRecipe(db,
                "Creamy Spinach",
                "1. Steam the spinach until soft and drain it.\n" +
                        "2. Melt butter in a pot.\n" +
                        "3. Add garlic and onion and cook until soft.\n" +
                        "4. Add flour and stir for 2 to 3 minutes.\n" +
                        "5. Slowly add milk while stirring.\n" +
                        "6. Add cheese and stir until melted.\n" +
                        "7. Season with salt and black pepper.\n" +
                        "8. Add the spinach and mix well.");

        addRecipeIngredient(db, r_id, "spinach", 800, "g");
        addRecipeIngredient(db, r_id, "butter", 4, "tbsp");
        addRecipeIngredient(db, r_id, "garlic", 4, "tsp");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "black pepper", 1, "tsp");
        addRecipeIngredient(db, r_id, "salt", 1, "tsp");
        addRecipeIngredient(db, r_id, "cheese", 1, "cup");
        addRecipeIngredient(db, r_id, "flour", 0.5, "cup");
        addRecipeIngredient(db, r_id, "milk", 2, "cup");



        // Recipe 14: Fish Curry

        r_id = addRecipe(db,
                "Fish Curry",
                "1. Cook mustard seeds and fenugreek seeds in a large pot.\n" +
                        "2. Add onion, chilli and curry leaves and cook until lightly brown.\n" +
                        "3. Add garlic and the ground spices.\n" +
                        "4. Add tomatoes and tomato paste and cook well.\n" +
                        "5. Add brinjal and cook for 5 to 7 minutes.\n" +
                        "6. Add tamarind water and cook for another 5 to 10 minutes.\n" +
                        "7. Add the fish and cook for 5 to 7 minutes on each side.\n" +
                        "8. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "fish", 1.2, "kg");
        addRecipeIngredient(db, r_id, "garlic", 85, "g");
        addRecipeIngredient(db, r_id, "tomato", 5, "piece");
        addRecipeIngredient(db, r_id, "tomato paste", 50, "g");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "curry leaves", 2, "sprig");
        addRecipeIngredient(db, r_id, "brinjal", 200, "g");
        addRecipeIngredient(db, r_id, "green chilli", 2, "piece");
        addRecipeIngredient(db, r_id, "masala", 5, "tbsp");
        addRecipeIngredient(db, r_id, "coriander cumin powder", 1, "tsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "mustard seeds", 1, "tsp");
        addRecipeIngredient(db, r_id, "fenugreek seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "tamarind", 1, "tbsp");
        addRecipeIngredient(db, r_id, "sugar", 1, "tsp");



        // Recipe 15: Tin Fish Curry

        r_id = addRecipe(db,
                "Tin Fish Curry",
                "1. Cook onion, chilli, curry leaves and garlic until the onion is lightly caramelised.\n" +
                        "2. Add turmeric and masala and cook for 2 to 3 minutes.\n" +
                        "3. Add tomatoes and sugar and simmer until thick.\n" +
                        "4. Gently add the tin fish and cook for 2 to 3 minutes.\n" +
                        "5. Add boiled eggs if using them.\n" +
                        "6. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "tin fish", 800, "g");
        addRecipeIngredient(db, r_id, "onion", 0.5, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 3, "piece");
        addRecipeIngredient(db, r_id, "masala", 2, "tbsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "garlic", 1, "tbsp");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "tomato", 4, "piece");
        addRecipeIngredient(db, r_id, "sugar", 0.5, "tsp");



        // Recipe 16: Mutton and Samp

        r_id = addRecipe(db,
                "Mutton and Samp",
                "1. Cook onion, curry leaves, chilli, ginger, garlic and whole spices.\n" +
                        "2. Add the ground spices and cook for a few minutes.\n" +
                        "3. Add tomato and cook until soft.\n" +
                        "4. Add the mutton and mix well.\n" +
                        "5. Add mint and thyme.\n" +
                        "6. Add salt, stock and hot water and cook until the meat is tender.\n" +
                        "7. Add the cooked samp and beans and cook together.\n" +
                        "8. Garnish with coriander.");

        addRecipeIngredient(db, r_id, "mutton", 1, "kg");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "cumin seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "fennel seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "cinnamon stick", 1, "piece");
        addRecipeIngredient(db, r_id, "cloves", 2, "piece");
        addRecipeIngredient(db, r_id, "cardamom", 1, "piece");
        addRecipeIngredient(db, r_id, "bay leaf", 1, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 2, "piece");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "ginger and garlic", 1, "tsp");
        addRecipeIngredient(db, r_id, "masala", 4, "tbsp");
        addRecipeIngredient(db, r_id, "garam masala", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "coriander cumin powder", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "tomato", 1, "piece");
        addRecipeIngredient(db, r_id, "mint leaves", 10, "piece");
        addRecipeIngredient(db, r_id, "thyme", 2, "sprig");
        addRecipeIngredient(db, r_id, "salt", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "stock cube", 1, "piece");
        addRecipeIngredient(db, r_id, "water", 200, "ml");
        addRecipeIngredient(db, r_id, "samp and beans", 500, "g");



        // Recipe 17: Kichari

        r_id = addRecipe(db,
                "Kichari",
                "1. Rinse the rice and lentils.\n" +
                        "2. Heat ghee, turmeric and cumin seeds.\n" +
                        "3. Add rice, lentils, salt, asafoetida and water.\n" +
                        "4. Cook until the rice and lentils are soft.\n" +
                        "5. In another pan heat ghee with cumin seeds, mustard seeds, garlic, shallot, chilli, curry leaves, turmeric and masala.\n" +
                        "6. Add the tempered spices to the cooked rice and lentils.\n" +
                        "7. Mix well and garnish with coriander.");

        addRecipeIngredient(db, r_id, "basmati rice", 1, "cup");
        addRecipeIngredient(db, r_id, "split red lentils", 1, "cup");
        addRecipeIngredient(db, r_id, "ghee", 4, "tbsp");
        addRecipeIngredient(db, r_id, "turmeric", 0.75, "tsp");
        addRecipeIngredient(db, r_id, "cumin seeds", 1, "tsp");
        addRecipeIngredient(db, r_id, "salt", 1, "tsp");
        addRecipeIngredient(db, r_id, "asafoetida", 0.25, "tsp");
        addRecipeIngredient(db, r_id, "water", 5, "cup");
        addRecipeIngredient(db, r_id, "mustard seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "garlic", 4, "clove");
        addRecipeIngredient(db, r_id, "shallot", 1, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 1, "piece");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "masala", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "coriander", 2, "tbsp");



        // Recipe 18: Pumpkin Curry

        r_id = addRecipe(db,
                "Pumpkin Curry",
                "1. Heat oil and add mustard seeds and cumin seeds.\n" +
                        "2. Add cinnamon, dried chilli and onion and cook for a few minutes.\n" +
                        "3. Add garlic and curry leaves.\n" +
                        "4. Add pumpkin and coat it in the onion mixture.\n" +
                        "5. Cover and cook for 10 minutes.\n" +
                        "6. Add water and continue cooking until soft.\n" +
                        "7. Add sugar if desired and garnish with coriander.");

        addRecipeIngredient(db, r_id, "pumpkin", 400, "g");
        addRecipeIngredient(db, r_id, "onion", 1, "piece");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "cumin seeds", 1, "tsp");
        addRecipeIngredient(db, r_id, "mustard seeds", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "cinnamon stick", 2, "piece");
        addRecipeIngredient(db, r_id, "garlic", 3, "clove");
        addRecipeIngredient(db, r_id, "dried chilli", 4, "piece");
        addRecipeIngredient(db, r_id, "sugar", 1, "tsp");
        addRecipeIngredient(db, r_id, "water", 0.25, "cup");



        // Recipe 19: Dhall Pita

        r_id = addRecipe(db,
                "Dhall Pita",
                "1. Add lentils, chilli, tomato, onion, turmeric, salt, asafoetida and water to a pot and cook until soft.\n" +
                        "2. Mix flour, turmeric, salt and oil in a bowl.\n" +
                        "3. Slowly add hot water and knead into a dough.\n" +
                        "4. Roll the dough and cut it into small diamond shapes.\n" +
                        "5. Heat oil and add cumin seeds, mustard seeds, curry leaves, chillies, shallots, garlic and turmeric.\n" +
                        "6. Add the tempered spices to the cooked lentils.\n" +
                        "7. Add the dough pieces and cook until they float.\n" +
                        "8. Add butter and coriander and mix.");

        addRecipeIngredient(db, r_id, "split red lentils", 1.5, "cup");
        addRecipeIngredient(db, r_id, "onion", 0.25, "piece");
        addRecipeIngredient(db, r_id, "tomato", 0.5, "piece");
        addRecipeIngredient(db, r_id, "green chilli", 2, "piece");
        addRecipeIngredient(db, r_id, "turmeric", 1.5, "tsp");
        addRecipeIngredient(db, r_id, "salt", 1.5, "tsp");
        addRecipeIngredient(db, r_id, "asafoetida", 0.5, "tsp");
        addRecipeIngredient(db, r_id, "water", 6.5, "cup");
        addRecipeIngredient(db, r_id, "butter", 15, "g");
        addRecipeIngredient(db, r_id, "flour", 1, "cup");
        addRecipeIngredient(db, r_id, "cooking oil", 5, "tbsp");
        addRecipeIngredient(db, r_id, "mustard seeds", 1, "tsp");
        addRecipeIngredient(db, r_id, "cumin seeds", 1, "tsp");
        addRecipeIngredient(db, r_id, "curry leaves", 1, "sprig");
        addRecipeIngredient(db, r_id, "shallot", 2, "piece");
        addRecipeIngredient(db, r_id, "garlic", 3, "clove");
        addRecipeIngredient(db, r_id, "dried chilli", 2, "piece");



        // Recipe 20: Mushroom and Broccoli Pasta

        r_id = addRecipe(db,
                "Mushroom and Broccoli Pasta",
                "1. Cook onion, garlic, chilli and thyme in a pot.\n" +
                        "2. Add mushrooms and cook until soft.\n" +
                        "3. Add onion soup powder and milk and mix well.\n" +
                        "4. Add cream, pasta water and broccoli.\n" +
                        "5. Simmer on low heat for about 5 minutes.\n" +
                        "6. Add the cooked pasta and coat it in the sauce.\n" +
                        "7. Garnish with parsley and serve.");

        addRecipeIngredient(db, r_id, "pasta", 150, "g");
        addRecipeIngredient(db, r_id, "broccoli", 0.5, "head");
        addRecipeIngredient(db, r_id, "mushrooms", 1, "punnet");
        addRecipeIngredient(db, r_id, "onion", 0.5, "piece");
        addRecipeIngredient(db, r_id, "red chilli", 1, "piece");
        addRecipeIngredient(db, r_id, "thyme", 3, "sprig");
        addRecipeIngredient(db, r_id, "garlic", 1, "tsp");
        addRecipeIngredient(db, r_id, "onion soup powder", 1, "packet");
        addRecipeIngredient(db, r_id, "milk", 0.5, "cup");
        addRecipeIngredient(db, r_id, "cream", 250, "ml");
    }


    // Updates the database when the version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Delete the old tables
        db.execSQL("DROP TABLE IF EXISTS " + recipe_ingredient_table);
        db.execSQL("DROP TABLE IF EXISTS " + recipe_table);
        db.execSQL("DROP TABLE IF EXISTS " + pantry_table);

        // Create the tables again
        onCreate(db);
    }
}