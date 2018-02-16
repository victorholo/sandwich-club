package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String TAG_NAME = "name";
    public static final String TAG_MAIN_NAME = "mainName";
    public static final String TAG_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String TAG_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichDetails = new JSONObject(json);

        JSONObject names = sandwichDetails.getJSONObject(TAG_NAME);

        String mainName = names.getString(TAG_MAIN_NAME);

        List<String> alsoKnownAsNames = new ArrayList<>();

        JSONArray alsoKnownAsArray = names.getJSONArray(TAG_ALSO_KNOWN_AS);
        for(int i = 0; i < alsoKnownAsArray.length(); i++){
            String alsoKnownString = alsoKnownAsArray.getString(i);
            alsoKnownAsNames.add(alsoKnownString);
        }

        String placeOfOrigin = sandwichDetails.getString(TAG_PLACE_OF_ORIGIN);
        String description = sandwichDetails.getString(TAG_DESCRIPTION);
        String image = sandwichDetails.getString(TAG_IMAGE);

        List<String> ingredients = new ArrayList<>();

        JSONArray ingredientsArray = sandwichDetails.getJSONArray(TAG_INGREDIENTS);
        for(int i = 0; i < ingredientsArray.length(); i++){
            String ingredient = ingredientsArray.getString(i);
            ingredients.add(ingredient);
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAsNames, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
}
