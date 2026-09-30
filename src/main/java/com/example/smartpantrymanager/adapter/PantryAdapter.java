package com.example.smartpantrymanager.adapter;

// Used to open the Edit Ingredient screen
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
import com.example.smartpantrymanager.AddIngredientActivity;
import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.PantryItem;

// Used to store the pantry items
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    // Stores the list of pantry items
    private List<PantryItem> p_items;


    // Constructor receives the list of pantry items
    public PantryAdapter(List<PantryItem> p_items) {
        this.p_items = p_items;
    }


    // Holds the views for one pantry item
    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        // TextViews that display the pantry item information
        TextView item_nameView;
        TextView item_quantityView;
        TextView item_unitView;
        TextView item_expirydateView;


        // Constructor for the ViewHolder
        public PantryViewHolder(@NonNull View pantryView) {
            super(pantryView);

            // Connect each Java TextView to its XML TextView
            item_nameView = pantryView.findViewById(R.id.pitem_name);
            item_quantityView = pantryView.findViewById(R.id.pitem_quantity);
            item_unitView = pantryView.findViewById(R.id.pitem_unit);
            item_expirydateView = pantryView.findViewById(R.id.pitem_expirydate);
        }
    }


    // Creates the layout for one pantry item
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View pantry_itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(pantry_itemView);
    }


    // Displays the pantry item information
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder pantry_holder, int position) {

        // Get one pantry item from the list
        PantryItem p_item = p_items.get(position);

        // Display the item name
        pantry_holder.item_nameView.setText(p_item.getPItem_name());

        // Display the item quantity
        pantry_holder.item_quantityView.setText(
                String.valueOf(p_item.getPItem_quantity())
        );

        // Display the item unit
        pantry_holder.item_unitView.setText(p_item.getPItem_unit());

        // Display the expiry date
        pantry_holder.item_expirydateView.setText(
                p_item.getPItem_expiry_date()
        );

        // Open the ingredient screen when a pantry item is clicked
        pantry_holder.itemView.setOnClickListener(v -> {

            // Get the current screen
            Context context = v.getContext();

            // Open the Add Ingredient screen
            Intent intent = new Intent(context, AddIngredientActivity.class);

            // Send the pantry item information to the next screen
            intent.putExtra("pItem_id", p_item.getPItem_id());
            intent.putExtra("pItem_name", p_item.getPItem_name());
            intent.putExtra("pItem_quantity", p_item.getPItem_quantity());
            intent.putExtra("pItem_unit", p_item.getPItem_unit());
            intent.putExtra("pItem_expiry_date", p_item.getPItem_expiry_date());

            context.startActivity(intent);
        });
    }


    // Returns the total number of pantry items
    @Override
    public int getItemCount() {
        return p_items.size();
    }
}