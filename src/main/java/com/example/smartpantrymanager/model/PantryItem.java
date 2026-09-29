package com.example.smartpantrymanager.model;

public class PantryItem {

    // Variables for a pantry item
    private int pItem_id;
    private String pItem_name;
    private double pItem_quantity;
    private String pItem_unit;
    private String pItem_expiry_date;

    // Constructor
    public PantryItem(int pItem_id, String pItem_name, double pItem_quantity,
                      String pItem_unit, String pItem_expiry_date) {

        this.pItem_id = pItem_id;
        this.pItem_name = pItem_name;
        this.pItem_quantity = pItem_quantity;
        this.pItem_unit = pItem_unit;
        this.pItem_expiry_date = pItem_expiry_date;
    }

    // Getter and Setter methods
    public int getPItem_id() {
        return pItem_id;
    }

    public void setPItem_id(int pItem_id) {
        this.pItem_id = pItem_id;
    }

    public String getPItem_name() {
        return pItem_name;
    }

    public void setPItem_name(String pItem_name) {
        this.pItem_name = pItem_name;
    }

    public double getPItem_quantity() {
        return pItem_quantity;
    }

    public void setPItem_quantity(double pItem_quantity) {
        this.pItem_quantity = pItem_quantity;
    }

    public String getPItem_unit() {
        return pItem_unit;
    }

    public void setPItem_unit(String pItem_unit) {
        this.pItem_unit = pItem_unit;
    }

    public String getPItem_expiry_date() {
        return pItem_expiry_date;
    }

    public void setPItem_expiry_date(String pItem_expiry_date) {
        this.pItem_expiry_date = pItem_expiry_date;
    }
}