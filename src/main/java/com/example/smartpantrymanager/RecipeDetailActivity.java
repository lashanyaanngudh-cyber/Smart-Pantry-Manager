package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.smartpantrymanager.database.PantryDatabaseHelper;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity extends Activity {

    // Variables used on the recipe detail screen
    TextView txtRecipeName;
    TextView txtIngredients;
    TextView txtInstructions;

    // Back arrow
    TextView recipe_back_arrowView;

    // Database helper
    PantryDatabaseHelper pantry_dbHelper;

    // Stores the selected recipe
    Recipe selectedRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);


        // Connect the recipe name
        txtRecipeName =
                findViewById(R.id.txtRecipeName);


        // Connect the ingredients
        txtIngredients =
                findViewById(R.id.txtIngredients);


        // Connect the instructions
        txtInstructions =
                findViewById(R.id.txtInstructions);


        // Connect the back arrow
        recipe_back_arrowView =
                findViewById(R.id.recipe_back_arrow);


        // Create the database helper
        pantry_dbHelper =
                new PantryDatabaseHelper(this);


        // Get the recipe ID
        int r_id =
                getIntent().getIntExtra(
                        "r_id",
                        -1
                );


        // Get all recipes
        List<Recipe> r_itemList =
                pantry_dbHelper.getAllRecipes();


        // Find the selected recipe
        for (Recipe r_item : r_itemList) {

            if (r_item.getR_id() == r_id) {

                selectedRecipe = r_item;

                break;
            }
        }


        // Display the selected recipe
        if (selectedRecipe != null) {

            // Display the recipe name
            txtRecipeName.setText(
                    selectedRecipe.getR_name()
            );


            // Create the ingredient list
            StringBuilder rIng_text =
                    new StringBuilder();


            // Add each ingredient
            for (RecipeIngredient rIng_item :
                    selectedRecipe.getR_ingList()) {

                rIng_text
                        .append("• ")
                        .append(rIng_item.getRIng_quantity())
                        .append(" ")
                        .append(rIng_item.getRIng_unit())
                        .append(" ")
                        .append(rIng_item.getRIng_name())
                        .append("\n");
            }


            // Display the ingredients
            txtIngredients.setText(
                    rIng_text.toString()
            );


            // Display the recipe instructions
            txtInstructions.setText(
                    selectedRecipe.getR_instructions()
            );
        }


        // Go back to the Recipes screen
        recipe_back_arrowView.setOnClickListener(view -> {

            finish();

        });
    }
}