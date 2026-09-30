package com.example.smartpantrymanager.adapter;

// Used to open another screen
import android.content.Context;
import android.content.Intent;

// Used for the RecyclerView item layout
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// RecyclerView imports
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// App classes
import com.example.smartpantrymanager.ItemDetailActivity;
import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.PantryItem;

// Used for expiry date calculations
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    // Stores the list of pantry items
    private List<PantryItem> p_items;


    // Constructor receives the list of pantry items
    public PantryAdapter(List<PantryItem> p_items) {

        this.p_items = p_items;
    }


    // Holds the views for one pantry item
    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        // Displays the pantry item information
        TextView item_nameView;
        TextView item_quantityView;
        TextView item_unitView;
        TextView item_expirydateView;
        TextView item_statusView;

        // Three-dot item options button
        TextView item_options_buttonView;


        public PantryViewHolder(@NonNull View pantryView) {
            super(pantryView);


            // Connect the Java views to the XML views
            item_nameView =
                    pantryView.findViewById(
                            R.id.pitem_name
                    );

            item_quantityView =
                    pantryView.findViewById(
                            R.id.pitem_quantity
                    );

            item_unitView =
                    pantryView.findViewById(
                            R.id.pitem_unit
                    );

            item_expirydateView =
                    pantryView.findViewById(
                            R.id.pitem_expirydate
                    );

            item_statusView =
                    pantryView.findViewById(
                            R.id.pitem_status
                    );

            item_options_buttonView =
                    pantryView.findViewById(
                            R.id.item_options_button
                    );
        }
    }


    // Creates the layout for one pantry item
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View pantry_itemView =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.item_pantry,
                                parent,
                                false
                        );


        return new PantryViewHolder(
                pantry_itemView
        );
    }


    // Displays the pantry item information
    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder pantry_holder,
            int position) {

        // Get one pantry item
        PantryItem p_item =
                p_items.get(position);


        // Display the item name
        pantry_holder.item_nameView.setText(
                p_item.getPItem_name()
        );


        // Display quantity and unit together
        pantry_holder.item_quantityView.setText(
                "Quantity: "
                        + p_item.getPItem_quantity()
                        + " "
                        + p_item.getPItem_unit()
        );


        // The unit is already displayed with the quantity
        pantry_holder.item_unitView.setText(
                p_item.getPItem_unit()
        );


        // Get the expiry date
        String expiry_date =
                p_item.getPItem_expiry_date();


        // Check if the item has an expiry date
        if (expiry_date != null
                && !expiry_date.trim().isEmpty()) {


            // Display the expiry date
            pantry_holder.item_expirydateView.setText(
                    "Expiry: " + expiry_date
            );


            try {

                // Format used by the dates stored in the database
                SimpleDateFormat date_format =
                        new SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.getDefault()
                        );


                // Do not allow incorrect dates
                date_format.setLenient(false);


                // Convert the item's expiry date
                Date item_expiry_date =
                        date_format.parse(
                                expiry_date
                        );


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


                // Check if the item has already expired
                if (item_expiry_date.before(
                        today_date
                )) {

                    pantry_holder.item_statusView.setText(
                            "EXPIRED"
                    );


                    // Use muted red text for expired items
                    pantry_holder.item_statusView.setTextColor(
                            pantry_holder
                                    .item_statusView
                                    .getContext()
                                    .getColor(
                                            R.color.status_expired
                                    )
                    );

                }


                // Check if the item expires within seven days
                else if (!item_expiry_date.after(
                        seven_days_date
                )) {

                    pantry_holder.item_statusView.setText(
                            "EXPIRING"
                    );


                    // Use warm brown text for expiring items
                    pantry_holder.item_statusView.setTextColor(
                            pantry_holder
                                    .item_statusView
                                    .getContext()
                                    .getColor(
                                            R.color.status_expiring
                                    )
                    );

                }


                // The item is not close to expiry
                else {

                    pantry_holder.item_statusView.setText(
                            "GOOD"
                    );


                    // Use dark sage green text for good items
                    pantry_holder.item_statusView.setTextColor(
                            pantry_holder
                                    .item_statusView
                                    .getContext()
                                    .getColor(
                                            R.color.status_good
                                    )
                    );
                }


            } catch (ParseException e) {

                // Use a simple status if the date cannot be read
                pantry_holder.item_statusView.setText(
                        "GOOD"
                );


                pantry_holder.item_statusView.setTextColor(
                        pantry_holder
                                .item_statusView
                                .getContext()
                                .getColor(
                                        R.color.status_good
                                )
                );
            }


        } else {

            // The item does not have an expiry date
            pantry_holder.item_expirydateView.setText(
                    "Expiry: Not set"
            );


            pantry_holder.item_statusView.setText(
                    "GOOD"
            );


            pantry_holder.item_statusView.setTextColor(
                    pantry_holder
                            .item_statusView
                            .getContext()
                            .getColor(
                                    R.color.status_good
                            )
            );
        }


        // Open Item Details when the three dots are tapped
        pantry_holder.item_options_buttonView
                .setOnClickListener(v -> {

                    // Get the current screen
                    Context context =
                            v.getContext();


                    // Open the Item Details screen
                    Intent intent =
                            new Intent(
                                    context,
                                    ItemDetailActivity.class
                            );


                    // Send the pantry item ID
                    intent.putExtra(
                            "pItem_id",
                            p_item.getPItem_id()
                    );


                    // Send the pantry item name
                    intent.putExtra(
                            "pItem_name",
                            p_item.getPItem_name()
                    );


                    // Send the pantry item quantity
                    intent.putExtra(
                            "pItem_quantity",
                            p_item.getPItem_quantity()
                    );


                    // Send the pantry item unit
                    intent.putExtra(
                            "pItem_unit",
                            p_item.getPItem_unit()
                    );


                    // Send the pantry item expiry date
                    intent.putExtra(
                            "pItem_expiry_date",
                            p_item.getPItem_expiry_date()
                    );


                    // Open the Item Details screen
                    context.startActivity(
                            intent
                    );
                });
    }


    // Returns the total number of pantry items
    @Override
    public int getItemCount() {

        return p_items.size();
    }
}