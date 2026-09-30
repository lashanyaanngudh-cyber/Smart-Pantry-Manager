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
    private Spinner spinnerUnit;
    private Button buttonSaveSettings;

    // Bottom navigation used to move between the main screens
    private BottomNavigationView bottom_navigationView;

    // Used to save the user's settings
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Connect Java variables to XML components
        switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);

        spinnerUnit =
                findViewById(R.id.spinnerUnit);

        buttonSaveSettings =
                findViewById(R.id.buttonSaveSettings);

        // Connect the bottom navigation
        bottom_navigationView =
                findViewById(R.id.bottom_navigationView);

        // Show Settings as the selected screen
        bottom_navigationView.setSelectedItemId(
                R.id.nav_settings
        );

        // Open the saved settings
        preferences = getSharedPreferences(
                "PantrySettings",
                MODE_PRIVATE
        );

        // Unit options shown in the Spinner
        String[] units = {
                "Grams",
                "Kilograms",
                "Millilitres",
                "Litres"
        };

        // Creates the Spinner list
        ArrayAdapter<String> unitAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        // Sets the layout for the Spinner options
        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(unitAdapter);

        // Load previously saved settings
        loadSettings();

        // Save settings when button is clicked
        buttonSaveSettings.setOnClickListener(view -> {
            saveSettings();
        });


        // Move between the main screens
        bottom_navigationView.setOnItemSelectedListener(item -> {

            // Get the selected navigation item
            int item_id = item.getItemId();

            // Open the Pantry screen
            if (item_id == R.id.nav_pantry) {

                Intent intent = new Intent(
                        SettingsActivity.this,
                        MainActivity.class
                );

                startActivity(intent);
                finish();

                return true;
            }

            // Open the Suggested Recipes screen
            if (item_id == R.id.nav_recipes) {

                Intent intent = new Intent(
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


    // Save the user's settings
    private void saveSettings() {

        // Get the values selected by the user
        boolean expiryAlerts =
                switchExpiryAlerts.isChecked();

        int selectedUnit =
                spinnerUnit.getSelectedItemPosition();

        // Open the settings editor
        SharedPreferences.Editor editor =
                preferences.edit();

        // Save the selected settings
        editor.putBoolean(
                "expiryAlerts",
                expiryAlerts
        );

        editor.putInt(
                "selectedUnit",
                selectedUnit
        );

        editor.apply();

        // Tell the user the settings were saved
        Toast.makeText(
                this,
                "Settings saved",
                Toast.LENGTH_SHORT
        ).show();
    }


    // Load the user's saved settings
    private void loadSettings() {

        // Get the saved alert setting
        boolean expiryAlerts =
                preferences.getBoolean(
                        "expiryAlerts",
                        true
                );

        // Get the saved unit
        int selectedUnit =
                preferences.getInt(
                        "selectedUnit",
                        0
                );

        // Display the saved settings
        switchExpiryAlerts.setChecked(expiryAlerts);
        spinnerUnit.setSelection(selectedUnit);
    }
}