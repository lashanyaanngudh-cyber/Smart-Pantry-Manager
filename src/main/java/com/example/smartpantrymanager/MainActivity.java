package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.PantryDatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    // Database helper
    private PantryDatabaseHelper dbHelper;

    // Displays the pantry items
    private RecyclerView pantry_recyclerView;

    // Button used to add an item
    private Button open_add_ing_buttonView;

    // Displays the total number of items
    private TextView total_items_textView;

    // Displays the number of low stock items
    private TextView low_stock_textView;

    // Displays the number of expiring items
    private TextView expiring_items_textView;

    // Bottom navigation
    private BottomNavigationView bottom_navigationView;

    // Stores the pantry items
    private List<PantryItem> p_items;

    // Connects the pantry items to the RecyclerView
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

        // Connect the Add Item button
        open_add_ing_buttonView =
                findViewById(R.id.open_add_ing_button);

        // Connect the total items
        total_items_textView =
                findViewById(R.id.total_items_text);

        // Connect the low stock items
        low_stock_textView =
                findViewById(R.id.low_stock_text);

        // Connect the expiring items
        expiring_items_textView =
                findViewById(R.id.expiring_items_text);

        // Connect the bottom navigation
        bottom_navigationView =
                findViewById(R.id.bottom_navigationView);


        // Show Pantry as selected
        bottom_navigationView.setSelectedItemId(
                R.id.nav_pantry
        );


        // Open the Add Item screen
        open_add_ing_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );

            startActivity(intent);
        });


        // Move between the main screens
        bottom_navigationView.setOnItemSelectedListener(item -> {

            // Get the selected item
            int item_id = item.getItemId();


            // Open Home
            if (item_id == R.id.nav_home) {

                Intent intent = new Intent(
                        MainActivity.this,
                        HomeActivity.class
                );

                startActivity(intent);

                return true;
            }


            // Stay on Pantry
            if (item_id == R.id.nav_pantry) {

                return true;
            }


            // Open Recipes
            if (item_id == R.id.nav_recipes) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                );

                startActivity(intent);

                return true;
            }


            // Open Settings
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


        // Display the pantry items in a vertical list
        pantry_recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );


        // Get all pantry items
        p_items =
                dbHelper.getAllPantryItems();


        // Display the total number of items
        total_items_textView.setText(
                String.valueOf(p_items.size())
        );


        // Display the number of low stock items
        low_stock_textView.setText(
                String.valueOf(countLowStockItems())
        );


        // Display the number of expiring items
        expiring_items_textView.setText(
                String.valueOf(countExpiringItems())
        );


        // Create the pantry adapter
        pantry_adapter =
                new PantryAdapter(p_items);


        // Connect the adapter
        pantry_recyclerView.setAdapter(
                pantry_adapter
        );
    }


    // Refresh the pantry when returning to the screen
    @Override
    protected void onResume() {
        super.onResume();


        // Show Pantry as selected
        if (bottom_navigationView != null) {

            bottom_navigationView.setSelectedItemId(
                    R.id.nav_pantry
            );
        }


        // Refresh the pantry list
        if (pantry_adapter != null) {

            // Clear the old list
            p_items.clear();

            // Get the updated items
            p_items.addAll(
                    dbHelper.getAllPantryItems()
            );


            // Update total items
            total_items_textView.setText(
                    String.valueOf(p_items.size())
            );


            // Update low stock items
            low_stock_textView.setText(
                    String.valueOf(countLowStockItems())
            );


            // Update expiring items
            expiring_items_textView.setText(
                    String.valueOf(countExpiringItems())
            );


            // Update the RecyclerView
            pantry_adapter.notifyDataSetChanged();
        }
    }


    // Counts pantry items that are low in stock
    private int countLowStockItems() {

        // Stores the number of low stock items
        int low_stock_count = 0;


        // Check every pantry item
        for (PantryItem p_item : p_items) {

            // An item is low stock when the quantity is 2 or less
            if (p_item.getPItem_quantity() <= 2) {

                low_stock_count++;
            }
        }


        // Return the number of low stock items
        return low_stock_count;
    }


    // Counts items that expire within seven days
    private int countExpiringItems() {

        // Stores the number of expiring items
        int expiring_count = 0;


        // Date format used in the database
        SimpleDateFormat date_format =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                );

        date_format.setLenient(false);


        // Get today's date
        Calendar today_calendar =
                Calendar.getInstance();


        // Remove the current time
        today_calendar.set(
                Calendar.HOUR_OF_DAY,
                0
        );

        today_calendar.set(
                Calendar.MINUTE,
                0
        );

        today_calendar.set(
                Calendar.SECOND,
                0
        );

        today_calendar.set(
                Calendar.MILLISECOND,
                0
        );


        Date today_date =
                today_calendar.getTime();


        // Get the date seven days from today
        Calendar seven_days_calendar =
                Calendar.getInstance();

        seven_days_calendar.setTime(
                today_date
        );

        seven_days_calendar.add(
                Calendar.DAY_OF_YEAR,
                7
        );


        Date seven_days_date =
                seven_days_calendar.getTime();


        // Check every pantry item
        for (PantryItem p_item : p_items) {

            // Get the expiry date
            String expiry_date =
                    p_item.getPItem_expiry_date();


            // Check items that have an expiry date
            if (expiry_date != null &&
                    !expiry_date.trim().isEmpty()) {

                try {

                    // Convert the expiry date
                    Date item_expiry_date =
                            date_format.parse(expiry_date);


                    // Check if it expires within seven days
                    if (!item_expiry_date.before(today_date)
                            && !item_expiry_date.after(seven_days_date)) {

                        expiring_count++;
                    }

                } catch (ParseException e) {

                    // Ignore an incorrect date
                }
            }
        }


        // Return the number of expiring items
        return expiring_count;
    }
}