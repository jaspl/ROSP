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

public class PassData {
    String url = "http://192.168.8.103/lol/jsonFileTest.php";

    public void setNumberOfSamples(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    int numberOfSamples;

    public void jsonCreator(ArrayList<Integer> steps, String logate) {
        String allSteps = "";
        for (int i = 0; i < steps.size(); i++) {
            if (i != steps.size()+1) {
                allSteps = allSteps + ", ";
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

    public void postJson(JSONObject json) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               VolleyLog.d(TAG, "Error: " + response.toString());
               Log.d("XD", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) ;
        MainActivity.requestQueue.add(jsonObjectRequest);
    }

}
