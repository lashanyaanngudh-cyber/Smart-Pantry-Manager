package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;

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

        // Make the RecyclerView display items in a vertical list
        pantry_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get all pantry items from the database
        p_items = dbHelper.getAllPantryItems();

        // Create the adapter using the pantry items
        pantry_adapter = new PantryAdapter(p_items);

        // Connect the adapter to the RecyclerView
        pantry_recyclerView.setAdapter(pantry_adapter);
    }
}