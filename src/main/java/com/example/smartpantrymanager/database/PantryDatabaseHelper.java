package com.example.smartpantrymanager.database;

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


    // Creates the tables when the database is first created
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


        // Creates Recipes table
        String createRecipeTable =
                "CREATE TABLE " + recipe_table + " (" +
                        recipe_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        recipe_name + " TEXT NOT NULL, " +
                        recipe_instructions + " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeTable);


        // Creates Recipe Ingredients table
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
    }


    // Updates the database when the database version changes
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