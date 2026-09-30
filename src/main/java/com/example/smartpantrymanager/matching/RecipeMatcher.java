package com.example.smartpantrymanager.matching;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {

    // Checks if one required recipe ingredient is in the pantry
    public boolean checkIngredient(List<PantryItem> pantry_itemList,
                                   RecipeIngredient required_ing) {

        // The ingredient has not been found yet
        boolean ing_found = false;

        // Go through each item in the pantry
        for (PantryItem pantry_item : pantry_itemList) {

            // Check if the ingredient names match
            if (ingredientNamesMatch(
                    pantry_item.getPItem_name(),
                    required_ing.getRIng_name())) {

                // Get the pantry quantity and unit
                double pantry_itemQuantity = pantry_item.getPItem_quantity();
                String pantry_itemUnit = pantry_item.getPItem_unit();

                // Get the quantity and unit required by the recipe
                double recIng_reqQuantity = required_ing.getRIng_quantity();
                String recIng_reqUnit = required_ing.getRIng_unit();

                // Convert the quantities into standard units
                pantry_itemQuantity = convertQuantity(
                        pantry_itemQuantity, pantry_itemUnit);

                recIng_reqQuantity = convertQuantity(
                        recIng_reqQuantity, recIng_reqUnit);

                // Check if the units are compatible
                if (unitsMatch(pantry_itemUnit, recIng_reqUnit)) {

                    // Check if there is enough of the ingredient
                    if (pantry_itemQuantity >= recIng_reqQuantity) {

                        ing_found = true;
                        break;
                    }
                }
            }
        }

        // Return whether the ingredient was found
        return ing_found;
    }


    // Converts units into smaller standard units
    private double convertQuantity(double converted_quantity,
                                   String quantity_unit) {

        // Convert litres to millilitres
        if (quantity_unit.equalsIgnoreCase("L")) {
            converted_quantity = converted_quantity * 1000;
        }

        // Convert cups to millilitres
        else if (quantity_unit.equalsIgnoreCase("cup")) {
            converted_quantity = converted_quantity * 250;
        }

        // Convert tablespoons to millilitres
        else if (quantity_unit.equalsIgnoreCase("tbsp")) {
            converted_quantity = converted_quantity * 15;
        }

        // Convert teaspoons to millilitres
        else if (quantity_unit.equalsIgnoreCase("tsp")) {
            converted_quantity = converted_quantity * 5;
        }

        // Convert kilograms to grams
        else if (quantity_unit.equalsIgnoreCase("kg")) {
            converted_quantity = converted_quantity * 1000;
        }

        // Return the converted quantity
        return converted_quantity;
    }


    // Checks if two units can be compared
    private boolean unitsMatch(String pantry_unit,
                               String recIng_unit) {

        // Same units are compatible
        if (pantry_unit.equalsIgnoreCase(recIng_unit)) {
            return true;
        }

        // Check if both units are volume units
        if ((pantry_unit.equalsIgnoreCase("L") ||
                pantry_unit.equalsIgnoreCase("ml") ||
                pantry_unit.equalsIgnoreCase("cup") ||
                pantry_unit.equalsIgnoreCase("tbsp") ||
                pantry_unit.equalsIgnoreCase("tsp")) &&

                (recIng_unit.equalsIgnoreCase("L") ||
                        recIng_unit.equalsIgnoreCase("ml") ||
                        recIng_unit.equalsIgnoreCase("cup") ||
                        recIng_unit.equalsIgnoreCase("tbsp") ||
                        recIng_unit.equalsIgnoreCase("tsp"))) {

            return true;
        }

        // Kilograms and grams are compatible
        if ((pantry_unit.equalsIgnoreCase("kg") &&
                recIng_unit.equalsIgnoreCase("g")) ||
                (pantry_unit.equalsIgnoreCase("g") &&
                        recIng_unit.equalsIgnoreCase("kg"))) {

            return true;
        }

        // Units are not compatible
        return false;
    }


    // Checks if two ingredient names match
    private boolean ingredientNamesMatch(String pantry_ingName,
                                         String recIng_reqName) {

        // Remove spaces and change both names to lowercase
        pantry_ingName = pantry_ingName.trim().toLowerCase();
        recIng_reqName = recIng_reqName.trim().toLowerCase();

        // Check if the names are already the same
        if (pantry_ingName.equals(recIng_reqName)) {
            return true;
        }

        // Check simple plural words ending in s
        if (pantry_ingName.endsWith("s") &&
                pantry_ingName.substring(
                                0, pantry_ingName.length() - 1)
                        .equals(recIng_reqName)) {

            return true;
        }

        if (recIng_reqName.endsWith("s") &&
                recIng_reqName.substring(
                                0, recIng_reqName.length() - 1)
                        .equals(pantry_ingName)) {

            return true;
        }

        // Check words such as tomato and tomatoes
        if (pantry_ingName.endsWith("es") &&
                pantry_ingName.substring(
                                0, pantry_ingName.length() - 2)
                        .equals(recIng_reqName)) {

            return true;
        }

        if (recIng_reqName.endsWith("es") &&
                recIng_reqName.substring(
                                0, recIng_reqName.length() - 2)
                        .equals(pantry_ingName)) {

            return true;
        }

        // The ingredient names do not match
        return false;
    }


    // Checks if every ingredient needed for a recipe is in the pantry
    public boolean checkRecipe(Recipe recipe_item,
                               List<PantryItem> pantry_itemList) {

        // Assume the recipe can be made
        boolean recipe_match = true;

        // Go through every ingredient required by the recipe
        for (RecipeIngredient required_ing : recipe_item.getR_ingList()) {

            // Check if the required ingredient is available
            if (!checkIngredient(pantry_itemList, required_ing)) {

                // One ingredient failed, so the recipe cannot be made
                recipe_match = false;
                break;
            }
        }

        // Return whether the whole recipe can be made
        return recipe_match;
    }


    // Finds all recipes that can be made using the pantry items
    public List<Recipe> getMatchedRecipes(List<Recipe> r_itemList,
                                          List<PantryItem> pantry_itemList) {

        // List that stores only the recipes that can be made
        List<Recipe> matched_rList = new ArrayList<>();

        // Go through every recipe
        for (Recipe recipe_item : r_itemList) {

            // Check if all ingredients for the recipe are available
            if (checkRecipe(recipe_item, pantry_itemList)) {

                // Add the recipe if all ingredients passed
                matched_rList.add(recipe_item);
            }
        }

        // Return only the recipes that can be made
        return matched_rList;
    }
}