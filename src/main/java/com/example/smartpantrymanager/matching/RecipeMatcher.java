package com.example.smartpantrymanager.matching;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {


    // Checks if one required recipe ingredient is available in the pantry
    public boolean checkIngredient(List<PantryItem> pantry_itemList,
                                   RecipeIngredient required_ing) {

        // Water is assumed to always be available
        // so it does not need to be stored in the pantry
        if (required_ing.getRIng_name()
                .trim()
                .equalsIgnoreCase("water")) {

            return true;
        }


        boolean ing_found = false;


        // Go through all the ingredients in the pantry
        for (PantryItem pantry_item : pantry_itemList) {

            // Check if the pantry ingredient name matches
            // the ingredient required by the recipe
            if (ingredientNamesMatch(
                    pantry_item.getPItem_name(),
                    required_ing.getRIng_name())) {


                double pantry_itemQuantity =
                        pantry_item.getPItem_quantity();

                String pantry_itemUnit =
                        pantry_item.getPItem_unit();

                double recIng_reqQuantity =
                        required_ing.getRIng_quantity();

                String recIng_reqUnit =
                        required_ing.getRIng_unit();


                // Check if the units can be directly compared
                if (unitsMatch(
                        pantry_itemUnit,
                        recIng_reqUnit)) {


                    // Convert both quantities into common units
                    pantry_itemQuantity = convertQuantity(
                            pantry_itemQuantity,
                            pantry_itemUnit);

                    recIng_reqQuantity = convertQuantity(
                            recIng_reqQuantity,
                            recIng_reqUnit);


                    // Check if there is enough of the ingredient
                    if (pantry_itemQuantity >= recIng_reqQuantity) {

                        ing_found = true;
                        break;
                    }
                }


                // Check dry ingredients stored in grams or kilograms
                // when the recipe uses tbsp, tsp or cup
                else if ((pantry_itemUnit.equalsIgnoreCase("g") ||
                        pantry_itemUnit.equalsIgnoreCase("kg")) &&

                        (recIng_reqUnit.equalsIgnoreCase("tbsp") ||
                                recIng_reqUnit.equalsIgnoreCase("tsp") ||
                                recIng_reqUnit.equalsIgnoreCase("cup"))) {


                    // Convert the pantry amount to grams
                    pantry_itemQuantity = convertQuantity(
                            pantry_itemQuantity,
                            pantry_itemUnit);


                    // Convert the recipe amount to grams
                    recIng_reqQuantity = convertDryIngredient(
                            recIng_reqQuantity,
                            recIng_reqUnit,
                            required_ing.getRIng_name());


                    // -1 means that there is no conversion
                    // available for that ingredient
                    if (recIng_reqQuantity != -1) {

                        // Check if enough is available
                        if (pantry_itemQuantity >= recIng_reqQuantity) {

                            ing_found = true;
                            break;
                        }
                    }
                }
            }
        }


        return ing_found;
    }


    // Converts compatible measurements into common smaller units
    private double convertQuantity(double converted_quantity,
                                   String quantity_unit) {

        // Litres to millilitres
        if (quantity_unit.equalsIgnoreCase("L")) {

            converted_quantity = converted_quantity * 1000;
        }

        // Cups to millilitres
        else if (quantity_unit.equalsIgnoreCase("cup")) {

            converted_quantity = converted_quantity * 250;
        }

        // Tablespoons to millilitres
        else if (quantity_unit.equalsIgnoreCase("tbsp")) {

            converted_quantity = converted_quantity * 15;
        }

        // Teaspoons to millilitres
        else if (quantity_unit.equalsIgnoreCase("tsp")) {

            converted_quantity = converted_quantity * 5;
        }

        // Kilograms to grams
        else if (quantity_unit.equalsIgnoreCase("kg")) {

            converted_quantity = converted_quantity * 1000;
        }


        return converted_quantity;
    }


    // Converts some dry ingredients from recipe measurements
    // such as tsp, tbsp and cup into grams
    private double convertDryIngredient(double dry_quantity,
                                        String dry_unit,
                                        String dry_ingName) {

        dry_ingName = dry_ingName.trim().toLowerCase();


        // Tablespoon conversions
        if (dry_unit.equalsIgnoreCase("tbsp")) {

            if (dry_ingName.equals("flour")) {

                return dry_quantity * 8;
            }

            else if (dry_ingName.equals("masala")) {

                return dry_quantity * 8;
            }

            else if (dry_ingName.equals("garam masala")) {

                return dry_quantity * 8;
            }

            else if (dry_ingName.equals("butter")) {

                return dry_quantity * 14;
            }

            else if (dry_ingName.equals("sugar")) {

                return dry_quantity * 12;
            }

            else if (dry_ingName.equals("salt")) {

                return dry_quantity * 18;
            }

            else if (dry_ingName.equals("parsley")) {

                return dry_quantity * 4;
            }

            else if (dry_ingName.equals("coriander")) {

                return dry_quantity * 4;
            }
        }


        // Teaspoon conversions
        else if (dry_unit.equalsIgnoreCase("tsp")) {

            if (dry_ingName.equals("turmeric")) {

                return dry_quantity * 3;
            }

            else if (dry_ingName.equals("chilli powder")) {

                return dry_quantity * 3;
            }

            else if (dry_ingName.equals("fennel seeds")) {

                return dry_quantity * 2;
            }

            else if (dry_ingName.equals("coriander cumin powder")) {

                return dry_quantity * 3;
            }

            else if (dry_ingName.equals("nutmeg")) {

                return dry_quantity * 2;
            }

            else if (dry_ingName.equals("thyme")) {

                return dry_quantity * 1;
            }

            else if (dry_ingName.equals("masala")) {

                return dry_quantity * 3;
            }

            else if (dry_ingName.equals("garam masala")) {

                return dry_quantity * 3;
            }

            else if (dry_ingName.equals("cumin seeds")) {

                return dry_quantity * 2;
            }

            else if (dry_ingName.equals("mustard seeds")) {

                return dry_quantity * 3;
            }

            else if (dry_ingName.equals("salt")) {

                return dry_quantity * 6;
            }

            else if (dry_ingName.equals("sugar")) {

                return dry_quantity * 4;
            }

            else if (dry_ingName.equals("black pepper")) {

                return dry_quantity * 2;
            }

            else if (dry_ingName.equals("aromat")) {

                return dry_quantity * 5;
            }
        }


        // Cup conversions
        else if (dry_unit.equalsIgnoreCase("cup")) {

            if (dry_ingName.equals("spinach")) {

                return dry_quantity * 30;
            }

            else if (dry_ingName.equals("flour")) {

                return dry_quantity * 125;
            }

            else if (dry_ingName.equals("sugar")) {

                return dry_quantity * 200;
            }

            else if (dry_ingName.equals("cheese")) {

                return dry_quantity * 100;
            }

            else if (dry_ingName.equals("cheddar cheese")) {

                return dry_quantity * 100;
            }
        }


        // Return -1 when there is no conversion
        return -1;
    }


    // Checks whether two measurement units
    // can be directly compared
    private boolean unitsMatch(String pantry_unit,
                               String recIng_unit) {


        // Exact same unit
        if (pantry_unit.equalsIgnoreCase(recIng_unit)) {

            return true;
        }


        // Liquid measurements can be converted
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


        // Weight measurements can be converted
        if ((pantry_unit.equalsIgnoreCase("kg") &&
                recIng_unit.equalsIgnoreCase("g")) ||

                (pantry_unit.equalsIgnoreCase("g") &&
                        recIng_unit.equalsIgnoreCase("kg"))) {

            return true;
        }


        return false;
    }


    // Checks whether the pantry ingredient name
    // matches the recipe ingredient name
    private boolean ingredientNamesMatch(String pantry_ingName,
                                         String recIng_reqName) {


        pantry_ingName =
                pantry_ingName.trim().toLowerCase();

        recIng_reqName =
                recIng_reqName.trim().toLowerCase();


        // Exact ingredient name
        if (pantry_ingName.equals(recIng_reqName)) {

            return true;
        }


        // Check simple singular and plural names
        if (pantry_ingName.endsWith("s") &&
                pantry_ingName.substring(
                                0,
                                pantry_ingName.length() - 1)
                        .equals(recIng_reqName)) {

            return true;
        }


        if (recIng_reqName.endsWith("s") &&
                recIng_reqName.substring(
                                0,
                                recIng_reqName.length() - 1)
                        .equals(pantry_ingName)) {

            return true;
        }


        // Check words ending in "es"
        if (pantry_ingName.endsWith("es") &&
                pantry_ingName.substring(
                                0,
                                pantry_ingName.length() - 2)
                        .equals(recIng_reqName)) {

            return true;
        }


        if (recIng_reqName.endsWith("es") &&
                recIng_reqName.substring(
                                0,
                                recIng_reqName.length() - 2)
                        .equals(pantry_ingName)) {

            return true;
        }


        // General fish can satisfy a specific type of fish
        // Example: Fish can match Red Fish
        if (pantry_ingName.equals("fish") &&
                recIng_reqName.endsWith(" fish")) {

            return true;
        }


        // General mushrooms can satisfy a specific
        // type of mushroom
        // Example: Mushrooms can match Button Mushrooms
        if (pantry_ingName.equals("mushrooms") &&
                recIng_reqName.endsWith(" mushrooms")) {

            return true;
        }


        if (pantry_ingName.equals("mushroom") &&
                recIng_reqName.endsWith(" mushrooms")) {

            return true;
        }


        // Garlic and crushed garlic are intentionally
        // NOT matched with each other.
        //
        // Garlic is used when the recipe requires cloves.
        // Crushed Garlic is used when the recipe requires grams.


        return false;
    }


    // Checks whether every ingredient required
    // by a recipe is available
    public boolean checkRecipe(Recipe recipe_item,
                               List<PantryItem> pantry_itemList) {


        boolean recipe_match = true;


        // Check every ingredient required by the recipe
        for (RecipeIngredient required_ing :
                recipe_item.getR_ingList()) {


            // If even one ingredient is unavailable,
            // the recipe must not be suggested
            if (!checkIngredient(
                    pantry_itemList,
                    required_ing)) {


                // Used while testing to show why
                // the recipe did not match
                System.out.println(
                        "RECIPE FAILED: "
                                + recipe_item.getR_name()
                                + " - Missing: "
                                + required_ing.getRIng_name()
                                + " "
                                + required_ing.getRIng_quantity()
                                + " "
                                + required_ing.getRIng_unit()
                );


                recipe_match = false;
                break;
            }
        }


        return recipe_match;
    }


    // Returns only recipes where every required
    // ingredient is available
    public List<Recipe> getMatchedRecipes(
            List<Recipe> r_itemList,
            List<PantryItem> pantry_itemList) {


        List<Recipe> matched_rList =
                new ArrayList<>();


        // Check each recipe
        for (Recipe recipe_item : r_itemList) {


            if (checkRecipe(
                    recipe_item,
                    pantry_itemList)) {


                // Only add recipes that fully match
                matched_rList.add(recipe_item);
            }
        }


        return matched_rList;
    }
}