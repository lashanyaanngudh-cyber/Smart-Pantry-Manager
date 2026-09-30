package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartpantrymanager.database.PantryDatabaseHelper;

public class AddIngredientActivity extends Activity {

    // Input fields for the item information
    EditText ing_nameView;
    EditText ing_quantityView;
    EditText ing_unitView;
    EditText ing_expirydateView;

    // Button used to save or update the item
    Button add_ing_buttonView;

    // Button used to open the Delete Item screen
    Button open_delete_ing_buttonView;

    // Existing hidden Back to Pantry button
    Button back_pantry_buttonView;

    // Arrow used to return to the Pantry screen
    TextView back_arrowView;

    // Used to access the pantry database
    PantryDatabaseHelper pantry_db;

    // Stores the ID of the pantry item being edited
    int pItem_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_ingredient);


        // Connect the Java input fields to the XML input fields
        ing_nameView = findViewById(R.id.ing_name);
        ing_quantityView = findViewById(R.id.ing_quantity);
        ing_unitView = findViewById(R.id.ing_unit);
        ing_expirydateView = findViewById(R.id.ing_expirydate);


        // Connect the buttons
        add_ing_buttonView =
                findViewById(R.id.add_ing_button);

        open_delete_ing_buttonView =
                findViewById(R.id.open_delete_ing_button);

        back_pantry_buttonView =
                findViewById(R.id.back_pantry_button);


        // Connect the back arrow
        back_arrowView =
                findViewById(R.id.back_arrow);


        // Connect to the pantry database
        pantry_db =
                new PantryDatabaseHelper(this);


        // Check if an existing pantry item was selected
        if (getIntent().hasExtra("pItem_id")) {

            // Get the pantry item ID
            pItem_id =
                    getIntent().getIntExtra(
                            "pItem_id",
                            -1
                    );


            // Change the Save button because an item is being edited
            add_ing_buttonView.setText(
                    "Update Item"
            );


            // Show the Delete Item button when editing
            open_delete_ing_buttonView.setVisibility(
                    View.VISIBLE
            );


            // Display the existing pantry item name
            ing_nameView.setText(
                    getIntent().getStringExtra(
                            "pItem_name"
                    )
            );


            // Display the existing pantry item quantity
            ing_quantityView.setText(
                    String.valueOf(
                            getIntent().getDoubleExtra(
                                    "pItem_quantity",
                                    0
                            )
                    )
            );


            // Display the existing pantry item unit
            ing_unitView.setText(
                    getIntent().getStringExtra(
                            "pItem_unit"
                    )
            );


            // Display the existing pantry item expiry date
            ing_expirydateView.setText(
                    getIntent().getStringExtra(
                            "pItem_expiry_date"
                    )
            );
        }


        // Return to the Pantry screen when the arrow is clicked
        back_arrowView.setOnClickListener(v -> {

            finish();

        });


        // Open the Delete Item screen
        open_delete_ing_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AddIngredientActivity.this,
                    DeleteIngredientActivity.class
            );


            // Send the pantry item ID
            intent.putExtra(
                    "pItem_id",
                    pItem_id
            );


            // Send the pantry item name
            intent.putExtra(
                    "pItem_name",
                    ing_nameView
                            .getText()
                            .toString()
                            .trim()
            );


            startActivity(intent);
        });


        // Save or update the item
        add_ing_buttonView.setOnClickListener(v -> {

            // Get the information entered by the user
            String pItem_name =
                    ing_nameView
                            .getText()
                            .toString()
                            .trim();

            String quantity_text =
                    ing_quantityView
                            .getText()
                            .toString()
                            .trim();

            String pItem_unit =
                    ing_unitView
                            .getText()
                            .toString()
                            .trim();

            String pItem_expiry_date =
                    ing_expirydateView
                            .getText()
                            .toString()
                            .trim();


            // Check if the item name is empty
            if (pItem_name.isEmpty()) {

                ing_nameView.setError(
                        "Please enter an item name"
                );

                ing_nameView.requestFocus();

                return;
            }


            // Check if the quantity is empty
            if (quantity_text.isEmpty()) {

                ing_quantityView.setError(
                        "Please enter a quantity"
                );

                ing_quantityView.requestFocus();

                return;
            }


            // Check if the unit is empty
            if (pItem_unit.isEmpty()) {

                ing_unitView.setError(
                        "Please enter a unit"
                );

                ing_unitView.requestFocus();

                return;
            }


            // Check if the quantity contains a valid number
            try {

                Double.parseDouble(
                        quantity_text
                );

            } catch (NumberFormatException e) {

                ing_quantityView.setError(
                        "Please enter a valid quantity"
                );

                ing_quantityView.requestFocus();

                return;
            }


            // Convert the quantity from text to a number
            double pItem_quantity =
                    Double.parseDouble(
                            quantity_text
                    );


            // Check that the quantity is greater than zero
            if (pItem_quantity <= 0) {

                ing_quantityView.setError(
                        "Quantity must be greater than 0"
                );

                ing_quantityView.requestFocus();

                return;
            }


            // Check if an existing pantry item is being edited
            if (getIntent().hasExtra("pItem_id")) {

                // Update the existing pantry item
                int result =
                        pantry_db.updatePantryItem(
                                pItem_id,
                                pItem_name,
                                pItem_quantity,
                                pItem_unit,
                                pItem_expiry_date
                        );


                // Check if the item was updated successfully
                if (result > 0) {

                    Toast.makeText(
                            this,
                            "Item updated successfully",
                            Toast.LENGTH_SHORT
                    ).show();


                    // Return to the Pantry screen
                    finish();

                } else {

                    Toast.makeText(
                            this,
                            "Item could not be updated",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            } else {

                // Add a new pantry item
                long result =
                        pantry_db.addPantryItem(
                                pItem_name,
                                pItem_quantity,
                                pItem_unit,
                                pItem_expiry_date
                        );


                // Check if the item was added successfully
                if (result != -1) {

                    Toast.makeText(
                            this,
                            "Item added successfully",
                            Toast.LENGTH_SHORT
                    ).show();


                    // Return to the Pantry screen
                    finish();

                } else {

                    Toast.makeText(
                            this,
                            "Item could not be added",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

        });


        // Keep the old hidden Back to Pantry button working
        back_pantry_buttonView.setOnClickListener(v -> {

            finish();

        });


        // Add padding so the screen does not overlap the system bars
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }
}