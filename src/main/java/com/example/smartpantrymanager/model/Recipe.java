package com.example.smartpantrymanager.model;

import java.util.ArrayList;

public class Recipe {

    // Variables for a recipe
    private int r_id;
    private String r_name;
    private String r_instructions;
    private ArrayList<RecipeIngredient> r_ingList;

    // Constructor
    public Recipe(int r_id, String r_name, String r_instructions) {

        this.r_id = r_id;
        this.r_name = r_name;
        this.r_instructions = r_instructions;

        // Create an empty ingredient list for the recipe
        this.r_ingList = new ArrayList<>();
    }

    // Getter and Setter methods
    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_instructions() {
        return r_instructions;
    }

    public void setR_instructions(String r_instructions) {
        this.r_instructions = r_instructions;
    }

    // Array list to store the recipe ingredients
    public ArrayList<RecipeIngredient> getR_ingList() {
        return r_ingList;
    }

    public void setR_ingList(ArrayList<RecipeIngredient> r_ingList) {
        this.r_ingList = r_ingList;
    }

    // Add an ingredient to the recipe
    public void addIngredient(RecipeIngredient ingredient) {
        r_ingList.add(ingredient);
    }
}