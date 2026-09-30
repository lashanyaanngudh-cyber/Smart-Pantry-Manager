package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import android.app.Activity;

public class SettingsActivity extends Activity {

    // Settings screen components
    private Switch switchExpiryAlerts;
    private Spinner spinnerUnit;
    private Button buttonSaveSettings;

    // Used to save the user's settings
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Connect Java variables to XML components
        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        buttonSaveSettings = findViewById(R.id.buttonSaveSettings);

        // Open the saved settings
        preferences = getSharedPreferences("PantrySettings", MODE_PRIVATE);

        // Unit options shown in the Spinner
        String[] units = {
                "Grams",
                "Kilograms",
                "Millilitres",
                "Litres"
        };

        // Creates the Spinner list
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
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
        buttonSaveSettings.setOnClickListener(view -> saveSettings());
    }

    private void saveSettings() {

        // Get the values selected by the user
        boolean expiryAlerts = switchExpiryAlerts.isChecked();
        int selectedUnit = spinnerUnit.getSelectedItemPosition();

        // Open the settings editor
        SharedPreferences.Editor editor = preferences.edit();

        // Save the selected settings
        editor.putBoolean("expiryAlerts", expiryAlerts);
        editor.putInt("selectedUnit", selectedUnit);

        editor.apply();

        // Tell the user the settings were saved
        Toast.makeText(
                this,
                "Settings saved",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void loadSettings() {

        // Get the saved alert setting
        boolean expiryAlerts = preferences.getBoolean(
                "expiryAlerts",
                true
        );

        // Get the saved unit
        int selectedUnit = preferences.getInt(
                "selectedUnit",
                0
        );

        // Display the saved settings
        switchExpiryAlerts.setChecked(expiryAlerts);
        spinnerUnit.setSelection(selectedUnit);
    }
}