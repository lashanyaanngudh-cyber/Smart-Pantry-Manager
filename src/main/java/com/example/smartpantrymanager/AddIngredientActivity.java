package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.database.PantryDatabaseHelper;

public class AddIngredientActivity extends Activity {

    // Input fields for the ingredient information
    EditText ing_nameView;
    EditText ing_quantityView;
    EditText ing_unitView;
    EditText ing_expirydateView;

    // Button used to add the ingredient
    Button add_ing_buttonView;

    // Used to access the pantry database
    PantryDatabaseHelper pantry_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // Connect the Java input fields to the XML input fields
        ing_nameView = findViewById(R.id.ing_name);
        ing_quantityView = findViewById(R.id.ing_quantity);
        ing_unitView = findViewById(R.id.ing_unit);
        ing_expirydateView = findViewById(R.id.ing_expirydate);

        // Connect the Java button to the XML button
        add_ing_buttonView = findViewById(R.id.add_ing_button);

        // Connect to the pantry database
        pantry_db = new PantryDatabaseHelper(this);

        // Add the ingredient when the button is clicked
        add_ing_buttonView.setOnClickListener(v -> {

            // Get the information entered by the user
            String pItem_name = ing_nameView.getText().toString().trim();
            String quantity_text = ing_quantityView.getText().toString().trim();
            String pItem_unit = ing_unitView.getText().toString().trim();
            String pItem_expiry_date = ing_expirydateView.getText().toString().trim();

            // Check that all fields have been completed
            if (pItem_name.isEmpty() || quantity_text.isEmpty()
                    || pItem_unit.isEmpty() || pItem_expiry_date.isEmpty()) {

                Toast.makeText(this,
                        "Please complete all fields",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // Convert the quantity from text to a number
            double pItem_quantity = Double.parseDouble(quantity_text);

            // Add the ingredient to the database
            long result = pantry_db.addPantryItem(
                    pItem_name,
                    pItem_quantity,
                    pItem_unit,
                    pItem_expiry_date
            );

            // Check if the ingredient was added successfully
            // Check if the ingredient was added successfully
            if (result != -1) {

                Toast.makeText(this,
                        "Ingredient added successfully",
                        Toast.LENGTH_SHORT).show();

                // Return to the pantry list
                finish();

            } else {

                Toast.makeText(this,
                        "Ingredient could not be added",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Add padding so the screen does not overlap the system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });
    }
}