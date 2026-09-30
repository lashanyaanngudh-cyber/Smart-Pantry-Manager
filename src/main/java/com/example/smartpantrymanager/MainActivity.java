package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.PantryDatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends Activity {

    // Database helper
    private PantryDatabaseHelper dbHelper;

    // RecyclerView that displays the pantry items
    private RecyclerView pantry_recyclerView;

    // Button used to add a new ingredient
    private Button open_add_ing_buttonView;

    // Bottom navigation used to move between the main screens
    private BottomNavigationView bottom_navigationView;

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

        // Connect the RecyclerView
        pantry_recyclerView =
                findViewById(R.id.pantry_recyclerView);

        // Connect the Add Ingredient button
        open_add_ing_buttonView =
                findViewById(R.id.open_add_ing_button);

        // Connect the bottom navigation
        bottom_navigationView =
                findViewById(R.id.bottom_navigationView);

        // Show Pantry as the selected screen
        bottom_navigationView.setSelectedItemId(
                R.id.nav_pantry
        );


        // Open the Add Ingredient screen
        open_add_ing_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );

            startActivity(intent);
        });


        // Move between the main screens
        bottom_navigationView.setOnItemSelectedListener(item -> {

            // Get the selected navigation item
            int item_id = item.getItemId();

            // Stay on the Pantry screen
            if (item_id == R.id.nav_pantry) {
                return true;
            }

            // Open the Suggested Recipes screen
            if (item_id == R.id.nav_recipes) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                );

                startActivity(intent);

                return true;
            }

            // Open the Settings screen
            if (item_id == R.id.nav_settings) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                );

                startActivity(intent);

                return true;
            }

            return false;
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

        // Show Pantry as the selected navigation item
        if (bottom_navigationView != null) {
            bottom_navigationView.setSelectedItemId(
                    R.id.nav_pantry
            );
        }

        // Refresh the pantry list
        if (pantry_adapter != null) {

            // Get the updated pantry items
            p_items.clear();
            p_items.addAll(
                    dbHelper.getAllPantryItems()
            );

            // Update the RecyclerView
            pantry_adapter.notifyDataSetChanged();
        }
    }
}