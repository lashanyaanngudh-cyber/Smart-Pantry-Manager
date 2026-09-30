package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;

// Used to open another Activity
import android.content.Intent;

// Used for the screen buttons
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.PantryDatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;

import java.util.List;

public class MainActivity extends Activity {

    // Database helper
    private PantryDatabaseHelper dbHelper;

    // RecyclerView that displays the pantry items
    private RecyclerView pantry_recyclerView;

    // Button used to open the Add Ingredient screen
    private Button open_add_ing_buttonView;

    // Button used to open the Suggested Recipes screen
    private Button open_recipes_buttonView;

    // Button used to open the Settings screen
    private Button open_settings_buttonView;

    // List that stores the pantry items
    private List<PantryItem> p_items;

    // Adapter that connects the pantry items to the RecyclerView
    private PantryAdapter pantry_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the database helper
        dbHelper = new PantryDatabaseHelper(this);

        // Open the database
        dbHelper.getWritableDatabase();

        // Connect the RecyclerView to the RecyclerView in activity_main.xml
        pantry_recyclerView = findViewById(R.id.pantry_recyclerView);

        // Connect the Add Ingredient button
        open_add_ing_buttonView = findViewById(R.id.open_add_ing_button);

        // Connect the Suggested Recipes button
        open_recipes_buttonView = findViewById(R.id.open_recipes_button);

        // Connect the Settings button
        open_settings_buttonView = findViewById(R.id.open_settings_button);

        // Open the Add Ingredient screen when the button is clicked
        open_add_ing_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );

            startActivity(intent);
        });

        // Open the Suggested Recipes screen when the button is clicked
        open_recipes_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        // Open the Settings screen when the button is clicked
        open_settings_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        // Make the RecyclerView display items in a vertical list
        pantry_recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Get all pantry items from the database
        p_items = dbHelper.getAllPantryItems();

        // Create the adapter using the pantry items
        pantry_adapter = new PantryAdapter(p_items);

        // Connect the adapter to the RecyclerView
        pantry_recyclerView.setAdapter(pantry_adapter);
    }


    // Refresh the pantry list when returning to this screen
    @Override
    protected void onResume() {
        super.onResume();

        // Only refresh if the adapter has already been created
        if (pantry_adapter != null) {

            // Get the updated pantry items from the database
            p_items.clear();
            p_items.addAll(dbHelper.getAllPantryItems());

            // Tell the RecyclerView that the information has changed
            pantry_adapter.notifyDataSetChanged();
        }
    }
}