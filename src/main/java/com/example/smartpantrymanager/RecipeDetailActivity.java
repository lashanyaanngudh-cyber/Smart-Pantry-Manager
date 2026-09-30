package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
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
    Button btnBack;

    PantryDatabaseHelper pantry_dbHelper;
    Recipe selectedRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        // Connect the Java variables to the XML views
        txtRecipeName =
                findViewById(R.id.txtRecipeName);

        txtIngredients =
                findViewById(R.id.txtIngredients);

        txtInstructions =
                findViewById(R.id.txtInstructions);

        btnBack =
                findViewById(R.id.btnBack);


        // Create the database helper
        pantry_dbHelper =
                new PantryDatabaseHelper(this);


        // Get the recipe ID sent from the suggested recipes screen
        int r_id = getIntent().getIntExtra("r_id", -1);


        // Get all recipes from the database
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
                    selectedRecipe.getR_name());


            // Create the ingredient list
            StringBuilder rIng_text =
                    new StringBuilder();

            for (RecipeIngredient rIng_item :
                    selectedRecipe.getR_ingList()) {

                rIng_text.append("• ")
                        .append(rIng_item.getRIng_quantity())
                        .append(" ")
                        .append(rIng_item.getRIng_unit())
                        .append(" ")
                        .append(rIng_item.getRIng_name())
                        .append("\n");
            }


            // Display the ingredients
            txtIngredients.setText(
                    rIng_text.toString());


            // Display the recipe instructions
            txtInstructions.setText(
                    selectedRecipe.getR_instructions());
        }


        // Go back to the suggested recipes screen
        btnBack.setOnClickListener(view -> finish());
    }
}