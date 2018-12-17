package com.example.jan.przetwarzanierozproszone;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Class post data
 */
public class PassData {
    String url = "ec2-34-248-214-99.eu-west-1.compute.amazonaws.com";

    /**
     * Method sets number of steps
     * @param numberOfSamples
     */
    public void setNumberOfSamples(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    int numberOfSamples;

    /**
     * Method creates json object
     * @param steps
     * @param logate
     */
    public void jsonCreator(ArrayList<Integer> steps, String logate) {
        String allSteps = "";
        for (int i = 0; i < steps.size(); i++) {
            if (i != 0) {
                allSteps = allSteps + ",";
            }
            allSteps = allSteps + steps.get(i);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("logdate", logate);
            jsonObject.put("nickname", MainActivity.nickname);
            jsonObject.put("steps", allSteps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON", String.valueOf(jsonObject));
        postJson(jsonObject);
        allSteps = null;

    }

    /**
     * Method posts json object
     * @param json
     */
    public void postJson(JSONObject json) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("XD", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        MainActivity.requestQueue.add(jsonObjectRequest);
    }

}
