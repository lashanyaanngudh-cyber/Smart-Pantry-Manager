package com.example.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.Recipe;

import java.util.List;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    // List of recipes displayed in the RecyclerView
    private List<Recipe> r_itemList;


    // Constructor
    public RecipeAdapter(List<Recipe> r_itemList) {
        this.r_itemList = r_itemList;
    }


    // Creates the layout for one recipe
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View recipe_item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(recipe_item);
    }


    // Displays the recipe information
    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder, int position) {

        Recipe recipe_item = r_itemList.get(position);

        holder.recipe_nameText.setText(recipe_item.getR_name());
    }


    // Returns the number of recipes
    @Override
    public int getItemCount() {
        return r_itemList.size();
    }


    // Stores the views for one recipe
    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView recipe_nameText;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipe_nameText =
                    itemView.findViewById(R.id.recipe_nameText);
        }
    }
}