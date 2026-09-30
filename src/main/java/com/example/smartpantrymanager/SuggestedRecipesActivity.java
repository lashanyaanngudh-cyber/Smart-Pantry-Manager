package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.RecipeAdapter;
import com.example.smartpantrymanager.database.PantryDatabaseHelper;
import com.example.smartpantrymanager.matching.RecipeMatcher;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SuggestedRecipesActivity extends Activity {

    // Variables used on the suggested recipes screen
    RecyclerView recipe_recyclerView;
    PantryDatabaseHelper pantry_dbHelper;
    RecipeMatcher recipe_matcher;
    List<Recipe> matched_rList;
    TextView no_recipesText;

    // Bottom navigation used to move between the main screens
    BottomNavigationView bottom_navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        // Connect the Java variables to the XML views
        recipe_recyclerView =
                findViewById(R.id.recipe_recyclerView);

        no_recipesText =
                findViewById(R.id.no_recipesText);

        // Connect the bottom navigation
        bottom_navigationView =
                findViewById(R.id.bottom_navigationView);

        // Show Recipes as the selected screen
        bottom_navigationView.setSelectedItemId(
                R.id.nav_recipes
        );

        // Create the database helper
        pantry_dbHelper =
                new PantryDatabaseHelper(this);

        // Create the recipe matcher
        recipe_matcher =
                new RecipeMatcher();

        // Get the current pantry items
        List<PantryItem> pantry_itemList =
                pantry_dbHelper.getAllPantryItems();

        // Get all recipes from the database
        List<Recipe> r_itemList =
                pantry_dbHelper.getAllRecipes();

        // Find the recipes that can be made
        matched_rList =
                recipe_matcher.getMatchedRecipes(
                        r_itemList,
                        pantry_itemList
                );

        // Set the layout for the RecyclerView
        recipe_recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Check if there are any matching recipes
        if (matched_rList.isEmpty()) {

            // Show the no recipes message
            no_recipesText.setVisibility(View.VISIBLE);

            recipe_recyclerView.setVisibility(View.GONE);

        } else {

            // Hide the no recipes message
            no_recipesText.setVisibility(View.GONE);

            recipe_recyclerView.setVisibility(View.VISIBLE);

            // Display the matching recipes
            RecipeAdapter recipe_adapter =
                    new RecipeAdapter(matched_rList);

            recipe_recyclerView.setAdapter(recipe_adapter);
        }


        // Move between the main screens
        bottom_navigationView.setOnItemSelectedListener(item -> {

            // Get the selected navigation item
            int item_id = item.getItemId();

            // Open the Pantry screen
            if (item_id == R.id.nav_pantry) {

                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        MainActivity.class
                );

                startActivity(intent);
                finish();

                return true;
            }

            // Stay on the Recipes screen
            if (item_id == R.id.nav_recipes) {
                return true;
            }

            // Open the Settings screen
            if (item_id == R.id.nav_settings) {

                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        SettingsActivity.class
                );

                startActivity(intent);
                finish();

                return true;
            }

            return false;
        });
    }
}