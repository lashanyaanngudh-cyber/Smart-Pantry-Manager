package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.database.PantryDatabaseHelper;

public class DeleteIngredientActivity extends Activity {

    // Message that shows which ingredient will be deleted
    TextView delete_ing_messageView;

    // Buttons used to delete or cancel
    Button delete_ing_buttonView;
    Button cancel_delete_buttonView;

    // Used to access the pantry database
    PantryDatabaseHelper pantry_db;

    // Stores the ID of the pantry item being deleted
    int pItem_id;

    // Stores the name of the pantry item being deleted
    String pItem_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ingredient);

        // Connect the Java TextView to the XML TextView
        delete_ing_messageView = findViewById(R.id.delete_ing_message);

        // Connect the Java buttons to the XML buttons
        delete_ing_buttonView = findViewById(R.id.delete_ing_button);
        cancel_delete_buttonView = findViewById(R.id.cancel_delete_button);

        // Connect to the pantry database
        pantry_db = new PantryDatabaseHelper(this);

        // Get the pantry item ID sent from the Edit Ingredient screen
        pItem_id = getIntent().getIntExtra("pItem_id", -1);

        // Get the pantry item name sent from the Edit Ingredient screen
        pItem_name = getIntent().getStringExtra("pItem_name");

        // Show which ingredient is going to be deleted
        delete_ing_messageView.setText(
                "Are you sure you want to delete " + pItem_name + "?"
        );

        // Delete the ingredient when the Delete button is clicked
        delete_ing_buttonView.setOnClickListener(v -> {

            // Delete the pantry item from the database
            int result = pantry_db.deletePantryItem(pItem_id);

            // Check if the ingredient was deleted successfully
            if (result > 0) {

                Toast.makeText(this,
                        "Ingredient deleted successfully",
                        Toast.LENGTH_SHORT).show();

                // Return directly to the pantry list
                Intent intent = new Intent(
                        DeleteIngredientActivity.this,
                        MainActivity.class
                );

                // Remove the Edit and Delete screens from the back stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                // Close the Delete Ingredient screen
                finish();

            } else {

                Toast.makeText(this,
                        "Ingredient could not be deleted",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Close this screen without deleting the ingredient
        cancel_delete_buttonView.setOnClickListener(v -> {
            finish();
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