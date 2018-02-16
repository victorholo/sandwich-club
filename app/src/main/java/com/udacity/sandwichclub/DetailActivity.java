package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /* Populates the interface with sandwich info
     * If the information is missing, hide the label and description for that specific information
     */
    private void populateUI(Sandwich sandwich) {

        TextView alsoKnown = findViewById(R.id.also_known_tv);

        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if(alsoKnownList.size() == 0){
            TextView alsoKnownLabel = findViewById(R.id.also_known_label_tv);
            alsoKnownLabel.setVisibility(View.GONE);
            alsoKnown.setVisibility(View.GONE);
        }else{
            for(int i = 0; i < alsoKnownList.size(); i++) {
                alsoKnown.append(alsoKnownList.get(i));
                if(i != alsoKnownList.size() - 1){
                    alsoKnown.append("\n");
                }
            }
            alsoKnown.setContentDescription(alsoKnown.getText().toString());
        }

        TextView origin = findViewById(R.id.origin_tv);

        String originString = sandwich.getPlaceOfOrigin();
        if(originString == null || originString.isEmpty()){
            TextView originLabel = findViewById(R.id.place_of_origin_label_tv);
            originLabel.setVisibility(View.GONE);
            origin.setVisibility(View.GONE);
        }else{
            origin.setText(originString);
            origin.setContentDescription(originString);
        }

        TextView ingredients = findViewById(R.id.ingredients_tv);

        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList.size() == 0){
            TextView ingredientsLabel = findViewById(R.id.ingredients_label_tv);
            ingredientsLabel.setVisibility(View.GONE);
            ingredients.setVisibility(View.GONE);
        }else{
            for(int i = 0; i < ingredientsList.size(); i++){
                ingredients.append(ingredientsList.get(i));
                if(i != ingredientsList.size() - 1) {
                    ingredients.append("\n");
                }
            }
            ingredients.setContentDescription(ingredients.getText().toString());
        }

        TextView description = findViewById(R.id.description_tv);

        String descriptionString = sandwich.getDescription();
        if(descriptionString == null || descriptionString.isEmpty()){
            TextView descriptionLabel = findViewById(R.id.description_label_tv);
            descriptionLabel.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
        }else{
            description.setText(descriptionString);
            description.setContentDescription(descriptionString);
        }


    }
}
