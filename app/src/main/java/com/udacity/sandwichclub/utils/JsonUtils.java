package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static String mainName="";
    public static String placeOfOrigin="";
    public static String description="";
    public static String image="";
    public static List<String> alsoKnownAs=new ArrayList<>();
    public static List<String> ingredients=new ArrayList<>();


    //this function parse the JSON Data & return Sandwitch object

    public static Sandwich parseSandwichJson(String json) throws JSONException{


            JSONObject jsonAll = new JSONObject(json);
            JSONObject jsonname=new JSONObject(jsonAll.getString("name"));

            mainName=jsonname.getString("mainName");
            placeOfOrigin=jsonAll.getString("placeOfOrigin");
            description=jsonAll.getString("description");
            image=jsonAll.getString("image");

           JSONArray jsonAlsoknownAs=jsonname.getJSONArray("alsoKnownAs");

           for(int i=0;i<jsonAlsoknownAs.length();i++)
            {
                String s=(String) jsonAlsoknownAs.get(i);
                alsoKnownAs.add(s);
            }

            JSONArray jsoningredients = jsonAll.getJSONArray("ingredients");

            for(int i=0;i<jsoningredients.length();i++)
            {
                String s=(String) jsoningredients.get(i);
                ingredients.add(s);
            }

        Sandwich sandwich=new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        return sandwich;
    }
}
