package com.example.smartpantrymanager.model;

public class RecipeIngredient {

    // Variables for an ingredient in a recipe
    private int rIng_id;
    private int r_id;
    private String rIng_name;
    private double rIng_quantity;
    private String rIng_unit;

    // Constructor
    public RecipeIngredient(int rIng_id, int r_id, String rIng_name,
                            double rIng_quantity, String rIng_unit) {

        this.rIng_id = rIng_id;
        this.r_id = r_id;
        this.rIng_name = rIng_name;
        this.rIng_quantity = rIng_quantity;
        this.rIng_unit = rIng_unit;
    }

    // Getter and Setter methods
    public int getRIng_id() {
        return rIng_id;
    }

    public void setRIng_id(int rIng_id) {
        this.rIng_id = rIng_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getRIng_name() {
        return rIng_name;
    }

    public void setRIng_name(String rIng_name) {
        this.rIng_name = rIng_name;
    }

    public double getRIng_quantity() {
        return rIng_quantity;
    }

    public void setRIng_quantity(double rIng_quantity) {
        this.rIng_quantity = rIng_quantity;
    }

    public String getRIng_unit() {
        return rIng_unit;
    }

    public void setRIng_unit(String rIng_unit) {
        this.rIng_unit = rIng_unit;
    }
}