package com.example.smartpantrymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpantrymanager.database.PantryDatabaseHelper;

public class ItemDetailActivity extends Activity {

    // Editable fields for the pantry item
    EditText item_detail_nameView;
    EditText item_detail_quantityView;
    EditText item_detail_unitView;
    EditText item_detail_expiryView;

    // Arrow used to return to the Pantry screen
    TextView item_detail_back_arrowView;

    // Buttons used to update or delete the item
    Button item_detail_edit_buttonView;
    Button item_detail_delete_buttonView;

    // Used to access the pantry database
    PantryDatabaseHelper pantry_db;

    // Stores the ID of the selected pantry item
    int pItem_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_detail);


        // Connect the editable fields
        item_detail_nameView =
                findViewById(R.id.item_detail_name);

        item_detail_quantityView =
                findViewById(R.id.item_detail_quantity);

        item_detail_unitView =
                findViewById(R.id.item_detail_unit);

        item_detail_expiryView =
                findViewById(R.id.item_detail_expiry);


        // Connect the back arrow
        item_detail_back_arrowView =
                findViewById(R.id.item_detail_back_arrow);


        // Connect the buttons
        item_detail_edit_buttonView =
                findViewById(R.id.item_detail_edit_button);

        item_detail_delete_buttonView =
                findViewById(R.id.item_detail_delete_button);


        // Connect to the pantry database
        pantry_db =
                new PantryDatabaseHelper(this);


        // Get the pantry item ID
        pItem_id =
                getIntent().getIntExtra(
                        "pItem_id",
                        -1
                );


        // Display the item name
        item_detail_nameView.setText(
                getIntent().getStringExtra(
                        "pItem_name"
                )
        );


        // Display the item quantity
        item_detail_quantityView.setText(
                String.valueOf(
                        getIntent().getDoubleExtra(
                                "pItem_quantity",
                                0
                        )
                )
        );


        // Display the item unit
        item_detail_unitView.setText(
                getIntent().getStringExtra(
                        "pItem_unit"
                )
        );


        // Get the expiry date
        String pItem_expiry_date =
                getIntent().getStringExtra(
                        "pItem_expiry_date"
                );


        // Display the expiry date if one exists
        if (pItem_expiry_date != null) {

            item_detail_expiryView.setText(
                    pItem_expiry_date
            );
        }


        // Return to Pantry when the arrow is tapped
        item_detail_back_arrowView.setOnClickListener(v -> {

            finish();

        });


        // Update the item directly from this screen
        item_detail_edit_buttonView.setOnClickListener(v -> {

            // Get the updated information
            String pItem_name =
                    item_detail_nameView
                            .getText()
                            .toString()
                            .trim();

            String quantity_text =
                    item_detail_quantityView
                            .getText()
                            .toString()
                            .trim();

            String pItem_unit =
                    item_detail_unitView
                            .getText()
                            .toString()
                            .trim();

            String updated_expiry_date =
                    item_detail_expiryView
                            .getText()
                            .toString()
                            .trim();


            // Check if the item name is empty
            if (pItem_name.isEmpty()) {

                item_detail_nameView.setError(
                        "Please enter an item name"
                );

                item_detail_nameView.requestFocus();

                return;
            }


            // Check if the quantity is empty
            if (quantity_text.isEmpty()) {

                item_detail_quantityView.setError(
                        "Please enter a quantity"
                );

                item_detail_quantityView.requestFocus();

                return;
            }


            // Check if the unit is empty
            if (pItem_unit.isEmpty()) {

                item_detail_unitView.setError(
                        "Please enter a unit"
                );

                item_detail_unitView.requestFocus();

                return;
            }


            // Check that the quantity is a valid number
            double pItem_quantity;

            try {

                pItem_quantity =
                        Double.parseDouble(
                                quantity_text
                        );

            } catch (NumberFormatException e) {

                item_detail_quantityView.setError(
                        "Please enter a valid quantity"
                );

                item_detail_quantityView.requestFocus();

                return;
            }


            // Quantity must be greater than zero
            if (pItem_quantity <= 0) {

                item_detail_quantityView.setError(
                        "Quantity must be greater than 0"
                );

                item_detail_quantityView.requestFocus();

                return;
            }


            // Update the pantry item in the database
            int result =
                    pantry_db.updatePantryItem(
                            pItem_id,
                            pItem_name,
                            pItem_quantity,
                            pItem_unit,
                            updated_expiry_date
                    );


            // Check if the update worked
            if (result > 0) {

                Toast.makeText(
                        this,
                        "Item updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Item could not be updated",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        // Delete the item directly from this screen
        item_detail_delete_buttonView.setOnClickListener(v -> {

            // Get the current item name
            String pItem_name =
                    item_detail_nameView
                            .getText()
                            .toString()
                            .trim();


            // Ask the user to confirm before deleting
            new AlertDialog.Builder(
                    ItemDetailActivity.this
            )
                    .setTitle("Delete Item")
                    .setMessage(
                            "Are you sure you want to delete "
                                    + pItem_name
                                    + "?"
                    )

                    // Cancel keeps the item
                    .setNegativeButton(
                            "Cancel",
                            (dialog, which) -> dialog.dismiss()
                    )

                    // Delete removes the item
                    .setPositiveButton(
                            "Delete",
                            (dialog, which) -> {

                                int result =
                                        pantry_db.deletePantryItem(
                                                pItem_id
                                        );


                                // Check if the item was deleted
                                if (result > 0) {

                                    Toast.makeText(
                                            ItemDetailActivity.this,
                                            "Item deleted successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();


                                    // Return to Pantry
                                    finish();

                                } else {

                                    Toast.makeText(
                                            ItemDetailActivity.this,
                                            "Item could not be deleted",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    )

                    .show();
        });
    }
}