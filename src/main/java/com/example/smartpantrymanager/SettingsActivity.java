package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends Activity {

    // Settings screen components
    private Switch switchExpiryAlerts;
    private Switch switchLowStockAlerts;
    private Spinner spinnerUnit;
    private Button buttonSaveSettings;

    // Bottom navigation
    private BottomNavigationView bottom_navigationView;

    // Used to save the settings
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        // Connect the expiry switch
        switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);


        // Connect the low stock switch
        switchLowStockAlerts =
                findViewById(R.id.switchLowStockAlerts);


        // Connect the unit spinner
        spinnerUnit =
                findViewById(R.id.spinnerUnit);


        // Connect the save button
        buttonSaveSettings =
                findViewById(R.id.buttonSaveSettings);


        // Connect the bottom navigation
        bottom_navigationView =
                findViewById(R.id.bottom_navigationView);


        // Show Settings as selected
        bottom_navigationView.setSelectedItemId(
                R.id.nav_settings
        );


        // Open the saved settings
        preferences =
                getSharedPreferences(
                        "PantrySettings",
                        MODE_PRIVATE
                );


        // Unit options
        String[] units = {
                "Grams",
                "Kilograms",
                "Millilitres",
                "Litres",
                "Pieces"
        };


        // Create the unit list
        ArrayAdapter<String> unitAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );


        // Set the Spinner layout
        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );


        spinnerUnit.setAdapter(
                unitAdapter
        );


        // Load the saved settings
        loadSettings();


        // Save the settings
        buttonSaveSettings.setOnClickListener(view -> {

            saveSettings();

        });


        // Move between the main screens
        bottom_navigationView.setOnItemSelectedListener(item -> {

            // Get the selected item
            int item_id =
                    item.getItemId();


            // Open the Home screen
            if (item_id == R.id.nav_home) {

                Intent intent =
                        new Intent(
                                SettingsActivity.this,
                                HomeActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }


            // Open the Pantry screen
            if (item_id == R.id.nav_pantry) {

                Intent intent =
                        new Intent(
                                SettingsActivity.this,
                                MainActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }


            // Open the Recipes screen
            if (item_id == R.id.nav_recipes) {

                Intent intent =
                        new Intent(
                                SettingsActivity.this,
                                SuggestedRecipesActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }


            // Stay on the Settings screen
            if (item_id == R.id.nav_settings) {

                return true;
            }


            return false;
        });
    }


    // Save the settings
    private void saveSettings() {

        // Get the expiry alert setting
        boolean expiryAlerts =
                switchExpiryAlerts.isChecked();


        // Get the low stock alert setting
        boolean lowStockAlerts =
                switchLowStockAlerts.isChecked();


        // Get the selected unit
        int selectedUnit =
                spinnerUnit.getSelectedItemPosition();


        // Open the settings editor
        SharedPreferences.Editor editor =
                preferences.edit();


        // Save expiry alerts
        editor.putBoolean(
                "expiryAlerts",
                expiryAlerts
        );


        // Save low stock alerts
        editor.putBoolean(
                "lowStockAlerts",
                lowStockAlerts
        );


        // Save the selected unit
        editor.putInt(
                "selectedUnit",
                selectedUnit
        );


        // Save the changes
        editor.apply();


        // Show saved message
        Toast.makeText(
                this,
                "Settings saved",
                Toast.LENGTH_SHORT
        ).show();
    }


    // Load the saved settings
    private void loadSettings() {

        // Get the expiry alert setting
        boolean expiryAlerts =
                preferences.getBoolean(
                        "expiryAlerts",
                        true
                );


        // Get the low stock alert setting
        boolean lowStockAlerts =
                preferences.getBoolean(
                        "lowStockAlerts",
                        true
                );


        // Get the selected unit
        int selectedUnit =
                preferences.getInt(
                        "selectedUnit",
                        0
                );


        // Show the saved settings
        switchExpiryAlerts.setChecked(
                expiryAlerts
        );

        switchLowStockAlerts.setChecked(
                lowStockAlerts
        );

        spinnerUnit.setSelection(
                selectedUnit
        );
    }


    // Keep Settings selected
    @Override
    protected void onResume() {
        super.onResume();


        if (bottom_navigationView != null) {

            bottom_navigationView.setSelectedItemId(
                    R.id.nav_settings
            );
        }
    }
}