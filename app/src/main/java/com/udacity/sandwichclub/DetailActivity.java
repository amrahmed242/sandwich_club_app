package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Intent intent = null;
    int position = DEFAULT_POSITION;
    private static ImageView ingredientsIv;
    private static TextView mainName_data;
    private static TextView also_known_data;
    private static TextView placeoforigin_data;
    private static TextView ingredients_data;
    private static TextView description_data;
    private static List<String> alsoknownas_list=null;
    private static List<String> ingredients_list=null;
    private static Sandwich sandwich = null;
    private static LinearLayout lin_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //assigning views
        assign_Views();

        //checking intent data for errors
        check_intent();

        //getting JSON object to parse
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // check statement to prevent crashes from received object

        if (sandwich != null) {
            //populate the UI
                populate_UI();
            //animating data
                animate_Views();

        }else {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //loading image from internet
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void check_intent(){

        intent=getIntent();
        if (intent == null) {
            closeOnError();
        }

        position=intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void assign_Views(){
        //assigning views
        ingredientsIv = findViewById(R.id.image_iv);
        mainName_data= findViewById(R.id.mainName_data);
        placeoforigin_data= findViewById(R.id.placeoforigin_data);
        also_known_data= findViewById(R.id.also_known_data);
        ingredients_data= findViewById(R.id.ingredients_data);
        description_data= findViewById(R.id.description_data);
        lin_layout= findViewById(R.id.lin_layout);
    }

    private void populate_UI(){
        mainName_data.setText(sandwich.getMainName());
        placeoforigin_data.setText(sandwich.getPlaceOfOrigin());
        description_data.setText(sandwich.getDescription());

        alsoknownas_list=sandwich.getAlsoKnownAs();
        ingredients_list=sandwich.getIngredients();

        for (int i=0;i<alsoknownas_list.size();i++){also_known_data.append(alsoknownas_list.get(i));}
        for (int i=0;i<ingredients_list.size();i++){ingredients_data.append(ingredients_list.get(i));}
    }

    private void animate_Views(){
        lin_layout.animate().alpha(0.9f).setDuration(1000);
        ingredientsIv.animate().alpha(0.9f).setDuration(1250);
    }
}
